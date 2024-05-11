package com.example.securebooks2.Activities.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.BookServiceImpl
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.CategoryServiceImpl
import com.example.securebooks2.Activities.Models.Book
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddBookViewModel(
    private val  _auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val _firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val _storage: StorageReference = FirebaseStorage.getInstance().reference,
    private var _service: BookServiceImpl = BookServiceImpl(_auth,_firestore,_storage)

): ViewModel() {

    val result = MutableLiveData<Resource<Book>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }

    fun addBook(book: Book) {
        result.postValue(Resource.Loading(Book()))

        try {
           val entry =  _service.addBook(book)
           val msg = "${book.bookTitle} saved successfully."
           result.postValue(Resource.Success(book ,msg))


        }catch (e: Exception){
            result.postValue(Resource.Failure("Error saving ${book.bookTitle}"))

        }

    }


}