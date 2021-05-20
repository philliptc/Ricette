package com.example.ricette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class WelcomePageActivity : AppCompatActivity() {

    private val SPLASH_TIME : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        Handler().postDelayed({
            startActivity(Intent (this, MainActivity::class.java))
            finish()


        }, SPLASH_TIME)
    }
}