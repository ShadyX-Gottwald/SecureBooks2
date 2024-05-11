package com.example.securebooks2.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.example.securebooks2.Activities.Models.UserProfile
import com.example.securebooks2.Activities.Utilities.Resource
import com.example.securebooks2.Activities.Utilities.UserProfileUtils
import com.example.securebooks2.Activities.ViewModels.NewCollectionViewModel
import com.example.securebooks2.Activities.ViewModels.ProfileViewModel
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileActivity : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityProfileBinding
    private val viewmodel by lazy{ ProfileViewModel(auth,firestore ,storageReference) }
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = " ${UserProfileUtils.getUsername(auth.currentUser!!.email.toString())}"
        binding.helloTv.text = "Hello , $user"
        setUpStateObservers()
        setUpProfileStateObservers()

        //setSupportActionBar(binding.toolbar)
        setUpClickEvents()

    }

    private fun setUpClickEvents() {
        binding.buttonVC.setOnClickListener{
            val collectionActivity = Intent(this@ProfileActivity,MyCollectionActivity::class.java)
            startActivity(collectionActivity)
        }

        binding.imageView.setOnClickListener {
            selectImage()
        }

        binding.btnUpdate.setOnClickListener {

            //No Image Selected Error
            if(filePath == null){
                Toast.makeText(this,"Please Select Image" , Toast.LENGTH_SHORT).show()
            }
            else {

                viewmodel.createUserProfile(filePath!!)

            }
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,
            100)

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.data != null ) {

            //Get Image , Set to View.
            filePath = data.data!!
            //binding.setImageURI(filePath)
            binding.imageView.setImageURI(filePath)
        }
    }

    private fun setUpStateObservers() {

        viewmodel.result.observe(this, Observer {
            when(it) {
                is Resource.Success -> {

                    Toast.makeText(this@ProfileActivity , it.message , Toast.LENGTH_SHORT)
                        .show()
                }
              is Resource.Failure  -> {
                  Toast.makeText(this@ProfileActivity , it.message , Toast.LENGTH_SHORT).show()


              }
                is Resource.Loading -> {
                    Toast.makeText(this@ProfileActivity , it.message , Toast.LENGTH_SHORT).show()

                }

                else -> {}
            }

        })
    }

    private fun setUpProfileStateObservers() {
        viewmodel.user_exists.observe(this , Observer {user ->
            when(user) {
                is Resource.Success -> {
                    updateProfileImage(user)
                    Toast.makeText(this@ProfileActivity , user.message , Toast.LENGTH_SHORT)
                        .show()

                }
                is Resource.Failure -> {}
                is Resource.Loading -> {}
                else -> {}
            }

        })
    }

    private fun updateProfileImage(user: Resource<UserProfile>) {

        Glide.with(this).load(user.data?.imageUrl)
            .into(binding.imageView)


    }

}