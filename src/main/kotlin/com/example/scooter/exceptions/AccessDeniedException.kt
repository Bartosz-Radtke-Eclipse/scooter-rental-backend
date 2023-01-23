package com.example.scooter.exceptions

class AccessDeniedException(message: String = "User does not have sufficient permissions") : Exception(message) {
}