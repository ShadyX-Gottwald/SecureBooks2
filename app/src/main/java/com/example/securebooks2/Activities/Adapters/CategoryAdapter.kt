package com.example.securebooks2.Activities.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.databinding.EachCategoryItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryAdapter(
    private val list: List<Category>?,
    private val auth: FirebaseAuth,
    private val firestoreDB: FirebaseFirestore
): RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder>() {

    private lateinit var bind: EachCategoryItemBinding
    inner class CategoryAdapterViewHolder(eachTodoBinding: EachCategoryItemBinding):
        RecyclerView.ViewHolder(eachTodoBinding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        val binding = EachCategoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent , false)

        bind = binding
        return CategoryAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: CategoryAdapterViewHolder, position: Int) {
        //Set Bind to UI Respective UI Elements
        val categoryItem = list!![position]
        bind.materialTextView.text = categoryItem.categoryTitle
        bind.bookImg.setOnClickListener {

        }
    }



}