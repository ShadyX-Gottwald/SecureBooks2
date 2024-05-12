package com.example.securebooks2.Activities.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.BookServiceImpl
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.UserProfileServiceImpl
import com.example.securebooks2.Activities.Models.Book
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

private val  TAG =  "Viewbooks VM "
class ViewBooksViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: StorageReference = FirebaseStorage.getInstance().reference,
    private var _service: BookServiceImpl = BookServiceImpl(auth,firestore,storage)

): ViewModel() {

    init{
        //getLoggedUserBooks()
    }

    val getBookState = MutableLiveData<Resource<MutableList<Book>>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }

    fun getLoggedUserBooks() {
        try{
            getBookState.postValue(Resource.Loading(mutableListOf()))
            //Get Book from service Logic
          val result =   _service.getAllUserBooks()
            result.addOnSuccessListener {
                val books = it.toObjects(Book::class.java)
                getBookState.postValue(Resource.Success(books))
            }


        }catch(e: Exception) {
            getBookState.postValue(Resource.Failure( "Failed  Getting Books!"))

        }

    }

}