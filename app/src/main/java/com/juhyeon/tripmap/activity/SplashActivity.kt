package com.juhyeon.tripmap.activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemClock.sleep(300)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}