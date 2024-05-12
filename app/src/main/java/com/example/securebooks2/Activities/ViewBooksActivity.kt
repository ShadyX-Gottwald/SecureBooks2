package com.example.securebooks2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.securebooks2.Activities.Adapters.BookAdapter
import com.example.securebooks2.Activities.Adapters.CategoryAdapter
import com.example.securebooks2.Activities.Models.Book
import com.example.securebooks2.Activities.Utilities.Resource
import com.example.securebooks2.Activities.ViewModels.MyCollectionViewModel
import com.example.securebooks2.Activities.ViewModels.ViewBooksViewModel
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityViewBooksBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewBooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBooksBinding
    private lateinit var _adapter: BookAdapter
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewModel by lazy{ ViewBooksViewModel(auth,firestore) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpObservers()
        viewModel.getLoggedUserBooks()
    }

    private fun setUpObservers() {
        viewModel.getBookState.observe(this , Observer {
            when(it){
                is Resource.Loading -> {
                    Toast.makeText(this,"Loading"
                        , Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    setDataToRecView(it.data!!)
                    Toast.makeText(this,"Success Getting Data"
                        , Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(this,it.message
                        , Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })
    }

    private fun setDataToRecView(books: MutableList<Book>) {
        _adapter = BookAdapter(books,auth,firestore)
        binding.recView.adapter = _adapter
        binding.recView.setHasFixedSize(false)

    }
}