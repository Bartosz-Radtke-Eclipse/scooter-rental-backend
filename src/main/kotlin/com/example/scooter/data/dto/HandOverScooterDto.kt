package com.example.scooter.data.dto

import java.math.BigDecimal

data class HandOverScooterDto(val cost: BigDecimal, val time:String, val locLat: String, val locLength: String)