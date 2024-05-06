package com.example.securebooks2.Activities

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securebooks2.Activities.Domain.Services.LoginRegisterService
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.LoginRegisterImpl
import com.example.securebooks2.Activities.Models.RegisterModel
import com.example.securebooks2.Activities.Models.hasEmptyFields
import com.example.securebooks2.Activities.Models.passwordMatchesConfirm
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel(
    private val _auth: FirebaseAuth   ,
    private val _service: LoginRegisterImpl= LoginRegisterImpl(_auth)

) : ViewModel() {

    val registerState = MutableLiveData<Resource<Boolean>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }


    @SuppressLint("SuspiciousIndentation")
     fun registerUser(user: RegisterModel) {

        registerState.postValue(Resource.Loading(false))

        val SUCCESS_MESSAGE = "${user.email} Signed up SuccessFully"
        //Not Complete Fields Result exception
        if(user.hasEmptyFields() ) {
            registerState.postValue(Resource.Failure("Fields Cannot Be Empty."))

        }
        else if(user.passwordMatchesConfirm()) {
            //Sign up user to Firebase
          val signUp = _service.signUpToFirebase(user)

            when(signUp) {
                 true,null -> {
                     registerState
                         .postValue(Resource.Success(true, SUCCESS_MESSAGE))
                 }
                false -> {
                    registerState.postValue(Resource.Failure("Failure Signing Up."))
                }

            }



        }

    }
}