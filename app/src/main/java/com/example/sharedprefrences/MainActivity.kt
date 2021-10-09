package com.example.sharedprefrences

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sharedprefrences.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var sharedPreference: SharedPreferences? = null
    private var selectedUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreference =
            this.getSharedPreferences("my_app_pref", Context.MODE_PRIVATE)

        var profile = binding.imgProfile.setOnClickListener {
            selectImageFromGallery()
        }
        binding.btnSave.setOnClickListener {
            val firstname = binding.firstName.text.toString()
            val lastname = binding.lastName.text.toString()
            val email = binding.email.text.toString()
            val contact = binding.contactNumber.text.toString()
            val gender = binding.gender.text.toString()

            sharedPreference?.apply {

                val saveData: SharedPreferences.Editor = edit()
                saveData.putString("fname_key", firstname)
                saveData.putString("lname_key", lastname)
                saveData.putString("email_key", email)
                saveData.putString("contact_key", contact)
                saveData.putString("gender_key", gender)
                saveData.putString("profile_key", selectedUri.toString())
                saveData.apply()
            }

            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_LONG).show()
        }
        sharedPreference?.apply {
            val sharedFirstName = getString("fname_key", "F")
            binding.firstName.setText("$sharedFirstName").toString()
            val sharedLastName = getString("lname_key", "F")
            binding.lastName.setText("$sharedLastName").toString()
            val sharedEmail = getString("email_key", "F")
            binding.email.setText("$sharedEmail").toString()
            val sharedContact = getString("contact_key", "F")
            binding.contactNumber.setText("$sharedContact").toString()
            val sharedGender = getString("gender_key", "F")
            binding.gender.setText("$sharedGender").toString()
            val sharedProfile = getString("profile_key", "F")
            val imageUri = Uri.parse(sharedProfile)
            Glide.with(this@MainActivity).load(imageUri).into(binding.imgProfile)

        }

    }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.imgProfile.setImageURI(uri)
                selectedUri = uri

            }
        }
    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

//    private var latestTmpUri: Uri? = null
//    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
//        if (isSuccess) {
//            latestTmpUri?.let { uri ->
//                binding.imgProfile.setImageURI(uri)
//            }
//        }
//    }
//    private fun takeImage() {
//        lifecycleScope.launchWhenStarted {
//            getTmpFileUri().let { uri ->
//                latestTmpUri = uri
//                takeImageResult.launch(uri)
//            }
//        }
//    }
//    private fun getTmpFileUri(): Uri {
//        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
//            createNewFile()
//            deleteOnExit()
//        }
//
//        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
//    }

}