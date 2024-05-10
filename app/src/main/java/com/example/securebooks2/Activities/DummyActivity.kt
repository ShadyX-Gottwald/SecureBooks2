package com.example.securebooks2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityDummyBinding

class DummyActivity : AppCompatActivity() {
    private  lateinit var  bind: ActivityDummyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDummyBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var bundle : Bundle? = intent.extras

        var category = bundle!!.getString(MyCollectionActivity.CATEGORY)

        bind.textViewUsernameDisplay.text = category



        // checking if the intent has extra
//        if(intent.hasExtra(MyCollectionActivity.CATEGORY)){
//            // get the Serializable data model class with the details in it
//            category =
//                intent.getSerializableExtra(MyCollectionActivity.DATA) as Category
//        }
        // it the emplist is not null the it has some data and display it
//        if(category!=null){
//            bind.textViewUsernameDisplay.text = category.categoryTitle
//
//        }

    }
}