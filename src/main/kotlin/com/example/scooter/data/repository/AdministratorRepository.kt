package com.example.scooter.data.repository

import com.example.scooter.data.model.Administrator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdministratorRepository : JpaRepository<Administrator, Long>{
    fun existsByUser_UserName(userName:String): Boolean
}