package com.example.securebooks2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.securebooks2.Activities.Adapters.BookAdapter
import com.example.securebooks2.Activities.Models.Book
import com.example.securebooks2.Activities.Utilities.Resource
import com.example.securebooks2.Activities.ViewModels.CategoryBooksViewModel
import com.example.securebooks2.Activities.ViewModels.MyCollectionViewModel
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityCategoryBooksBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryBooksActivity : AppCompatActivity() {
    private lateinit var bind: ActivityCategoryBooksBinding
    private lateinit var _adapter: BookAdapter
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewModel by lazy{ CategoryBooksViewModel(auth,firestore) }
    private var Category =""
    private var TargetNum =""
    private var ImageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCategoryBooksBinding.inflate(layoutInflater)
        setContentView(bind.root)
        var bundle : Bundle? = intent.extras

        //Getting Data sent From previous Activity
        val category = bundle!!.getString(MyCollectionActivity.CATEGORY)
        Category = category!!

        //Get url For Image
        val imageUrl = bundle!!.getString(MyCollectionActivity.IMAGE_URL)
        ImageUrl = imageUrl!!
        TargetNum = bundle!!.getString(MyCollectionActivity.CATEGORY_TARGET_NUM).toString()

        bind.tcCategory.text = category

        setUpObservers()
        setClickListeners()

        //Get Data Initially
        viewModel.getBooksByCategory(category)
        setUpCountObservers()
        viewModel.countBooksInCategory(category)

    }

    private fun setUpObservers() {
        viewModel.getBookState.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    Toast.makeText(this,"Success Getting Data"
                        , Toast.LENGTH_SHORT).show()

                    setDataToRecView(it.data!!)
                }
                is Resource.Failure -> {
                    Toast.makeText(this,it.message
                        , Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(this,"Loading"
                        , Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        })
    }

    private fun setClickListeners() {

        bind.newItemFab.setOnClickListener {
            //Pass Book Limit and Category Title.
            val intentAddBook = Intent(this, AddBookActivity::class.java)
            // Passing the data to the next activity

            intentAddBook.putExtra(MyCollectionActivity.IMAGE_URL,ImageUrl)

            intentAddBook.putExtra(MyCollectionActivity.CATEGORY,Category)
            intentAddBook.putExtra(MyCollectionActivity.CATEGORY_TARGET_NUM, TargetNum)

            startActivity(intentAddBook)
        }

    }

    private fun setDataToRecView(books: MutableList<Book>) {
        _adapter = BookAdapter(books,auth,firestore)
        bind.recView.adapter = _adapter //Land of coding(2024)
        bind.recView.setHasFixedSize(false)

    }

    private fun setUpCountObservers() {
        viewModel.count.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    bind.progressBar1.max = TargetNum.toInt()
                    bind.progressBar1.setProgress(it.data!! , true)

                }
                is Resource.Failure -> {
                    Toast.makeText(this,it.message
                        , Toast.LENGTH_SHORT).show()

                }

                else -> {}
            }
        })
    }
}