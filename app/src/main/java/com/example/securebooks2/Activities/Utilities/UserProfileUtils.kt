package com.example.securebooks2.Activities.Utilities

class UserProfileUtils {


    companion object{

        fun getUsername(email: String): String {
            val split = email.split("@")
            return split[0]

        }
    }
}