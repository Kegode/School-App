package com.example.schoolapp
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
class UpdatepicActivity : AppCompatActivity() {
    var PICK_IMAGE_REQUEST_ONE = 1
    var mImageUri: Uri?= null
    var mStorageRef: StorageReference? = null
    private var mUploadTask: StorageTask<*>? = null
    var mImgUpload: ImageView? = null
    var mBtnSelect: Button? = null
    var mBtnPost: Button?=null
    var mBtnView: Button?=null
    var imageUrl:String ?= null
    var pd: ProgressDialog?= null
    var mAuth: FirebaseAuth?= null
    var name: FirebaseAuth?=null
    var photoId:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatepic)
        mImgUpload = findViewById(R.id.mImgUpload)
        mBtnPost=findViewById(R.id.mBtnPost)
        mBtnView=findViewById(R.id.mBtnView)
        mBtnSelect = findViewById(R.id.mBtnSelect)
        mAuth = FirebaseAuth.getInstance()
        var user = mAuth!!.currentUser
        photoId = intent.getStringExtra("photo_id")

        mBtnView!!.setOnClickListener {
            var intent = Intent(this, AdminPicsList::class.java)
            startActivity(intent)
        }


        mStorageRef = FirebaseStorage.getInstance().getReference(user!!.uid+"/"+photoId)
        pd = ProgressDialog(this)
        mBtnSelect!!.setOnClickListener {
            openFileChooser(PICK_IMAGE_REQUEST_ONE)
        }
        mBtnPost!!.setOnClickListener {
            if (mImageUri ==null){
                Toast.makeText(applicationContext,"Select a photo file", Toast.LENGTH_LONG).show()
            }else{
                uploadSamplePhotoOneFile()
            }
        }


    }



    fun openFileChooser(PICK_IMAGE_REQUEST_ONE: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST_ONE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_ONE && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            Glide.with(this).load(mImageUri).into(mImgUpload!!)
        }  else if (requestCode == PICK_IMAGE_REQUEST_ONE && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            Glide.with(this).load(mImageUri).into(mImgUpload!!)
        }
    }


    private fun getFileExtension(uri: Uri): String? {
        val cR = applicationContext.contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
    private fun uploadSamplePhotoOneFile() {
        val fileReference = mStorageRef!!.child(
            System.currentTimeMillis()
                .toString() + "." + getFileExtension(mImageUri!!)
        )
        mUploadTask = fileReference.putFile(mImageUri!!).addOnSuccessListener {
            val delay: Thread = object : Thread() {
                override fun run() {
                    try {
                        sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            delay.start()
            Toast.makeText(applicationContext, "Uploading  photo completed", Toast.LENGTH_SHORT).show()
            pd!!.dismiss()
            onBackPressed()
            fileReference.downloadUrl.addOnSuccessListener { uri ->
                var uid=FirebaseAuth.getInstance().uid
                imageUrl = uri.toString()
                var uploadData = PicUploads(mAuth!!.currentUser!!.uid, imageUrl!!, photoId!!)
                var ref = FirebaseDatabase.getInstance().getReference().child("Alluploads/$photoId!!")
                ref.setValue(uploadData)

//                var ref2 = FirebaseDatabase.getInstance().getReference().child("$uid/$photoid")
//                ref2.setValue(uploadData)
//                var ref3 = FirebaseDatabase.getInstance().getReference().child("Users/$uid/$photoid")
//                ref3.setValue(uploadData)
            }
        }.addOnFailureListener { e ->
            //Failure
            Toast.makeText(applicationContext,e.message, Toast.LENGTH_LONG).show()
        }.addOnProgressListener { taskSnapshot ->

            //Updating the Progress Bar
            var progress:Double=(100.0*taskSnapshot.bytesTransferred)/taskSnapshot.totalByteCount
            pd!!.setTitle("Uploading")
            pd!!.setMessage(" ${progress.toInt()}%")
            pd!!.show()

        }
    }
}