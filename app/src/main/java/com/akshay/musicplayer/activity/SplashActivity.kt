package com.akshay.musicplayer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.akshay.musicplayer.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //this will take to the home activity in 1.5 seconds
        Handler().postDelayed(
            {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
            }, 1500
        )
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}