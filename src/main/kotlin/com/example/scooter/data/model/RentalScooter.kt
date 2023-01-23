package com.example.scooter.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
data class RentalScooter(
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    val customer: Customer,
    @OneToOne
    @JoinColumn(name = "scooter_id", referencedColumnName = "id")
    val scooter: Scooter,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val rentalDate: LocalDateTime,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RentalScooter

        return id != 0L && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}

