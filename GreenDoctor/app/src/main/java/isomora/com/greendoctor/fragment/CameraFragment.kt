package isomora.com.greendoctor.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import isomora.com.greendoctor.base.BaseFragment
import isomora.com.greendoctor.Classifier
import isomora.com.greendoctor.R
import isomora.com.greendoctor.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.IOException

class CameraFragment : BaseFragment<FragmentCameraBinding>() {
    override val bindingInflater: (LayoutInflater) -> FragmentCameraBinding =
        FragmentCameraBinding::inflate

    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap

    private val mInputSize = 224
    private val mModelPath = "plant_disease_model.tflite"
    private val mLabelPath = "plant_labels.txt"
    private val mSamplePath = "soybean.JPG"

    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2

    override fun hasBackButtonOrNot() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun setActionBarTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.Camera)
    }

    override fun menuLogout() {
        TODO("Not yet implemented")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUp()
        addCallBacks()
        super.onViewCreated(view, savedInstanceState)

    }

    private fun addCallBacks() {
        binding.mCameraButton.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(callCameraIntent, mCameraRequestCode)
        }

        binding.mGalleryButton.setOnClickListener {
            val callGalleryIntent = Intent(Intent.ACTION_PICK)
            callGalleryIntent.type = "image/*"
            startActivityForResult(callGalleryIntent, mGalleryRequestCode)
        }
        binding.mDetectButton.setOnClickListener {
            val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
            binding.mResultTextView.text = results?.title + "\n Confidence:" + results?.confidence
            if (results!!.title[0] == 't')
                binding.textViewNamePlant.text = "tomato"
            else if (results.title[0] == 's' && results.title[1] == 't') {
                binding.textViewNamePlant.text = "strawberry"
            } else if (results.title[0] == 's' && results.title[1] == 'q') {
                binding.textViewNamePlant.text = "squash"
            } else if (results.title[0] == 's' ) {
                binding.textViewNamePlant.text = "soybean"
            }
        }
    }

    private fun setUp() {
        resources.assets.open(mSamplePath).use {
            mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            mPhotoImageView.setImageBitmap(mBitmap)
        }
        context?.assets.let {
            mClassifier = Classifier(requireContext().assets, mModelPath, mLabelPath, mInputSize)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == mCameraRequestCode) {
            //Considérons le cas de la caméra annulée
            if (resultCode == Activity.RESULT_OK && data != null) {
                mBitmap = data.extras!!.get("data") as Bitmap
                mBitmap = scaleImage(mBitmap)
                val toast = Toast.makeText(
                    context,
                    ("Image crop to: w= ${mBitmap.width} h= ${mBitmap.height}"),
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.BOTTOM, 0, 20)
                toast.show()
                mPhotoImageView.setImageBitmap(mBitmap)
                mResultTextView.text = "Your photo image set now."
            } else {
                Toast.makeText(context, "Camera cancel..", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == mGalleryRequestCode) {
            if (data != null) {
                val uri = data.data

                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                println("Success!!!")
                mBitmap = scaleImage(mBitmap)
                mPhotoImageView.setImageBitmap(mBitmap)

            }
        } else {
            Toast.makeText(context, "Unrecognized request code", Toast.LENGTH_LONG).show()

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