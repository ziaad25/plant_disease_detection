package isomora.com.greendoctor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import isomora.com.greendoctor.MainActivity
import isomora.com.greendoctor.databinding.ActivityLoginBinding
import isomora.com.greendoctor.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var mUser: FirebaseUser? = null
    lateinit var bindin: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindin.root)

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth!!.currentUser
        if (mUser != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        bindin.textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        bindin.textViewForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
            finish()
        }

        bindin.buttonLogin.setOnClickListener {
            var email = bindin.editTextEmailLogin.text.toString().trim()
            var password = bindin.editTextPasswordLogin.text.toString().trim()

            if (email.isEmpty()) {
                bindin.textInputEmailLogin.error = "should enter your email"
            } else if (password.isEmpty()) {
                bindin.textInputPasswordLogin.error = "should enter you password"
            } else {
                bindin.progressBar.visibility = View.VISIBLE
                //login the user
                login()
            }
        }

    }

    private fun login() {
        var email = bindin.editTextEmailLogin.text.toString().trim()
        var password = bindin.editTextPasswordLogin.text.toString().trim()

        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener() {
            if (it.isSuccessful) {
                verifyEmail()
            } else {
                bindin.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "Account doesn't exists", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun verifyEmail() {
        var user = mAuth!!.currentUser
        if (user!!.isEmailVerified) {
            Toast.makeText(this, "Email Verified", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            bindin.progressBar.visibility = View.INVISIBLE
            Toast.makeText(this, "Verify your email first", Toast.LENGTH_SHORT).show()
            mAuth!!.signOut()
        }
    }
}