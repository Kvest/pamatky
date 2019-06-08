package com.kvest.pamatky.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {
    private val viewModel: TestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.toString()
    }
}