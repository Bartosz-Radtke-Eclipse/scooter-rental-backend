package com.example.scooter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<ScooterApplication>(*args)
}

@SpringBootApplication
class ScooterApplication {

}
