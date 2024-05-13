package com.example.securebooks2.Activities.ViewModels

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.CategoryServiceImpl
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.UserProfileServiceImpl
import com.example.securebooks2.Activities.Models.UserProfile
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileViewModel(
    //Land of coding(2024)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: StorageReference = FirebaseStorage.getInstance().reference,
    private var _service: UserProfileServiceImpl = UserProfileServiceImpl(auth,firestore,storage)

): ViewModel() {

    init{
        getUserProfile()

    }
    val result = MutableLiveData<Resource<Boolean>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }
    val user_exists = MutableLiveData<Resource<UserProfile>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }

    //Land of coding(2024)
    @SuppressLint("SuspiciousIndentation")
    fun getUserProfile() {
        try{
            // Get User Profile, Empty Default Values.
            //Land of coding(2024)
          val res =   _service.getUserProfileDetails()  .addOnSuccessListener {
              if(it.isEmpty){
                  user_exists.postValue(Resource.Loading(UserProfile()))


              }
              else{
               val user =   it.toObjects<UserProfile>()[0]
                  user_exists.postValue(Resource.Success(user , "User Exists!"))
              }
          }



        }catch(e: Exception) {

        }


    }

    fun createUserProfile(uri: Uri){
        try{
          val res =   _service.uploadProfileImage(uri)
              .addOnSuccessListener {

                  _service.createUserProfile(it.toString(),auth!!.currentUser!!.uid)
                  result.postValue(Resource.Success(true , "Profile Created"))

          }

        }catch (e: Exception) {
            result.postValue(Resource.Failure("Error Could not create Profile."))

        }

    }

}