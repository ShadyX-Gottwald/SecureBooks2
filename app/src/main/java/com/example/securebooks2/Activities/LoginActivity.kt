package com.example.securebooks2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.securebooks2.Activities.Models.LoginModel
import com.example.securebooks2.Activities.Models.hasEmptyField
import com.example.securebooks2.Activities.Utilities.Resource
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()
    private val viewModel by lazy { LoginViewmodel(auth) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickEvents()
        setUpLoginStateObserver()
    }

    private fun setClickEvents() {
        binding.loginBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()

            //Login Process Result
            val user = LoginModel(email, pass)

          //  viewModel.loginUser(user)
            loginClicked()


        }

        binding.SignUp.setOnClickListener {
            val signup_intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(signup_intent)
            finish()
        }
    }

    private fun setUpLoginStateObserver() {
        viewModel.loginState.observe(this, Observer {
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast.makeText(this@LoginActivity , it.message , Toast.LENGTH_SHORT).show()
                    onSuccessEvent()

                }
                is Resource.Failure -> {
                    Toast.makeText(this@LoginActivity , it.message , Toast.LENGTH_SHORT).show()
                    onFailureUiTrigger()
                }
                else -> Unit
            }
        })
    }

    private fun onFailureUiTrigger() {
        binding.etPassword.text = null
    }
    private fun onSuccessEvent() {
        val nextScreen = Intent(this@LoginActivity , ProfileActivity::class.java)
        startActivity(nextScreen)
        finish()

    }
    private fun loginClicked() {
        val email =  binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty() ){
            //Show Progress bar When Logging In
            binding.progressCircular.visibility = View.VISIBLE

            //Use Auth and Results of Those Actions
            auth.signInWithEmailAndPassword(email ,password).addOnCompleteListener() {
                if(it.isSuccessful) {
                    Toast.makeText(this@LoginActivity ,"$email Logged In SuccessFully", Toast.LENGTH_LONG).show()
                    //fireStoreAddUser()
                    val intent = Intent(this@LoginActivity,ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    Toast.makeText(this@LoginActivity, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
                binding.progressCircular.visibility = View.GONE
            }
        }else {
            Toast.makeText(this@LoginActivity , "Empty Fields Not Allowed", Toast.LENGTH_SHORT).show()
        }
    }

}