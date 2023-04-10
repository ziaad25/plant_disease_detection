package isomora.com.greendoctor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import isomora.com.greendoctor.MainActivity
import isomora.com.greendoctor.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            kotlin.run {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 4000)
    }
}