package com.example.scooter.services

import com.example.scooter.data.dto.AdminCreateScooterDto
import com.example.scooter.data.model.Scooter
import com.example.scooter.data.repository.AdministratorRepository
import com.example.scooter.data.repository.HiddenScooterRepository
import com.example.scooter.data.repository.ScooterRepository
import com.example.scooter.exceptions.AccessDeniedException
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class AdminService(
    private val hiddenScooterRepository: HiddenScooterRepository,
    private val scooterRepository: ScooterRepository,
    private val scooterService: ScooterService,
    private val administratorRepository: AdministratorRepository
) {
    fun deleteScooter(serialNumber: String, userName: String) {
        validateAdmin(userName)
        val scooter = scooterService.getScooterFromSerialNumber(serialNumber)
        scooterService.checkIfScooterIsAvailable(scooter)
        hiddenScooterRepository.findByScooter(scooter)?.let { hiddenScooterRepository.delete(it) }
        scooterRepository.delete(scooter)
    }

    fun showAllScooters(userName: String): MutableList<Scooter> {
        validateAdmin(userName)
        return scooterRepository.findAll()
    }


    fun addScooterToDb(adminCreateScooterDto: AdminCreateScooterDto): Scooter {
        validateAdmin(adminCreateScooterDto.userName)
        checkIfSerialNumberIsValid(adminCreateScooterDto)
        val scooter = Scooter(adminCreateScooterDto)
        scooterRepository.save(scooter)
        return scooter
    }

    private fun checkIfSerialNumberIsValid(adminCreateScooterDto: AdminCreateScooterDto) {
        if (scooterRepository.existsBySerialNumber(adminCreateScooterDto.serialNumber)) {
            throw IllegalArgumentException(
                "Scooter with serial number: ${adminCreateScooterDto.serialNumber} already exist"
            )
        }
    }

    private fun validateAdmin(userName: String) {
        if (!administratorRepository.existsByUser_UserName(userName)) {
            throw AccessDeniedException()
        }
    }
}