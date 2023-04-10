package isomora.com.greendoctor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import isomora.com.greendoctor.databinding.ActivityLoginBinding
import isomora.com.greendoctor.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    lateinit var bindin: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindin.root)

        bindin.textViewSignUp.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}