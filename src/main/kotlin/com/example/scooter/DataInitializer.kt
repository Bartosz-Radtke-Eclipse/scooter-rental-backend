package com.example.scooter

import com.example.scooter.data.model.*
import com.example.scooter.data.repository.*
import jakarta.annotation.PostConstruct
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataInitializer(
    private val administratorRepository: AdministratorRepository,
    private val customerRepository: CustomerRepository,
    private val hiddenScooterRepository: HiddenScooterRepository,
    private val rentalScooterRepository: RentalScooterRepository,
    private val scooterRepository: ScooterRepository,
    private val servicemanRepository: ServicemanRepository,
) {
    @PostConstruct
    private fun initializeData() {
        val admin = User(
            userName = "admin",
            password = "admin",
            name = "admin",
            surname = "admin",
            mail = "admin@mail.com",
            phone = 123456789
        )

        val customer = User(
            userName = "customer",
            password = "customer",
            name = "customer",
            surname = "customer",
            mail = "customer@mail.com",
            phone = 123456789
        )

        val serviceman = User(
            userName = "serviceman",
            password = "serviceman",
            name = "serviceman",
            surname = "serviceman",
            mail = "serviceman@mail.com",
            phone = 123456789
        )

        val scooter1 = Scooter(
            model = "Ninebot E45D",
            color = "green",
            maxSpeed = 20,
            manufacturer = "Segway",
            serialNumber = "1337",
            batteryMeters = 45_000,
            isHidden = false,
            scooterStatus = ScooterStatus(
                remainingBatteryPercent = 100,
                locLat = ("51.109483"),
                locLength = ("17.060208")
            )
        )

        val scooter3 = Scooter(
            model = "Ninebot F40D",
            color = "blue",
            maxSpeed = 20,
            manufacturer = "Segway",
            serialNumber = "2337",
            batteryMeters = 40_000,
            isHidden = false,
            scooterStatus = ScooterStatus(
                remainingBatteryPercent = 100,
                locLat = "51.111039",
                locLength = "17.059961"
            )
        )

        val scooter2 = Scooter(
            model = "Ninebot AIR T15E",
            color = "black",
            maxSpeed = 20,
            manufacturer = "Segway",
            serialNumber = "3337",
            batteryMeters = 12_000,
            isHidden = false,
            scooterStatus = ScooterStatus(
                remainingBatteryPercent = 100,
                locLat = ("51.109712"),
                locLength = ("17.053749")
            )
        )
        rentalScooterRepository.deleteAll()
        hiddenScooterRepository.deleteAll()
        scooterRepository.deleteAll()
        scooterRepository.saveAll(listOf(scooter1, scooter2, scooter3))

        administratorRepository.deleteAll()
        customerRepository.deleteAll()
        servicemanRepository.deleteAll()
        administratorRepository.save(Administrator(user = admin))
        customerRepository.save(Customer(user = customer, balance = BigDecimal.valueOf(100.00)))
        servicemanRepository.save(Serviceman(user = serviceman, carID = 1))
//        servicemanRepository.save(Serviceman(user = admin, carID = 2))

    }
}