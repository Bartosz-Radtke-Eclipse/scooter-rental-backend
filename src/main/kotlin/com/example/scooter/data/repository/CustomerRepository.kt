package com.example.scooter.data.repository

import com.example.scooter.data.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByUser_UserName(user_userName: String):Customer?
}