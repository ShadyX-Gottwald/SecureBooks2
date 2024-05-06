package com.example.securebooks2.Activities.Domain.Services

import com.example.securebooks2.Activities.Models.Category

interface CategoryServices {

    fun createCategory(category: Category):Boolean
}