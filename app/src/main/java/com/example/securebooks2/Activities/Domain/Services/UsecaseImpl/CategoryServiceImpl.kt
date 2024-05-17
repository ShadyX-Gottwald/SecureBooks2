package com.example.securebooks2.Activities.Domain.Services.UsecaseImpl

import android.net.Uri
import com.example.securebooks2.Activities.Constants.FirebaseConstants
import com.example.securebooks2.Activities.Domain.Services.CategoryServices
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Models.toMap
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CategoryServiceImpl(
    //CodingSTUFF (2024)
    private var auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: StorageReference
): CategoryServices {

    var result = false


    override fun createCategory(category: Category, imageUrl: String): Boolean {
        result = false
        // Set image url to incoming Url of Image Storage
        category.imageUrl = imageUrl
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

    override fun uploadCategoryImage(uri: Uri?): Task<Uri> {
        //Set Image Title to Date
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss" , Locale.ENGLISH)
        val now = Date()
        val file = formatter.format(now)

        //Get Result of image upload

        return  uploadHelper(file , uri!!)
    }


    private fun uploadHelper(fileName: String , image: Uri): Task<Uri> {
        //Helper to upload image with instant date.
        val ref = storage.child("uploads/$fileName")
        val uploadTask = ref.putFile(image)

        val urlTask =
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })

        return urlTask
    }

    //Get image Url For book Display
    override fun getCategoryUrlForBook(category: String): Task<QuerySnapshot> {
      return firestore.collection(FirebaseConstants.BOOK_CATEGORY_COLLECTION)
           .where(Filter.equalTo("userId", auth.currentUser!!.uid))
           .where(Filter.equalTo("category", category))
           .get()
    }

}