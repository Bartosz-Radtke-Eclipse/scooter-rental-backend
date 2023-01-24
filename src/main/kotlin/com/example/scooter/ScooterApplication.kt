package com.example.scooter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

fun main(args: Array<String>) {
    runApplication<ScooterApplication>(*args)
}

@SpringBootApplication
class ScooterApplication {


    @Bean
    fun corsFilter(): CorsFilter {
        val urlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowCredentials = true
        corsConfiguration.allowedOrigins = listOf(
            "http://localhost:3000"
        )

        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)
        return CorsFilter(urlBasedCorsConfigurationSource)
    }
}
