package com.example.securebooks2.Activities.Domain.Services.UsecaseImpl

import android.net.Uri
import com.example.securebooks2.Activities.Constants.FirebaseConstants
import com.example.securebooks2.Activities.Domain.Services.UserProfileServices
import com.example.securebooks2.Activities.Models.UserProfile
import com.example.securebooks2.Activities.Models.toMap
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserProfileServiceImpl(
    private var auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: StorageReference

): UserProfileServices {
    override fun uploadProfileImage(uri: Uri): Task<Uri> {
        //Set Image Title to Date
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss" , Locale.ENGLISH)
        val now = Date()
        val file = formatter.format(now) //(CodingSTUFF ,2024)
        return uploadHelper(file , uri)
    }

    override fun getUserProfileDetails(): Task<QuerySnapshot> {
        val userId = auth!!.currentUser!!.uid
        //Gets user Profile Data
       return firestore.collection(FirebaseConstants.PROFILE_COLLECTION)
           .where(Filter.equalTo("userId" ,
           userId )
       ).limit(1)
            .get()//(CodingSTUFF ,2024)
    }

    override fun createUserProfile(imageUrl: String, userId: String) {
        //Get Params to Create users Profile ,Upload Url and User Id
        val profileuser = UserProfile(userId , imageUrl).toMap()
        firestore.collection(FirebaseConstants.PROFILE_COLLECTION)
            .add(profileuser)//(CodingSTUFF ,2024)

    }

    //hepler to Upload userImage to Firstore
    private fun uploadHelper(fileName: String , image: Uri): Task<Uri> {
        //Helper to upload image with instant date.
        val ref = storage.child("ProfileUploads/$fileName")
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

        return urlTask //(CodingSTUFF ,2024)
    }
}