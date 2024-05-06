package com.example.securebooks2.Activities.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.CategoryServiceImpl
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Models.toMap
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class NewCollectionViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val _service: CategoryServiceImpl = CategoryServiceImpl(auth,firestore)

): ViewModel() {

    val result = MutableLiveData<Resource<Boolean>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }


    fun createCategory(category: Category) {

        try{
             _service.createCategory(category)
            result.postValue(Resource.Success(true ,category.toString()))

        }catch(e:Exception){
            val saved =_service.createCategory(category)
            result.postValue(Resource.Success(false , "Failure Saving ,$category"))

        }

    }
}