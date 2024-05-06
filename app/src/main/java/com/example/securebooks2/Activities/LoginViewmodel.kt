package com.example.securebooks2.Activities

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securebooks2.Activities.Domain.Services.LoginRegisterService
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.LoginRegisterImpl
import com.example.securebooks2.Activities.Models.LoginModel
import com.example.securebooks2.Activities.Models.hasEmptyField
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewmodel (
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val _service: LoginRegisterImpl = LoginRegisterImpl(auth)
): ViewModel() {

    //Init value , not logged in state.
    val loginState = MutableLiveData<Resource<FirebaseUser?>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }

    fun loginUser(user: LoginModel) {
        val SUCCESS_MESSAGE = "${user.email} , Login Successful."


        if(user.hasEmptyField()) {
            loginState.postValue(Resource.Failure("Fields Cannot be Empty."))

        }

        else {
            try{


                    val validUser =  _service.loginToFirebase(user)
                    if(validUser != null) {
                        loginState.postValue(Resource.Success(validUser ,SUCCESS_MESSAGE))
                        Log.d("Login VM " , validUser.uid)
                    }
                    else {
                        //Log.d("Login VM " , validUser)

                        throw Exception("User Does Not Exist")
                    }


            }catch(e: Exception){
                loginState.postValue(Resource.Failure(e.message))

            }

        }

    }


}