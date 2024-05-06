package com.example.securebooks2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.securebooks2.Activities.Models.RegisterModel
import com.example.securebooks2.Activities.Utilities.Resource
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val viewModel by lazy { RegisterViewModel(auth) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickEvents()
        setupRegisterStateObserver()

    }

    private fun setupRegisterStateObserver() {

        viewModel.registerState.observe(this , Observer {
            when(it) {
                is Resource.Loading -> {
                    Toast.makeText(this@RegisterActivity, "Loading",Toast.LENGTH_SHORT).show()

                }
                is Resource.Success -> {
                    Toast.makeText(this@RegisterActivity, it.message,Toast.LENGTH_SHORT).show()
                    //navigate to login page
                    val login_intent = Intent(this@RegisterActivity , LoginActivity::class.java)
                    startActivity(login_intent)
                    finish()

                }
                is Resource.Failure -> {
                    Toast.makeText(this@RegisterActivity, it.message,Toast.LENGTH_SHORT).show()

                }

                else -> {}
            }
        })


    }


    private fun setClickEvents() {
        binding.SignUp
            .setOnClickListener {
            val login_intent = Intent(this@RegisterActivity , LoginActivity::class.java)
            startActivity(login_intent)
            finish()
        }

        binding.login.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val pass = binding.password.text.toString()
            val confirm_pass = binding.etPassConfirm.text.toString()

            //Register process
            val person = RegisterModel(email,pass ,confirm_pass)
           val result =  viewModel.registerUser(person)


        }
    }
}