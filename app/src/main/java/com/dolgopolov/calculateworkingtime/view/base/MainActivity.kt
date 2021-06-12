package com.dolgopolov.calculateworkingtime.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dolgopolov.calculateworkingtime.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}