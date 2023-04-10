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
import androidx.fragment.app.Fragment
import isomora.com.greendoctor.databinding.ActivityMainBinding
import isomora.com.greendoctor.fragment.CameraFragment
import isomora.com.greendoctor.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*;
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val fragmentCamera = CameraFragment()
    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap

    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2

    private val mInputSize = 224
    private val mModelPath = "plant_disease_model.tflite"
    private val mLabelPath = "plant_labels.txt"
    private val mSamplePath = "soybean.JPG"

    lateinit var bindin: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        bindin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindin.root)
        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)


        addNavigationListener()


    }


    fun addNavigationListener() {
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

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }


    fun scaleImage(bitmap: Bitmap?): Bitmap {
        val orignalWidth = bitmap!!.width
        val originalHeight = bitmap.height
        val scaleWidth = mInputSize.toFloat() / orignalWidth
        val scaleHeight = mInputSize.toFloat() / originalHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, orignalWidth, originalHeight, matrix, true)
    }

}

