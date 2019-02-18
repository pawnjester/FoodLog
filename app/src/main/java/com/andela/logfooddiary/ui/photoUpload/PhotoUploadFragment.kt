package com.andela.logfooddiary.ui.photoUpload

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andela.logfooddiary.R
import com.andela.logfooddiary.data.Upload
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_photo_upload.*


/**
 * A simple [Fragment] subclass.
 *
 */
class PhotoUploadFragment : Fragment() {

    private lateinit var storage: StorageReference
    private lateinit var databaseReference: DatabaseReference

    private lateinit var auth: FirebaseAuth

    private lateinit var uploads: ArrayList<Upload>

    private val listAdapter by lazy {
        PhotoDiaryAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads")
        uploads = ArrayList();
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(display_photo_recycler) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false)
            adapter = listAdapter
        }
        add_photo.setOnClickListener { _ -> openAddPhotoActivity() }
        getPhotos()
    }

    companion object {
        fun newInstance() = PhotoUploadFragment()
    }


    private fun checkFilePermissions() {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            var permissionCheck = this@UploadActivity.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE")
//            permissionCheck += this@UploadActivity.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE")
//            if (permissionCheck != 0) {
//                this.requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE), 1001) //Any number
//            }
//        }
    }

    private fun openAddPhotoActivity() {
        Intent(activity!!, PhotoActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun getPhotos() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val upload = postSnapshot.getValue(Upload::class.java)
                    uploads.add(upload ?: Upload())
                }
                if (uploads.isEmpty()) {
                    no_photo.visibility = View.VISIBLE
                    add_photo.visibility = View.VISIBLE
                }else{
                    listAdapter.updateList(uploads)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity!!, databaseError.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}
