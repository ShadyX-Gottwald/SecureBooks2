package com.example.securebooks2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.securebooks2.Activities.Models.Book
import com.example.securebooks2.Activities.Utilities.DateUtils
import com.example.securebooks2.Activities.Utilities.Resource
import com.example.securebooks2.Activities.ViewModels.AddBookViewModel
import com.example.securebooks2.Activities.ViewModels.NewCollectionViewModel
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityAddBookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _viewModel by lazy{ AddBookViewModel(auth,firestore) }
    private var Category =""
    private var ImageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpStateObserver()
        setUpClickListeners()

        var bundle : Bundle? = intent.extras

        //Getting Data sent From previous Activity
        val category = bundle!!.getString(MyCollectionActivity.CATEGORY)
        Category = category!!

        //Get url For Image
        val imageUrl = bundle.getString(MyCollectionActivity.IMAGE_URL)
        ImageUrl = imageUrl!!

        binding.tvCategory.text = category

        Toast.makeText(this, ImageUrl , Toast.LENGTH_LONG).show()

    }

    fun setUpStateObserver() {

        //Observe state Success Failure or Loading DB Access
        _viewModel.result.observe(this, Observer {state ->
            when(state) {
                is Resource.Loading -> {

                }
                is Resource.Failure -> {}
                is Resource.Success -> {
                    Toast.makeText(this@AddBookActivity,
                        state.message, Toast.LENGTH_SHORT).show()

                }
                else -> {}
            }
        })

    }

    fun setUpClickListeners() {
        binding.saveBtn.setOnClickListener{

            //Capture info
            val title = binding.etTitle.text.toString()
            val desc = binding.etDescription.text.toString()
            val date = binding.etDate.text.toString()

            val bookObj = Book().apply {
                this.bookTitle = title
                this.userId = auth.currentUser!!.uid.toString()
                this.category = Category
                this.description = desc
                this.date = date
                this.imageUrl = ImageUrl

            }
            if(!DateUtils.validDateMatch(bookObj.date)) {
                Toast.makeText(this@AddBookActivity,
                    "Please Enter Valid date(dd/MM/yyyy", Toast.LENGTH_SHORT).show()

            }
            //No EmptyFields Check and Valid date Format
            if(bookObj.hasNoEmptyFields() &&
                DateUtils.validDateMatch(bookObj.date)) {
                //Adding Book
                _viewModel.addBook(bookObj)
                //Clear Fields after Saving
                clearFields()


            }else {
                Toast.makeText(this@AddBookActivity,
                    "Please Enter All Fields.", Toast.LENGTH_SHORT).show()

            }


        }
    }

    private fun clearFields() {
        binding.etTitle.text = null
        binding.etDescription.text= null
        binding.etDate.text= null

    }
}