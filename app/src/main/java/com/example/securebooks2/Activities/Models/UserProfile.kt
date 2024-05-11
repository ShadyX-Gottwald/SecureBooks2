package com.example.securebooks2.Activities.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    var userId: String ,
    var imageUrl: String
):  Parcelable {

    constructor() : this("" ,"")
}

fun UserProfile.toMap(): HashMap<String,Any> {

    return hashMapOf<String,Any>(
        "userId" to userId ,
        "imageUrl" to imageUrl
    )

}
