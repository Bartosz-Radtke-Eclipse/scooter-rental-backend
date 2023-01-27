package com.example.scooter.controllers

import com.example.scooter.data.dto.HiddenScooterDto
import com.example.scooter.data.dto.RentScooterDto
import com.example.scooter.data.model.HiddenScooter
import com.example.scooter.data.model.Scooter
import com.example.scooter.exceptions.NotFoundException
import com.example.scooter.services.ScooterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/scooter")
class RentalController(private val scooterService: ScooterService) {
    @GetMapping("/list")
    fun getListOfAvailableScooters(): List<Scooter> {
        return scooterService.getListOfAvailableScooters()
    }

    @PostMapping("/rental")
    @Throws(NotFoundException::class, IllegalArgumentException::class)
    fun rentScooter(@RequestBody rentScooterDto: RentScooterDto): ResponseEntity<Any> {
        val rentedScooter = scooterService.rentScooter(rentScooterDto.userName, rentScooterDto.serialNumber)
        return ResponseEntity.ok(rentedScooter)
    }

    @PatchMapping("/rental")
    @Throws(NotFoundException::class, IllegalArgumentException::class)
    fun handOverScooter(@RequestBody rentScooterDto: RentScooterDto): ResponseEntity<Any> {
        val handOverScooter = scooterService.handOverScooter(rentScooterDto.userName, rentScooterDto.serialNumber)
        return ResponseEntity.ok(handOverScooter)
    }

    @PostMapping("/hidden")
    @Throws(IllegalArgumentException::class, AccessDeniedException::class, NotFoundException::class)
    fun hideScooter(@RequestBody hiddenScooterDto: HiddenScooterDto): ResponseEntity<HiddenScooter> {
        val hiddenScooter = scooterService.hideScooter(
            hiddenScooterDto.userName,
            hiddenScooterDto.serialNumber,
            hiddenScooterDto.reason
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(hiddenScooter)
    }
}