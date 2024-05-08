package com.example.securebooks2.Activities.Adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.databinding.EachCategoryItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryAdapter(
    private val list: List<Category>?,
    private val auth: FirebaseAuth,
    private val firestoreDB: FirebaseFirestore
) {

    private lateinit var bind: EachCategoryItemBinding
    inner class CategoryAdapterViewHolder(eachTodoBinding: EachCategoryItemBinding):
        RecyclerView.ViewHolder(eachTodoBinding.root) {}
}