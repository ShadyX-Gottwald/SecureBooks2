package com.example.securebooks2.Activities.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
data class Book(
    var bookTitle: String,
    var category: String,
    var userId: String,
    var description: String,
    var date: String,
    var imageUrl:String

    ) : Parcelable{

    constructor(): this ( "" ,"","" ,"" ,"","")

    fun hasNoEmptyFields(): Boolean {
        return (date.isNotEmpty()
                && bookTitle.isNotEmpty()
                && category.isNotEmpty())
    }


}
fun Book.toMap(): HashMap<String,Any> {
    val map = hashMapOf<String,Any>(
        "bookTitle" to bookTitle ,
        "category" to category,
        "userId" to userId,
        "description" to description,
        "date" to date ,
        "imageUrl" to imageUrl

    )
    return map
}

