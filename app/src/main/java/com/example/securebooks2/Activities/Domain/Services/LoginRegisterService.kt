package com.example.securebooks2.Activities.Domain.Services

import com.example.securebooks2.Activities.Models.RegisterModel

interface LoginRegisterService {

    fun signUpToFirebase(user: RegisterModel) : Boolean?
    fun loginToFirebase()
}