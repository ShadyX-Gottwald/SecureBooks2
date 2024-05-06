package com.example.securebooks2.Activities.Domain.Services

import com.example.securebooks2.Activities.Models.LoginModel
import com.example.securebooks2.Activities.Models.RegisterModel
import com.google.firebase.auth.FirebaseUser

interface LoginRegisterService {

    fun signUpToFirebase(user: RegisterModel) : Boolean?
    fun loginToFirebase(user: LoginModel): FirebaseUser?
}