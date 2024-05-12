package com.example.securebooks2.Activities.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.securebooks2.Activities.Models.Book
import com.example.securebooks2.databinding.EachBookItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookAdapter(
    private val list: List<Book>?,
    private val auth: FirebaseAuth,
    private val firestoreDB: FirebaseFirestore
) : RecyclerView.Adapter<BookAdapter.BookAdapterViewHolder>(){

    private lateinit var bind: EachBookItemBinding
    private var onClickListener: CategoryAdapter.OnClickListener? = null

    inner class BookAdapterViewHolder(eachBookBinding: EachBookItemBinding):
        RecyclerView.ViewHolder(eachBookBinding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapterViewHolder {
        val binding =
            EachBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        bind = binding
        return BookAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: BookAdapterViewHolder, position: Int) {
        //Set Bind to UI Respective UI Elements
        val bookItem = list!![position]
        bind.materialTextView.text = bookItem.bookTitle

        //Bind image Url to Image View

        holder.apply {
            bind.apply {

                Glide.with(itemView.context).load(bookItem.imageUrl)
                    .into(bind.bookImg)
            }
        }

    }

}