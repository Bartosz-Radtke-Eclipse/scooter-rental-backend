package com.example.scooter.controllers

import com.example.scooter.data.dto.CustomerBalanceDto
import com.example.scooter.data.model.Customer
import com.example.scooter.exceptions.NotFoundException
import com.example.scooter.services.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.Throws

@RestController
@RequestMapping("/api/user")
class CustomerController(private val customerService: CustomerService) {
    @PatchMapping("/balance")
    @Throws(NotFoundException::class)
    fun addUserBalance(@RequestBody customerBalanceDto: CustomerBalanceDto): ResponseEntity<CustomerBalanceDto> {
        return ResponseEntity.ok(
            customerService.addUserBalance(
                customerBalanceDto.userName,
                customerBalanceDto.money)
        )
    }
    @GetMapping("/info")
    @Throws(NotFoundException::class)
    fun getCustomerInfo(@RequestParam userName:String): ResponseEntity<Customer> {
        return ResponseEntity.ok(
            customerService.getCustomerByUserName(userName)
        )
    }
}