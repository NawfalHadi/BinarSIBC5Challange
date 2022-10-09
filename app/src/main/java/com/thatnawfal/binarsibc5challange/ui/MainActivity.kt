package com.thatnawfal.binarsibc5challange.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.thatnawfal.binarsibc5challange.R
import com.thatnawfal.binarsibc5challange.ui.auth.login.LoginBottomSheet
import com.thatnawfal.binarsibc5challange.ui.auth.login.OnLoginListener
import com.thatnawfal.binarsibc5challange.ui.auth.register.OnRegisterListener
import com.thatnawfal.binarsibc5challange.ui.auth.register.RegisterBottomSheet
import com.thatnawfal.binarsibc5challange.ui.home.HomeActivity
import kotlinx.coroutines.Delay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        Handler().postDelayed({
            showLoginBottomSheet()
        }, 3000)

    }

    private fun showLoginBottomSheet(){
        val currentDialog = supportFragmentManager.findFragmentByTag(LoginBottomSheet::class.java.simpleName)
        if (currentDialog == null) {
            LoginBottomSheet(object : OnLoginListener {
                override fun toRegister() {
                    showRegisterBottomSheet()
                }

                override fun loginSuccess() {
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    finish()
                }
            }).show(supportFragmentManager, LoginBottomSheet::class.java.simpleName)
        }
    }

    private fun showRegisterBottomSheet(){
        val currentDialog = supportFragmentManager.findFragmentByTag(RegisterBottomSheet::class.java.simpleName)
        if (currentDialog == null) {
            RegisterBottomSheet(object : OnRegisterListener{
                override fun finishRegister() {
                    showLoginBottomSheet()
                }
            }).show(supportFragmentManager, RegisterBottomSheet::class.java.simpleName)
        }
    }
}
