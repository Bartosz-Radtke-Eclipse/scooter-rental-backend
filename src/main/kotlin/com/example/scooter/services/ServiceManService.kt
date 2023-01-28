package com.example.scooter.services

import com.example.scooter.data.model.HiddenScooter
import com.example.scooter.data.model.Serviceman
import com.example.scooter.data.repository.HiddenScooterRepository
import com.example.scooter.data.repository.ScooterRepository
import com.example.scooter.data.repository.ServicemanRepository
import com.example.scooter.exceptions.AccessDeniedException
import com.example.scooter.exceptions.NotFoundException
import org.springframework.stereotype.Service

@Service
class ServiceManService(
    private val hiddenScooterRepository: HiddenScooterRepository,
    private val servicemanRepository: ServicemanRepository,
    private val scooterRepository: ScooterRepository,
) {

    fun restoreScooter(userName: String, serialNumber: String) {
        validateAndGetServiceMan(userName)
        val hiddenScooter = getHiddenScooter(serialNumber)
        val scooter = hiddenScooter.scooter
        scooter.isHidden = false
        scooterRepository.save(scooter)
        hiddenScooterRepository.delete(hiddenScooter)
    }

    fun showHiddenScooters(userName: String): MutableList<HiddenScooter> {
        validateAndGetServiceMan(userName)
        return hiddenScooterRepository.findAll()
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