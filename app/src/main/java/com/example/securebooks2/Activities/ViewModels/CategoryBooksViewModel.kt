package com.example.securebooks2.Activities.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securebooks2.Activities.Domain.Services.UsecaseImpl.BookServiceImpl
import com.example.securebooks2.Activities.Models.Book
import com.example.securebooks2.Activities.Utilities.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CategoryBooksViewModel(
    //Land of coding(2024)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: StorageReference = FirebaseStorage.getInstance().reference,
    private var _service: BookServiceImpl = BookServiceImpl(auth,firestore,storage)

): ViewModel() {

    val getBookState = MutableLiveData<Resource<MutableList<Book>>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }

    val count = MutableLiveData<Resource<Int>>(Resource.Unspecified()).apply {
        this.value = Resource.Unspecified()
    }

    fun getBooksByCategory(category: String) {
        try{
            getBookState.postValue(Resource.Loading(mutableListOf()))
            //Get Book from service Logic
            //Land of coding(2024)
            val result =   _service.getUserBooksByCategory(category)
            result.addOnSuccessListener {
                val books = it.toObjects(Book::class.java)
                getBookState.postValue(Resource.Success(books))
            }


        }catch(e: Exception) {
            getBookState.postValue(Resource.Failure( "Failed  Getting Books!"))

        }
    }

    fun countBooksInCategory(category: String) {

        try {
            _service.getUserBooksCountByCategory(category).addOnSuccessListener {
                val books = it.toObjects(Book::class.java).toMutableList()

                //Post count value
                count.postValue(Resource.Success(books.size))

            }

        }catch (e: Exception) {

            count.postValue(Resource.Failure("Error counting books. Try Again Later"))

        }
    }
}