package com.example.securebooks2.Activities.Domain.Services.UsecaseImpl

import com.example.securebooks2.Activities.Constants.FirebaseConstants
import com.example.securebooks2.Activities.Domain.Services.CategoryServices
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Models.toMap
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class CategoryServiceImpl(
    private var auth: FirebaseAuth,
    private  val firestore: FirebaseFirestore
): CategoryServices {

    var result = false
    override fun createCategory(category: Category): Boolean{
        result = false
        firestore.collection(FirebaseConstants.BOOK_CATEGORY_COLLECTION)
            .add(category.toMap())
            .addOnCompleteListener {
                this.result = true
            }.addOnFailureListener {
                this.result = false
            }
        return result

    }

    override fun getUserCategories(): Task<QuerySnapshot>
       = firestore.collection(FirebaseConstants.BOOK_CATEGORY_COLLECTION)
            .where(Filter.equalTo("userId", auth.currentUser!!.uid))
            .get()

}