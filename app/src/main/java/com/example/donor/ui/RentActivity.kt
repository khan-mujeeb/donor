package com.example.donor.ui

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.donor.MainActivity
import com.example.donor.data.RentInfo
import com.example.donor.databinding.ActivityRentBinding
import com.example.donor.utlis.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.IOException

class RentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRentBinding
    private lateinit var dialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        storage = FirebaseUtils.firebaseStorage
        database = FirebaseUtils.firebaseDatabase
        auth = FirebaseUtils.firebaseAuth

        alterDialog()

        binding.openCamera.setOnClickListener {
            choosePhotoFromGallery()
        }

        binding.finishBtn.setOnClickListener {
            dialog.show()
            uploadPdfToFirebaseStorage(contentUri)
        }

    }

    //    function to create user
    private fun uploadDataInfo(imgUri: String) {



        val item = RentInfo(
            img = imgUri,
            name = binding.nameEdtxt.text.toString(),
            phoneNumber = auth.currentUser!!.phoneNumber.toString(),
            rent = binding.rent.text.toString(),
            uid = auth.uid.toString()

        )

        val randomkey = database.getReference(auth.uid.toString()).push().key.toString()
        database.reference.child("items")
            .child(randomkey)
            .setValue(item)
            .addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(this, "item listed", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }

    // photo select
    private fun choosePhotoFromGallery() {
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(
            object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if(report!!.areAllPermissionsGranted()) {
                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        startActivityForResult(galleryIntent, GALLERY)
                    }
                }
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?) {
                    showDialogForPermissions()
                }
            }).onSameThread().check();

    }

    // function for storage permission
    fun showDialogForPermissions() {
        androidx.appcompat.app.AlertDialog.Builder(this).setMessage("" +
                "Allow permission to use this feature"
        ).setPositiveButton("Go to Settings") {
                _, _ ->
            try {
                val intent  = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()

            }
        }.setNegativeButton("Cancel") {dialog, _ ->
            dialog.dismiss()
        }.show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK) {
            if(requestCode== GALLERY) {

                if(data!=null) {

                    contentUri = data.data!!
                    try {
                        val selectedImage = MediaStore.Images.Media.getBitmap(
                            this.contentResolver,
                            contentUri
                        )
                        check = 1
                        binding.cameraImg.setImageBitmap(selectedImage)

                        Toast.makeText(this, "Image Set", Toast.LENGTH_SHORT).show()
                    }catch (e: IOException){
                        e.printStackTrace()
                        Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun alterDialog() {
        builder = AlertDialog.Builder(this)
        builder.setMessage("Loading..")
        builder.setCancelable(false)
        dialog = builder.create()
    }

    private fun uploadPdfToFirebaseStorage(uriValue: Uri?) {

        val storageRef = FirebaseStorage.getInstance().reference
        val pdfRef = storageRef.child("dp/${uriValue!!.lastPathSegment}")
        val uploadTask = pdfRef.putFile(uriValue)
        uploadTask.addOnProgressListener { taskSnapShot ->
            val progress = ((100.00 * taskSnapShot.bytesTransferred) / taskSnapShot.totalByteCount)
            Log.d(ContentValues.TAG, "progress is $progress")
//            val message = findViewById<TextView>(Build.VERSION_CODES.R.id.message)
//            dialog.setCustomTitle(message)


        }.addOnSuccessListener {
            // Get the download URL of the PDF file and save it to Firebase Realtime Database
            pdfRef.downloadUrl.addOnSuccessListener { downloadUri ->
                uploadDataInfo(downloadUri.toString())
            }
        }.addOnFailureListener {
            // Handle the error
        }
    }

    companion object{
        private const val GALLERY = 1
        private lateinit var contentUri: Uri
        private  var check = 0
    }

}