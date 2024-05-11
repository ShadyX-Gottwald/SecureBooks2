package com.example.securebooks2.Activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.securebooks2.Activities.Models.Category
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityDummyBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Formatter

class DummyActivity : AppCompatActivity() {
    private  lateinit var  bind: ActivityDummyBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDummyBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var bundle : Bundle? = intent.extras

        var category = bundle!!.getString(MyCollectionActivity.CATEGORY)

        bind.textViewUsernameDisplay2.text = category

        val datePicker = bind.datePicker
        val today = Calendar.getInstance()
//        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
//            today.get(Calendar.DAY_OF_MONTH)
//
//        ) { view, year, month, day ->
//            val month = month + 1
//            val msg = "$day-$month-$year"
//            val ans = LocalDate.parse(msg,
//                DateTimeFormatter.ofPattern("dd-MM-yyyy"))
//
//            Toast.makeText(this, ans.toString()
//                , Toast.LENGTH_SHORT).show()
//        }
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)
        bind.datePicker.setOnClickListener{

        }

        // clickDataPicker(this)

        // checking if the intent has extra

        }

}