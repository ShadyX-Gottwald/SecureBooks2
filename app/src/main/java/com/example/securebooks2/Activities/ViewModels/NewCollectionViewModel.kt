package com.example.securebooks2.Activities.ViewModels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.CategoryServiceImpl
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Models.toMap
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage

class NewCollectionViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: StorageReference = FirebaseStorage.getInstance().reference,
    private val _service: CategoryServiceImpl = CategoryServiceImpl(auth,firestore , storage)

): ViewModel() {

    val result = MutableLiveData<Resource<Boolean>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }


    fun createCategory(category: Category,uri: Uri) {

        try{
           val  res = _service.uploadCategoryImage(uri).addOnCompleteListener{task->
               if(task.isSuccessful) {
                   val imageUrl = task.result
                 val created =   _service.createCategory(category , imageUrl.toString())
                   result.postValue(Resource.Success(true , "Failure Saving ,$category"))


               }

           }
          //  result.postValue(Resource.Success(true ,category.toString()))

        }catch(e:Exception){
           // val saved =_service.createCategory(category)
            result.postValue(Resource.Failure( "Failure Saving ,$category"))

        }

    }

    fun uploadImageAddCategory(image: Uri , category: Category) {
        result.postValue(Resource.Loading(false))

        try{
          val _result =  _service.uploadCategoryImage(image).addOnCompleteListener{task ->
              if (task.isSuccessful) {
                  val downloadUri = task.result
                 _service.createCategory(category,downloadUri.toString())


              } else {
                  // Handle failures
              }

          }

        }catch (e: Exception){

            result.postValue(Resource.Failure(e.message))

        }

    }
}