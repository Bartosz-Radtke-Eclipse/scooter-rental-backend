package com.example.scooter.controllers

import com.example.scooter.data.dto.HiddenScooterDto
import com.example.scooter.data.model.HiddenScooter
import com.example.scooter.data.model.Scooter
import com.example.scooter.exceptions.AccessDeniedException
import com.example.scooter.exceptions.NotFoundException
import com.example.scooter.services.ServiceManService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.Throws

@RestController
@RequestMapping("/api/serviceman/scooter")
class ServicemanController(private val serviceManService: ServiceManService) {
    @PostMapping
    @Throws(IllegalArgumentException::class, AccessDeniedException::class, NotFoundException::class)
    fun hideScooter(@RequestBody hiddenScooterDto: HiddenScooterDto): ResponseEntity<HiddenScooter> {
        val hiddenScooter = serviceManService.hideScooter(
            hiddenScooterDto.userName,
            hiddenScooterDto.serialNumber,
            hiddenScooterDto.reason
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(hiddenScooter)
    }

    @GetMapping("/list")
    @Throws(AccessDeniedException::class)
    fun showAllScooters(@RequestParam userName: String): ResponseEntity<MutableList<Scooter>> {
        return ResponseEntity.status(HttpStatus.OK).body(serviceManService.showAllScooters(userName))
    }

    @DeleteMapping
    @Throws(AccessDeniedException::class, NotFoundException::class, IllegalArgumentException::class)
    fun restoreScooter(@RequestParam userName: String, @RequestParam serialNumber: String) {
        serviceManService.restoreScooter(userName, serialNumber)
    }

}