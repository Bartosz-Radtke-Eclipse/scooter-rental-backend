package com.example.scooter.data.repository

import com.example.scooter.data.model.Serviceman
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServicemanRepository : JpaRepository<Serviceman, Long>{
    fun findByUser_UserName(userName: String): Serviceman?
}