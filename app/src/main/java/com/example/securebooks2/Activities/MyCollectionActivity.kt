package com.example.securebooks2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityMyCollectionBinding

class MyCollectionActivity : AppCompatActivity() {
    private lateinit var  bind: ActivityMyCollectionBinding
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