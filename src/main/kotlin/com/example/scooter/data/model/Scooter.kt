package com.example.scooter.data.model

import com.example.scooter.data.dto.AdminCreateScooterDto
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
data class Scooter(
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val model: String,
    val color: String,
    val maxSpeed: Int,
    val manufacturer: String,
    @Column(unique = true)
    val serialNumber: String,
    val batteryMeters: Int,
    var isHidden: Boolean,
    @OneToOne(cascade = [CascadeType.ALL])
    val scooterStatus: ScooterStatus

) {
    constructor(adminCreateScooterDto: AdminCreateScooterDto) : this(
        model = adminCreateScooterDto.model,
        color = adminCreateScooterDto.color,
        maxSpeed = adminCreateScooterDto.maxSpeed,
        manufacturer = adminCreateScooterDto.manufacturer,
        serialNumber = adminCreateScooterDto.serialNumber,
        batteryMeters = adminCreateScooterDto.batteryMeters,
        isHidden = adminCreateScooterDto.isHidden,
        scooterStatus = ScooterStatus(
            remainingBatteryPercent = adminCreateScooterDto.remainingBatteryPercent,
            locLat = adminCreateScooterDto.locLat,
            locLength = adminCreateScooterDto.locLength
        )
    )


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Scooter

        return id != 0L && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

}