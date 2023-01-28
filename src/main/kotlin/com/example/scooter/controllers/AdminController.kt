package com.example.scooter.controllers

import com.example.scooter.data.dto.AdminCreateScooterDto
import com.example.scooter.data.model.Scooter
import com.example.scooter.exceptions.AccessDeniedException
import com.example.scooter.exceptions.NotFoundException
import com.example.scooter.services.AdminService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.Throws

@RestController
@RequestMapping("/api/admin/scooter")
class AdminController (private val adminService: AdminService){

    @GetMapping("/list/all")
    @Throws(AccessDeniedException::class)
    fun showAllScooters(@RequestParam userName: String): ResponseEntity<MutableList<Scooter>> {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.showAllScooters(userName))
    }

    @DeleteMapping
    @Throws(IllegalArgumentException::class, NotFoundException::class, AccessDeniedException::class)
    fun deleteScooter(@RequestParam serialNumber: String, @RequestParam userName: String){
        adminService.deleteScooter(serialNumber, userName)
    }

    @PostMapping
    @Throws(AccessDeniedException::class, IllegalArgumentException::class)
    fun addScooterToDb(@RequestBody adminCreateScooterDto: AdminCreateScooterDto): ResponseEntity<Scooter> {
        val scooter = adminService.addScooterToDb(adminCreateScooterDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(scooter)
    }

}