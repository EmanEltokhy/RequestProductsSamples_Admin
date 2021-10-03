package com.example.productssamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var logo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        getSupportActionBar()?.hide()
        logo = findViewById(R.id.logo_id)
        logo.alpha = 0f
        logo.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this , LoginActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in ,android.R.anim.fade_out )
            finish()
        }
    }
}