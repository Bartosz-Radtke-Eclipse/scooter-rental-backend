package com.example.scooter.controllers.exceptionhandler

import com.example.scooter.exceptions.AccessDeniedException
import com.example.scooter.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ScooterExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message ?: "Not found")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotEnoughBalanceException(exception: IllegalArgumentException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message ?: "Bad Request")
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(exception: AccessDeniedException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(exception.message)
    }
}