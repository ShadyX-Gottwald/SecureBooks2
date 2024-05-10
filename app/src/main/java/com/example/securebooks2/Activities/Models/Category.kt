package com.example.securebooks2.Activities.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.UUID


@Parcelize
data class Category(
    var categoryId: String,
    var userId: String,
    var imageUrl: String,
    var categoryTitle: String,
    var categoryTargetNum: Int

) : Parcelable {
    constructor(): this(UUID.randomUUID().toString(),""
        ,"" ,"",0)

    override fun toString(): String =  "$userId , $categoryTitle , $categoryTargetNum"

}

fun Category.toMap(): HashMap<String,Any> {
    val categoryMap: HashMap<String,Any> = hashMapOf(
        "categoryId" to categoryId,
        "userId" to userId,
        "categoryTitle" to categoryTitle ,
        "categoryTargetNum" to categoryTargetNum  ,
        "imageUrl" to imageUrl

    )
    return categoryMap
}