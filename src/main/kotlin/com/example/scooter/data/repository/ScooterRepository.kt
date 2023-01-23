package com.example.scooter.data.repository

import com.example.scooter.data.model.Scooter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScooterRepository : JpaRepository<Scooter, Long> {
    fun findBySerialNumber(serialNumber:String): Scooter?
    fun existsBySerialNumber(serialNumber: String): Boolean
}