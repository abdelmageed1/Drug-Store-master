package com.kotlinlearn.drugstore.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.kotlinlearn.drugstore.view.main.MainActivity
import com.kotlinlearn.drugstore.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var img = findViewById<ImageView>(R.id.img_splash)
        Thread {
            img.animate().translationYBy(1150f).duration=1500
            Thread.sleep(3000)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.start()
    }
}