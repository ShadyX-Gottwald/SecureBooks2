package com.example.securebooks2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.securebooks2.R
import com.example.securebooks2.databinding.ActivityDummyBinding

class DummyActivity : AppCompatActivity() {
    private  lateinit var  bind: ActivityDummyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDummyBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}