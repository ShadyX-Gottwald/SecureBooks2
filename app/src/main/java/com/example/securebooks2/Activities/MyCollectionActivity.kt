package com.example.securebooks2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.securebooks2.Activities.Adapters.CategoryAdapter
import com.example.securebooks2.Activities.ViewModels.MyCollectionViewModel
import com.example.securebooks2.R
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
    }

    private fun setClickListener() {

        bind.newItemFab.setOnClickListener {
            val newCollectionAct = Intent(this@MyCollectionActivity , NewCollectionActivity::class.java)
            startActivity(newCollectionAct)

        }
    }
}