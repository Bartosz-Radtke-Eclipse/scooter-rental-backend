package com.example.scooter.data.dto

import org.springframework.format.annotation.DateTimeFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class RentedScooterDto(
    val serialNumber: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val rentalDate: LocalDateTime,
    val model: String,
    val remainingBatteryPercent: Int,
    val price: BigDecimal,
    val minutesOfRental: Long,
    val secondsOfRental: Long
)