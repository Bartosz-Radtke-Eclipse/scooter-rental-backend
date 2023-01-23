package com.example.scooter.data.repository

import com.example.scooter.data.model.HiddenScooter
import com.example.scooter.data.model.Scooter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HiddenScooterRepository : JpaRepository<HiddenScooter, Long> {
    fun findByScooter(scooter: Scooter): HiddenScooter?

    fun findByScooter_SerialNumber(scooter_serialNumber: String): HiddenScooter?
}