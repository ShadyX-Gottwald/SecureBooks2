package com.example.securebooks2.Activities.Domain.Services

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface UserProfileServices {

    fun uploadProfileImage(uri: Uri): Task<Uri>
    fun getUserProfileDetails():Task<QuerySnapshot>
    //(CodingSTUFF ,2024)

    fun createUserProfile(imageUrl: String , userId: String)

}