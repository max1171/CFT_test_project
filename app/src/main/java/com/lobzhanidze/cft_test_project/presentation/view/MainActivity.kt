package com.lobzhanidze.cft_test_project.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lobzhanidze.cft_test_project.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                SearchBinFragment(),
                SearchBinFragment.TAG
            )
            .commit()
    }
}