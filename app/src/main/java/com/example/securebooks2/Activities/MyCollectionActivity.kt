package com.example.securebooks2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.securebooks2.Activities.Adapters.CategoryAdapter
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Utilities.Resource
import com.example.securebooks2.Activities.ViewModels.MyCollectionViewModel
import com.example.securebooks2.databinding.ActivityMyCollectionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyCollectionActivity : AppCompatActivity() {
    private lateinit var  bind: ActivityMyCollectionBinding
    private lateinit var _adapter: CategoryAdapter
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewModel by lazy{ MyCollectionViewModel(auth,firestore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMyCollectionBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setClickListener()
        setUpGetCategoryObservers()
    }

    private fun setClickListener() {

        bind.newItemFab.setOnClickListener {
            val newCollectionAct = Intent(this@MyCollectionActivity , NewCollectionActivity::class.java)
            startActivity(newCollectionAct)

        }
    }

    private fun setDataToRecView(categories: MutableList<Category>?) {
        _adapter = CategoryAdapter(categories,auth,firestore)
        bind.recView.adapter = _adapter
        bind.recView.setHasFixedSize(false)
        _adapter.setOnClickListener(object : CategoryAdapter.OnClickListener{
            override fun onClick(position: Int, model: Category) {
                val intent = Intent(this@MyCollectionActivity, DummyActivity::class.java)
                // Passing the data to the
                // EmployeeDetails Activity
                intent.putExtra(CATEGORY, categories?.get(position)?.categoryTitle)

                startActivity(intent)
            }
        })
    }

    companion object{
        const val CATEGORY = "category"
    }

    private fun setUpGetCategoryObservers() {
        viewModel.getCategoryState.observe(this , Observer {
            when(it){
                is Resource.Loading -> {
                    Toast.makeText(this,"Loading"
                        , Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(this,it.message
                        , Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(this,"Success Getting Data"
                        , Toast.LENGTH_SHORT).show()

                    setDataToRecView(it.data)


                }
                else -> {}
            }
        })



    }
}