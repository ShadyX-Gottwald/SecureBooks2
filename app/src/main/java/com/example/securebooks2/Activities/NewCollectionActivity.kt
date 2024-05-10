package com.example.securebooks2.Activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.Activities.Models.toMap
import com.example.securebooks2.Activities.Utilities.Resource
import com.example.securebooks2.Activities.ViewModels.NewCollectionViewModel
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityNewCollectionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class NewCollectionActivity : AppCompatActivity() {
    private lateinit var bind: ActivityNewCollectionBinding
    private val auth: FirebaseAuth  = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewmodel by lazy{NewCollectionViewModel(auth,firestore)}
    private val PICK_IMAGE_REQUEST = 71
    private lateinit var filePath: Uri
    private var storageReference: StorageReference? = FirebaseStorage.getInstance().reference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNewCollectionBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpObservers()
        setUpClickEvents()
        setUpImageSelectListeners()
      //  bind.sbTarget.min = 0
     //   bind.sbTarget.min = 10
        setUpSeekbarListeners()
    }

    private fun setUpClickEvents() {
        bind.createCollectionBtn.setOnClickListener(){
            //Get Variables
            val categoryId: String = UUID.randomUUID().toString()
            val userId = auth.currentUser!!.uid
            val categoryTitle = bind.etCategoryTitle.text.toString()
            val categoryTargetNum = bind.etTarget.text.toString().toInt()

            if(categoryTargetNum in 1..10){
                when(filePath) {
                    null -> {
                        Toast.makeText(this, "Please choose Image to Upload" , Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        //Capture Variables to Category Obj
                        val categoryObj = Category().apply {
                            this.categoryId = categoryId
                            this.userId = userId
                            this.categoryTargetNum = categoryTargetNum
                            this.categoryTitle = categoryTitle

                        }

                        viewmodel.createCategory(categoryObj, filePath)
                    }
                }


            }else {
                Toast.makeText(this, "Target Range 1-10" , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


        }

    }

    private fun setUpImageSelectListeners() {
        bind.collectionImg.setOnClickListener{
            selectImage()
        }


    }

    private fun setUpObservers(){
        viewmodel.result.observe(this@NewCollectionActivity, Observer {
            when(it) {
                is Resource.Failure -> {
                    Toast.makeText(this, "$${it.message } + ${it.data}",Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Success -> {
                    Toast.makeText(this, "$${it.message } + ${it.data}",Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {}
            }
        } )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpSeekbarListeners() {

//        bind.sbTarget.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                bind.progress.text = progress.toString()
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                TODO("Not yet implemented")
//            }
//        })

    }

    /***Code to select image , Upload****/

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
            bind.collectionImg.setImageURI(filePath)
        }
    }

}