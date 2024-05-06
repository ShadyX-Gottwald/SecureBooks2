package com.example.securebooks2.Activities.Models

data class LoginModel(
    val email: String,
    val password: String
)

fun LoginModel.hasEmptyField(): Boolean {
    return (this.email.isEmpty() || this.password.isEmpty())
}