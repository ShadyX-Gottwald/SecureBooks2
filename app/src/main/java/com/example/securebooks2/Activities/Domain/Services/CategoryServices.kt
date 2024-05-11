package com.example.securebooks2.Activities.Domain.Services

import android.net.Uri
import com.example.securebooks2.Activities.Models.Category
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface CategoryServices {

    fun createCategory(category: Category , imageUrl : String):Boolean

    fun getUserCategories(): Task<QuerySnapshot>

    fun uploadCategoryImage(uri: Uri?): Task<Uri>

    fun getCategoryUrlForBook(category: String): Task<QuerySnapshot>
}