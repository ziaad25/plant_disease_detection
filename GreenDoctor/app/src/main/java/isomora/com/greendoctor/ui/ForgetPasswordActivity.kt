package isomora.com.greendoctor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import isomora.com.greendoctor.R
import isomora.com.greendoctor.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgetPasswordBinding
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.textViewBackToLogin.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonRecover.setOnClickListener {
            Recover()
        }
    }

    private fun Recover() {
        var email = binding.editTextEmailRecover.text.toString().trim()

        if (email.isEmpty()) {
            binding.textInputLayoutMailRecover.error = "should enter your email"
        } else {
            //send password recover email
            mAuth!!.sendPasswordResetEmail(email).addOnCompleteListener() {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Mail Sent, you can recover your password using email",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this,
                        "Email is wrong , or Account not exits",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
}