package com.example.scooter.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
data class HiddenScooter(
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne
    @JoinColumn(name = "scooter_id", referencedColumnName = "id")
    val scooter: Scooter,
    @ManyToOne
    @JoinColumn(name = "serviceman_id", referencedColumnName = "id")
    val serviceman: Serviceman?,
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    val customer: Customer,
    val reason:String
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as HiddenScooter

        return id != 0L && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
