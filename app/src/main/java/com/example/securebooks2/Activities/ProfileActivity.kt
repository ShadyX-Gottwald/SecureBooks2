package com.example.securebooks2.Activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setUpClickEvents()

    }

    private fun setUpClickEvents() {
        binding.buttonVC.setOnClickListener{
            val collectionActivity = Intent(this@ProfileActivity,MyCollectionActivity::class.java)
            startActivity(collectionActivity)
        }
    }


}