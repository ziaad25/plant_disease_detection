package isomora.com.greendoctor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import isomora.com.greendoctor.databinding.ActivityLoginBinding
import isomora.com.greendoctor.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var bindin: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindin = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(bindin.root)

        bindin.textViewLogin.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}