package com.example.securebooks2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityAddBookBinding

class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}