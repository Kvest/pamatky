package com.kvest.pamatky.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kvest.pamatky.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.toString()
    }
}