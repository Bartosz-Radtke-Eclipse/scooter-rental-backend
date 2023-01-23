package com.example.scooter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScooterApplication

fun main(args: Array<String>) {
    runApplication<ScooterApplication>(*args)
}
