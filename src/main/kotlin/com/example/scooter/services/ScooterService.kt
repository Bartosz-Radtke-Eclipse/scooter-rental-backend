package com.example.scooter.services

import com.example.scooter.data.dto.HandOverScooterDto
import com.example.scooter.data.model.Customer
import com.example.scooter.data.model.HiddenScooter
import com.example.scooter.data.model.RentalScooter
import com.example.scooter.data.model.Scooter
import com.example.scooter.data.repository.CustomerRepository
import com.example.scooter.data.repository.HiddenScooterRepository
import com.example.scooter.data.repository.RentalScooterRepository
import com.example.scooter.data.repository.ScooterRepository
import com.example.scooter.exceptions.NotFoundException
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ScooterService(
    private val scooterRepository: ScooterRepository,
    private val rentalScooterRepository: RentalScooterRepository,
    private val customerRepository: CustomerRepository,
    private val customerService: CustomerService,
    private val hiddenScooterRepository: HiddenScooterRepository
) {
    fun getListOfAvailableScooters(): List<Scooter> {
        return scooterRepository.findAll().filter { !it.isHidden }
    }

    fun hideScooter(userName: String, serialNumber: String, reason: String?): HiddenScooter {
        val customer = customerService.getCustomerByUserName(userName)
        val scooter = getScooterFromSerialNumber(serialNumber)
        checkIfScooterIsAvailable(scooter)
        if (scooter.isHidden) {
            throw IllegalArgumentException("Scooter is already hidden")
        }
        scooter.isHidden = true
        scooterRepository.save(scooter)
        return hiddenScooterRepository.save(
            HiddenScooter(
                scooter = scooter,
                serviceman = null,
                reason = reason ?: "no reason",
                customer = customer
            )
        )

    }

    fun rentScooter(userName: String, serialNumber: String): RentalScooter {
        val scooter: Scooter = getScooterFromSerialNumber(serialNumber)
        val customer: Customer = customerService.getCustomerByUserName(userName)
        checkIfScooterIsAvailable(scooter)
        checkIfBalanceIsAboveMinimum(customer.balance)
        checkIfBatteryIsNotZero(scooter.scooterStatus.remainingBatteryPercent)
        scooter.isHidden = true
        scooterRepository.save(scooter)
        return rentalScooterRepository.save(
            RentalScooter(
                customer = customer,
                scooter = scooter,
                rentalDate = LocalDateTime.now()
            )
        )
    }

    fun handOverScooter(userName: String, serialNumber: String): HandOverScooterDto {
        val rentalScooter = getRentalScooterByUserNameAndSerialNumber(userName, serialNumber)
        val handOverDate = LocalDateTime.now()
        val diff = ChronoUnit.MINUTES.between(rentalScooter.rentalDate, handOverDate)
        val cost = BigDecimal.valueOf((diff * 0.50) + 2)
        val customer = rentalScooter.customer
        customer.balance = customer.balance.minus(cost)
        customerRepository.save(customer)

        rentalScooterRepository.delete(rentalScooter)
        val scooter = rentalScooter.scooter
        scooter.isHidden = false
        scooter.scooterStatus.remainingBatteryPercent -= diff.toInt()
        scooterRepository.save(scooter)
        return HandOverScooterDto(cost, diff.toString(), scooter.scooterStatus.locLat, scooter.scooterStatus.locLength)
    }

    private fun getRentalScooterByUserNameAndSerialNumber(
        userName: String,
        serialNumber: String
    ) =
        (rentalScooterRepository.findByCustomer_User_UserNameAndScooter_SerialNumber(userName, serialNumber)
            .takeIf { it != null }
            ?: throw java.lang.IllegalArgumentException("User did not rent scooter"))

    internal fun checkIfScooterIsAvailable(scooter: Scooter) {
        if (rentalScooterRepository.existsByScooter(scooter)) {
            throw IllegalArgumentException("Scooter is not available")
        }
    }

    private fun checkIfBatteryIsNotZero(remainingBatteryPercent: Int) {
        if (remainingBatteryPercent <= 0) {
            throw IllegalArgumentException("Battery level is to low")
        }
    }

    private fun checkIfBalanceIsAboveMinimum(balance: BigDecimal) {
        if (balance <= BigDecimal.TEN) {
            throw IllegalArgumentException("User balance is less than 10 pln")
        }
    }


    internal fun getScooterFromSerialNumber(serialNumber: String) =
        scooterRepository.findBySerialNumber(serialNumber).takeIf { it != null }
            ?: throw NotFoundException("Scooter not found")


}