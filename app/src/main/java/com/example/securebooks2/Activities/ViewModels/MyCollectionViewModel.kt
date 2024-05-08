package com.example.securebooks2.Activities.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.CategoryServiceImpl
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private val TAG = "MyCollection VM TAG"

class MyCollectionViewModel(
    private val  _auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val _firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private var _service: CategoryServiceImpl = CategoryServiceImpl(_auth,_firestore)

): ViewModel() {

    val getCategoryState = MutableLiveData<Resource<MutableList<Category>>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }
    init{
        getUserCategories()
    }

    fun getUserCategories() {
        getCategoryState.postValue(Resource.Loading(mutableListOf()))
        try{
            val categories = _service.getUserCategories()
            categories.addOnSuccessListener {
                val data =   it.toObjects(Category::class.java).toMutableList()

                Log.d(TAG , data.size.toString())
                Log.d(TAG , data.joinToString { " ," })

                getCategoryState.postValue(Resource.Success(data))

            }
        }catch (e: Exception) {
            getCategoryState.postValue(Resource.Failure("Failed to Get User Categories"))
        }
    }


}