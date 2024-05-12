package com.example.securebooks2.Activities.Domain.Services.UsecaseImpl

import com.example.securebooks2.Activities.Constants.FirebaseConstants
import com.example.securebooks2.Activities.Domain.Services.BookServices
import com.example.securebooks2.Activities.Models.Book
import com.example.securebooks2.Activities.Models.toMap
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference

class BookServiceImpl(
    private var auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: StorageReference
): BookServices {

    override fun addBook(book: Book): Task<DocumentReference> {
       return  firestore.collection(FirebaseConstants.BOOK_COLLECTION)
            .add(book.toMap())
    }

    override fun getAllUserBooks(): Task<QuerySnapshot> {
        val user = auth.currentUser!!.uid
        return firestore.collection(FirebaseConstants.BOOK_COLLECTION)
            .where(Filter.equalTo("userId", user))
            .get()
    }
}


