package com.example.scooter.controllers

import com.example.scooter.data.model.HiddenScooter
import com.example.scooter.exceptions.AccessDeniedException
import com.example.scooter.exceptions.NotFoundException
import com.example.scooter.services.ServiceManService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/serviceman/scooter")
class ServicemanController(private val serviceManService: ServiceManService) {

    @GetMapping("/list")
    @Throws(AccessDeniedException::class)
    fun showHiddenScooters(@RequestParam userName: String): ResponseEntity<MutableList<HiddenScooter>> {
        return ResponseEntity.status(HttpStatus.OK).body(serviceManService.showHiddenScooters(userName))
    }

    @DeleteMapping
    @Throws(AccessDeniedException::class, NotFoundException::class, IllegalArgumentException::class)
    fun restoreScooter(@RequestParam userName: String, @RequestParam serialNumber: String) {
        serviceManService.restoreScooter(userName, serialNumber)
    }

}