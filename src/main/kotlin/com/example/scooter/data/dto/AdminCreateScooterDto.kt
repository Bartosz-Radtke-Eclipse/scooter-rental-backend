package com.example.scooter.data.dto

data class AdminCreateScooterDto(
    val userName: String,
    val model: String,
    val color: String,
    val maxSpeed: Int,
    val manufacturer: String,
    val serialNumber: String,
    val batteryMeters: Int,
    var isHidden: Boolean,
    val remainingBatteryPercent: Int,
    val locLat: String,
    val locLength: String
)