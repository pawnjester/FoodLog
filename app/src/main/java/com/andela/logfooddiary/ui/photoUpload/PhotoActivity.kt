package com.andela.logfooddiary.ui.photoUpload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v7.app.AppCompatActivity
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.andela.logfooddiary.R
import com.andela.logfooddiary.data.Upload
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*


class PhotoActivity : AppCompatActivity() {

    private lateinit var storage: StorageReference
    private lateinit var databaseReference: DatabaseReference

    private lateinit var auth: FirebaseAuth

    private lateinit var uri: Uri

    companion object {
        val PICK_IMAGE_REQUEST = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        storage = FirebaseStorage.getInstance().getReference("uploads")
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads")
        auth = FirebaseAuth.getInstance()

        choose_image.setOnClickListener { _ -> openFileChooser() }
        upload_image_button.setOnClickListener { _ ->
                uploadFile()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == Activity.RESULT_OK
                && data != null && data.data != null){
            uri = data.data

            Picasso.get().load(uri).into(new_image)  //picasso for image loading
        }
    }

    private fun openFileChooser() {
        Intent().apply {
                setType("image/*")
            action = Intent.ACTION_GET_CONTENT
            startActivityForResult(this, PICK_IMAGE_REQUEST)
        }
    }

    private fun getFileExtension(uri: Uri): String {
        val contentResolver = getContentResolver()
        val mimeType = MimeTypeMap.getSingleton()
        return mimeType.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun uploadFile(){
        if (uri != null){
            val storageReference = storage.child(
                    "${System.currentTimeMillis()}.${getFileExtension(uri)}")
            storageReference.putFile(uri).addOnSuccessListener {
                Handler().apply {
                    postDelayed({progress_upload.setProgress(0)}, 500)
                }
                it.storage.downloadUrl.addOnSuccessListener {
                    Toast.makeText(this, "Upload Successful", Toast.LENGTH_LONG).show()
                    val newUpload = Upload(edit_text_file_name.text.toString().trim(),
                            it.toString())
                    val uploadId = databaseReference.push().key
                    databaseReference.child(uploadId ?: "").setValue(newUpload)

                }
            }
                    .addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
                    .addOnProgressListener {
                val progress = (100.0 * it.bytesTransferred / it.totalByteCount)
                progress_upload.setProgress(progress.toInt())
            }
        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show()
        }
    }
}
