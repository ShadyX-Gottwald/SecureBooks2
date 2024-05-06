package com.example.securebooks2.Activities.Models

data class RegisterModel(
    val email: String = "" ,
    val password: String = "" ,
    val passConfirm: String = ""
)

fun RegisterModel.hasEmptyFields(): Boolean {
    //Returns true if fields empty
    return (this.email.isEmpty() ||
            this.password.isEmpty()
            || this.passConfirm.isEmpty())

}
fun RegisterModel.passwordMatchesConfirm(): Boolean {
    return (this.password.equals(passConfirm))

}