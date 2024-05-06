package com.example.securebooks2.Activities.Domain.Services.UsecaseImpl

import com.example.securebooks2.Activities.Domain.Services.LoginRegisterService
import com.example.securebooks2.Activities.Models.LoginModel
import com.example.securebooks2.Activities.Models.RegisterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginRegisterImpl(
    private val auth: FirebaseAuth
) :LoginRegisterService {

        var result: Boolean? = null
    var login_result: FirebaseUser? = null

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

    override fun loginToFirebase(user: LoginModel): FirebaseUser? {
        auth
          .signInWithEmailAndPassword(user.email ,user.password).addOnCompleteListener {
             if(it.isSuccessful) {
                login_result =  it.result.user
             }

          }.addOnCompleteListener {

            }

        return login_result

    }
}