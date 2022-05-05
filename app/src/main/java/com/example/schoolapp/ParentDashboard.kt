package com.example.schoolapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

class ParentDashboard : AppCompatActivity() {
    var mBtnPLogout: Button?=null
    var auth: FirebaseAuth?=null
    var studentPAddLO: ConstraintLayout?=null
    var ourSchoolPLV: ConstraintLayout?=null
    var galleryPLO: ConstraintLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_dashboard)
        mBtnPLogout=findViewById(R.id.mBtnPLogout)
        studentPAddLO=findViewById(R.id.studentPAddLO)
        ourSchoolPLV=findViewById(R.id.ourSchoolPLV)
        galleryPLO=findViewById(R.id.galleryPLO)

        auth = FirebaseAuth.getInstance();
        studentPAddLO!!.setOnClickListener {
            var intent = Intent(this, StudentListActivity::class.java)
            startActivity(intent)
        }

        galleryPLO!!.setOnClickListener {
            var intent = Intent(this, PicsList::class.java)
            startActivity(intent)
        }

        ourSchoolPLV!!.setOnClickListener {
            var intent = Intent(this, NewsList::class.java)
            startActivity(intent)
        }

        mBtnPLogout!!.setOnClickListener{
            auth!!.signOut()
            startActivity(Intent(this,MainPage::class.java))
            finish()
        }
    }
}