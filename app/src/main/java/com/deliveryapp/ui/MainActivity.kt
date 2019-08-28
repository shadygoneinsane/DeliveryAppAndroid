package com.deliveryapp.ui

import android.os.Bundle
import androidx.navigation.findNavController
import com.deliveryapp.R
import com.deliveryapp.testing.OpenForTesting
import dagger.android.support.DaggerAppCompatActivity

@OpenForTesting
class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_container).navigateUp()
    }
}