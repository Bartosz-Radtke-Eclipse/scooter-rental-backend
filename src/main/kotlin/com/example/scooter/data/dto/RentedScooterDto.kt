package com.example.scooter.data.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class RentedScooterDto (val serialNumber: String, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) val rentalDate: LocalDateTime)