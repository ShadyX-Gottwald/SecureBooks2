package com.example.securebooks2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickEvents()
    }

    private fun setClickEvents() {
         binding.loginBtn.setOnClickListener{

         }

        binding.SignUp.setOnClickListener {
            val signup_intent = Intent(this@LoginActivity , RegisterActivity::class.java)
            startActivity(signup_intent)
            finish()
        }
    }
}