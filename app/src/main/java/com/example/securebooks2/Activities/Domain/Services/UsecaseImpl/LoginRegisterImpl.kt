package com.example.securebooks2.Activities.Domain.Services.UsecaseImpl

import com.example.securebooks2.Activities.Domain.Services.LoginRegisterService
import com.example.securebooks2.Activities.Models.RegisterModel
import com.google.firebase.auth.FirebaseAuth

class LoginRegisterImpl(
    private val auth: FirebaseAuth
) :LoginRegisterService {

        var result: Boolean? = null

    override fun signUpToFirebase(user: RegisterModel): Boolean?{
        result = null

        auth.createUserWithEmailAndPassword(user.email,user.password)
            .addOnCompleteListener {
            if(it.isSuccessful) {
                result = true
                return@addOnCompleteListener
            }
        }.addOnFailureListener{
            result =false
            return@addOnFailureListener
        }
        return result
    }

    override fun loginToFirebase() {
        TODO("Not yet implemented")
    }
}