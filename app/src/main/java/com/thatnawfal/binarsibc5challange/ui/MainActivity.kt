package com.thatnawfal.binarsibc5challange.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.thatnawfal.binarsibc5challange.R
import com.thatnawfal.binarsibc5challange.ui.home.HomeActivity
import kotlinx.coroutines.Delay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 3000)

    }
}