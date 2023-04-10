package isomora.com.greendoctor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import isomora.com.greendoctor.MainActivity
import isomora.com.greendoctor.databinding.ActivityLoginBinding
import isomora.com.greendoctor.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    lateinit var bindin: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindin = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(bindin.root)

        mAuth = FirebaseAuth.getInstance()

        bindin.textViewLogin.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindin.buttonSignUp.setOnClickListener {
            Register()
        }
    }

    private fun Register() {
        var email = bindin.editTextEmailSignUp.text.toString().trim()
        var password = bindin.editTextPasswordSignUp.text.toString().trim()
        var confirm = bindin.editTextConfirmPasswordSignUp.text.toString().trim()

        if (email.isEmpty()) {
            bindin.textInputEmailSignUp.error = "enter your mail"
        } else if (password.isEmpty() || password.length < 7) {
            bindin.textInputPasswordSignUp.error = "the password should be greater than 7"
        } else if (confirm != password) {
            bindin.textInputConfirmPasswordSignUp.error = "the password not same"
        } else {
            //Registr the user to firebase

            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                    sendEmailVerification()

                } else {
                    Toast.makeText(this, "Register fail", Toast.LENGTH_SHORT).show()

                }

            }
        }
    }

    private fun sendEmailVerification() {
        var user = mAuth!!.currentUser

        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Verification Email is Sent,Verify and log In Again",
                        Toast.LENGTH_SHORT
                    ).show()
                    mAuth!!.signOut()
                    finish()
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}