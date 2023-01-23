package com.example.scooter.services

import com.example.scooter.data.dto.CustomerBalanceDto
import com.example.scooter.data.dto.CustomerInfoDto
import com.example.scooter.data.dto.RentedScooterDto
import com.example.scooter.data.repository.CustomerRepository
import com.example.scooter.data.repository.RentalScooterRepository
import com.example.scooter.exceptions.NotFoundException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CustomerService(private val customerRepository: CustomerRepository, private val rentalScooterRepository: RentalScooterRepository) {
    fun addUserBalance(userName: String, moneyToAdd: BigDecimal): CustomerBalanceDto {
        val customer = getCustomerByUserName(userName)
        customer.balance = customer.balance.plus(moneyToAdd)
        customerRepository.save(customer)
        return CustomerBalanceDto(customer.balance, userName)
    }

    fun getCustomerInfo(userName: String): CustomerInfoDto {
        val customer = getCustomerByUserName(userName)
        val rentedScooters = rentalScooterRepository.findAllByCustomer(customer).map { RentedScooterDto(it.scooter.serialNumber, it.rentalDate) }
        return CustomerInfoDto(customer.balance, customer.user.userName, rentedScooters)

    }

    internal fun getCustomerByUserName(userName: String) =
        (customerRepository.findByUser_UserName(userName).takeIf { it != null }
            ?: throw NotFoundException("User not found"))
}