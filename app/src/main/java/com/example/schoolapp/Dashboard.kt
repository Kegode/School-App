package com.example.schoolapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

class Dashboard : AppCompatActivity() {
    var mBtnLogout:Button?=null
    var auth:FirebaseAuth?=null
    var studentAddLO: ConstraintLayout?=null
    var ourSchoolLV: ConstraintLayout?=null
    var galleryLO: ConstraintLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mBtnLogout=findViewById(R.id.mBtnLogout)
        studentAddLO=findViewById(R.id.studentAddLO)
        ourSchoolLV=findViewById(R.id.ourSchoolLV)
        galleryLO=findViewById(R.id.galleryLO)

        auth = FirebaseAuth.getInstance();
        studentAddLO!!.setOnClickListener {
            var intent = Intent(this, AddStudent::class.java)
            startActivity(intent)
        }

        galleryLO!!.setOnClickListener {
            var intent = Intent(this, BalanceActivity::class.java)
            startActivity(intent)
        }

        ourSchoolLV!!.setOnClickListener {
            var intent = Intent(this, OurSchool::class.java)
            startActivity(intent)
        }

        mBtnLogout!!.setOnClickListener{
        auth!!.signOut()
            startActivity(Intent(this,MainPage::class.java))
            finish()
        }

    }
}