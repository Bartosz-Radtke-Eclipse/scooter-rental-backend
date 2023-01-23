package com.example.scooter.services

import com.example.scooter.data.dto.CustomerBalanceDto
import com.example.scooter.data.repository.CustomerRepository
import com.example.scooter.exceptions.NotFoundException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CustomerService(private val customerRepository: CustomerRepository) {
    fun addUserBalance(userName: String, moneyToAdd: BigDecimal): CustomerBalanceDto {
        val customer = getCustomerByUserName(userName)
        customer.balance = customer.balance.plus(moneyToAdd)
        customerRepository.save(customer)
        return CustomerBalanceDto(customer.balance, userName)
    }

    internal fun getCustomerByUserName(userName: String) =
        (customerRepository.findByUser_UserName(userName).takeIf { it != null }
            ?: throw NotFoundException("User not found"))
}