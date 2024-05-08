package com.example.securebooks2.Activities.Domain.Services

import com.example.securebooks2.Activities.Models.Category
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface CategoryServices {

    fun createCategory(category: Category):Boolean

    fun getUserCategories(): Task<QuerySnapshot>
}