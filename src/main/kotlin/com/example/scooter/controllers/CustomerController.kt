package com.example.scooter.controllers

import com.example.scooter.data.dto.CustomerBalanceDto
import com.example.scooter.data.dto.CustomerInfoDto
import com.example.scooter.exceptions.NotFoundException
import com.example.scooter.services.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class CustomerController(private val customerService: CustomerService) {
    @PatchMapping("/balance")
    @Throws(NotFoundException::class)
    fun addUserBalance(@RequestBody customerBalanceDto: CustomerBalanceDto): ResponseEntity<CustomerBalanceDto> {
        return ResponseEntity.ok(
            customerService.addUserBalance(
                customerBalanceDto.userName,
                customerBalanceDto.money
            )
        )
    }

    @GetMapping("/info")
    @Throws(NotFoundException::class)
    fun getCustomerInfo(@RequestParam userName: String): ResponseEntity<CustomerInfoDto> {
        return ResponseEntity.ok(
            customerService.getCustomerInfo(userName)
        )
    }
}