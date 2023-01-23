package com.example.scooter.services

import com.example.scooter.data.model.HiddenScooter
import com.example.scooter.data.model.Scooter
import com.example.scooter.data.model.Serviceman
import com.example.scooter.data.repository.HiddenScooterRepository
import com.example.scooter.data.repository.ScooterRepository
import com.example.scooter.data.repository.ServicemanRepository
import com.example.scooter.exceptions.AccessDeniedException
import com.example.scooter.exceptions.NotFoundException
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class ServiceManService(
    private val hiddenScooterRepository: HiddenScooterRepository,
    private val scooterService: ScooterService,
    private val servicemanRepository: ServicemanRepository,
    private val scooterRepository: ScooterRepository
) {
    fun hideScooter(userName: String, serialNumber: String, reason: String?): HiddenScooter {
        val serviceman = validateAndGetServiceMan(userName)
        val scooter = scooterService.getScooterFromSerialNumber(serialNumber)
        scooterService.checkIfScooterIsAvailable(scooter)
        if (scooter.isHidden) {
            throw IllegalArgumentException("Scooter is already hidden")
        }
        scooter.isHidden = true
        scooterRepository.save(scooter)
        return hiddenScooterRepository.save(
            HiddenScooter(
                scooter = scooter,
                serviceman = serviceman,
                reason = reason ?: "no reason",
                customer = null
            )
        )

    }

    fun showAllScooters(userName: String): MutableList<Scooter> {
        validateAndGetServiceMan(userName)
        return scooterRepository.findAll()
    }

    fun restoreScooter(userName: String, serialNumber: String){
        validateAndGetServiceMan(userName)
        val hiddenScooter = getHiddenScooter(serialNumber)
        val scooter = hiddenScooter.scooter
        scooter.isHidden = false
        scooterRepository.save(scooter)
        hiddenScooterRepository.delete(hiddenScooter)
    }

    private fun getHiddenScooter(serialNumber: String): HiddenScooter {
        return hiddenScooterRepository.findByScooter_SerialNumber(serialNumber).takeIf { it != null }
            ?: throw NotFoundException("Scooter is not hidden")
    }

    private fun validateAndGetServiceMan(userName: String): Serviceman {
        return servicemanRepository.findByUser_UserName(userName).takeIf { it != null }
            ?: throw AccessDeniedException()
    }
}