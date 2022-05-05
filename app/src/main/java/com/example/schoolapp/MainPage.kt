package com.example.schoolapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainPage : AppCompatActivity() {
    var adminOption:Button?=null
    var parentOption: Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        adminOption=findViewById(R.id.adminOption)
        parentOption=findViewById(R.id.parentOption)

        adminOption!!.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        parentOption!!.setOnClickListener {
            var intent = Intent(this, ParentLogin::class.java)
            startActivity(intent)
        }
    }

}