package com.example.securebooks2.Activities.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.securebooks2.Activities.Domain.Services.OnItemClickListener
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.databinding.EachCategoryItemBinding
import com.example.securebooks2.databinding.EachCategoryItemBinding.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryAdapter(
    private val list: List<Category>?,
    private val auth: FirebaseAuth,
    private val firestoreDB: FirebaseFirestore
): RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder>()
    ,OnItemClickListener<Category> {

    private lateinit var bind: EachCategoryItemBinding
    private var onClickListener: OnClickListener? = null
    inner class CategoryAdapterViewHolder(eachTodoBinding: EachCategoryItemBinding):
        RecyclerView.ViewHolder(eachTodoBinding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        val binding = inflate(LayoutInflater.from(parent.context), parent , false)

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
            onItemClicked(list[position])


        }

        bind.bookImg.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, categoryItem )
            }
        }
    }


    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Category)
    }
    override fun onItemClicked(item: Category) {
        //val intent: Intent = Intent( , DummyActivity::class.java)
       // intent.
    }


}