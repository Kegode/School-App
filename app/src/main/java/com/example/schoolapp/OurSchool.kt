package com.example.schoolapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class OurSchool : AppCompatActivity() {
    var auth: FirebaseAuth?=null
    var mImage:ImageView?=null
    var mTxtHead: EditText?=null
    var mTxtBody: EditText?=null
    var buttonPost: Button?=null
    var buttonView: Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_our_school)


        mTxtHead = findViewById(R.id.mTxtHead)
        mTxtBody = findViewById(R.id.mTxtBody)
        buttonPost = findViewById(R.id.buttonPost)
        buttonView = findViewById(R.id.buttonView)

        auth = FirebaseAuth.getInstance()
        buttonView!!.setOnClickListener {
            var intent = Intent(this, NewsList::class.java)


            startActivity(intent)
        }
        buttonPost!!.setOnClickListener {

            var head = mTxtHead!!.text.toString().trim()
            var body = mTxtBody!!.text.toString().trim()


            if (
                mTxtHead!!.text.trim().toString().isNotEmpty() ||
                mTxtBody!!.text.trim().toString().isNotEmpty()


            ) {
                Toast.makeText(this, "Details submitted", Toast.LENGTH_LONG).show()
                createNews(

                    head,
                    body





                )


            } else {
                Toast.makeText(this, "Please fill all the inputs required", Toast.LENGTH_LONG)
                    .show()
            }


        }


    }
    private fun createNews( head: String, body: String) {

        var uid = FirebaseAuth.getInstance().currentUser!!
        var userData = News(uid.uid,head, body )
        var ref = FirebaseDatabase.getInstance().reference.child("News/"+System.currentTimeMillis())
        ref.setValue(userData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e("Task", "Successful posted")
                var intent = Intent(this, NewsList::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                startActivity(intent)

            } else {
                Toast.makeText(
                    this,
                    task.exception!!.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    }
}