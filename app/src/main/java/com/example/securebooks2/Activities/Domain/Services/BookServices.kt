package com.example.securebooks2.Activities.Domain.Services

import com.example.securebooks2.Activities.Models.Book
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

interface BookServices {

    fun addBook(book: Book): Task<DocumentReference>
}