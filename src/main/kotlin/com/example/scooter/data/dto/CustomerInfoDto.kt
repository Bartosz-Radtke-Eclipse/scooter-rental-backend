package com.example.scooter.data.dto

import java.math.BigDecimal

data class CustomerInfoDto(val balance:BigDecimal, val userName: String, val rentedScooters : List<RentedScooterDto>)