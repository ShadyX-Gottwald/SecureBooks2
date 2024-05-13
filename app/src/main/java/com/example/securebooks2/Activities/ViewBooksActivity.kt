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

/***
 * Reference List
 *
 *  * Firebase save User data. [online] youtube(Land of Coding).
 *  *  Available at:
 *  hhttps://www.youtube.com/watch?v=50uBQKWpDJk&list=PLzZEuVaFb9ExqUwxMoXg0Li0wYW2IeAkz&index=8
 *  [Accessed 10 May. 2024].
 *  *
 * * Firebase Upload Image Firestore. [online] youtube (Land of Coding).
 *  *  *  Available at:
 *  *  hhttps://https://www.youtube.com/watch?v=xk1BKoJ8Nk4&list=PLzZEuVaFb9ExqUwxMoXg0Li0wYW2IeAkz&index=15
 *  *  [Accessed 11 May. 2024].
 *
 *  * Upload and Retrieve Images ( Firebase Storage And URL to Cloud Firestore ). [online] youtube (CodingSTUFF).
 *  *  *  *  Available at: https://www.youtube.com/watch?v=toKt3LnsBWE&t=1458s
 *  *  *
 *  *  *  [Accessed 11 May. 2024].
 *
 *
 *   * To-Do App in Kotlin ( Firebase ) - Part 4. [online] youtube (CodingSTUFF).
 *  *  *  *  *  Available at:
 *  https://www.youtube.com/watch?v=KbdEiAlFfz4&list=RDCMUC5hwBZynOhshCbqTGGeoRSA&index=4
 *  *  *  *
 *  *  *  *  [Accessed 05 May. 2024].
 *
 * *   * To-Do App in Kotlin ( Firebase ) - Part 3. [online] youtube (CodingSTUFF).
 *  *  *  *  *  *  Available at:
 *  *  https:https://www.youtube.com/watch?v=lDQlf_qawFE&list=RDCMUC5hwBZynOhshCbqTGGeoRSA&index=3
 *  *  *  *  *
 *  *  *  *  *  [Accessed 05 May. 2024].
 *  *
 *
 * ****/


class ViewBooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBooksBinding
    private lateinit var _adapter: BookAdapter
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewModel by lazy{ ViewBooksViewModel(auth,firestore) }  //Land of coding(2024)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpObservers()
        viewModel.getLoggedUserBooks()
    }

    private fun setUpObservers() {
        //Land of coding(2024)
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
        binding.recView.adapter = _adapter //Land of coding(2024)
        binding.recView.setHasFixedSize(false)

    }
}