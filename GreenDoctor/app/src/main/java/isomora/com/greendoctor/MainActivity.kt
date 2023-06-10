package isomora.com.greendoctor

import android.os.Bundle
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import isomora.com.greendoctor.databinding.ActivityMainBinding
import isomora.com.greendoctor.fragment.CameraFragment
import isomora.com.greendoctor.fragment.HomeFragment
import isomora.com.greendoctor.ui.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*;
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val fragmentCamera = CameraFragment()

    var mAuth: FirebaseAuth? = null
    var mUser: FirebaseUser? = null
    var mStore: FirebaseFirestore? = null

    lateinit var bindin: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        bindin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindin.root)

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth!!.currentUser
        mStore = FirebaseFirestore.getInstance()

        addNavigationListener()
    }


    private fun addNavigationListener() {
        addFragment(HomeFragment())
        bindin.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Camera -> {
                    addFragment(fragmentCamera)
                    true
                }

                else -> {
                    addFragment(HomeFragment())
                    true
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top_par, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.log_out -> {
                mAuth!!.signOut()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return true
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }


}

