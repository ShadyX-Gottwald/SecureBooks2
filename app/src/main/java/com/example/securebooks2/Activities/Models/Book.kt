package com.example.securebooks2.Activities.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
data class Book(
    val bookTitle: String ,
    val category: String ,
    val date: Date ,
    val imageUrl: String
) : Parcelable{

    constructor(): this ("" , "" , Date() ,"")
}

