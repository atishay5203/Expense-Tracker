package com.example.expense_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
// the main activity layout consists of a fragment navigation graph
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}