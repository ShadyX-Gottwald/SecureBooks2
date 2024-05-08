package com.example.securebooks2.Activities

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
import java.util.UUID

class NewCollectionActivity : AppCompatActivity() {
    private lateinit var bind: ActivityNewCollectionBinding
    private val auth: FirebaseAuth  = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewmodel by lazy{NewCollectionViewModel(auth,firestore)}
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNewCollectionBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpObservers()
        setUpClickEvents()
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
                //Capture Variables to Category Obj
                val categoryObj = Category().apply {
                    this.categoryId = categoryId
                    this.userId = userId
                    this.categoryTargetNum = categoryTargetNum
                    this.categoryTitle = categoryTitle

                }

                viewmodel.createCategory(categoryObj)


            }else {
                Toast.makeText(this, "Target Range 1-10" , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


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
}