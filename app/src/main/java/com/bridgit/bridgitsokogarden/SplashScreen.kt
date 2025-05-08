package com.bridgit.bridgitsokogarden

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show your splash layout
        setContentView(R.layout.activity_splash_screen)

        // Apply fade-in animation
        val mainLayout = findViewById<View>(R.id.splashRoot)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        mainLayout.startAnimation(fadeIn)

        // Move to MainActivity after delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3500)
    }
}
