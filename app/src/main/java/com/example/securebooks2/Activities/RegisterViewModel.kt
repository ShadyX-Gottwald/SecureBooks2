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
    //(CodingSTUFF, 2024)
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
/***
 * Reference List
 *
 *  * Firebase save User data. [online] youtube(Land of Coding).
 *  *  Available at:
 *  hhttps://www.youtube.com/watch?v=50uBQKWpDJk&list=PLzZEuVaFb9ExqUwxMoXg0Li0wYW2IeAkz&index=8
 *  [Accessed 10 May. 2024].
 *  *
 * * Firebase Upload Image Firestore. [online] youtube (Land of Coding).
 *  *  *  Available at:
 *  *  hhttps://https://www.youtube.com/watch?v=xk1BKoJ8Nk4&list=PLzZEuVaFb9ExqUwxMoXg0Li0wYW2IeAkz&index=15
 *  *  [Accessed 11 May. 2024].
 *
 *  * Upload and Retrieve Images ( Firebase Storage And URL to Cloud Firestore ). [online] youtube (CodingSTUFF).
 *  *  *  *  Available at: https://www.youtube.com/watch?v=toKt3LnsBWE&t=1458s
 *  *  *
 *  *  *  [Accessed 11 May. 2024].
 *
 *
 *   * To-Do App in Kotlin ( Firebase ) - Part 4. [online] youtube (CodingSTUFF).
 *  *  *  *  *  Available at:
 *  https://www.youtube.com/watch?v=KbdEiAlFfz4&list=RDCMUC5hwBZynOhshCbqTGGeoRSA&index=4
 *  *  *  *
 *  *  *  *  [Accessed 05 May. 2024].
 *
 * *   * To-Do App in Kotlin ( Firebase ) - Part 3. [online] youtube (CodingSTUFF).
 *  *    Available at:
 *  *  https:https://www.youtube.com/watch?v=lDQlf_qawFE&list=RDCMUC5hwBZynOhshCbqTGGeoRSA&index=3
 *  *[Accessed 05 May. 2024].
 *  *
 *
 * ****/
