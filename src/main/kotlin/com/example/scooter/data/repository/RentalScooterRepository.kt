package com.example.scooter.data.repository

import com.example.scooter.data.model.Customer
import com.example.scooter.data.model.RentalScooter
import com.example.scooter.data.model.Scooter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentalScooterRepository : JpaRepository<RentalScooter, Long>{
    fun findByCustomer_User_UserNameAndScooter_SerialNumber(
        customer_user_userName: String,
        scooter_serialNumber: String
    ): RentalScooter?
    fun existsByScooter(scooter: Scooter): Boolean

    fun findAllByCustomer(customer: Customer): List<RentalScooter>
}