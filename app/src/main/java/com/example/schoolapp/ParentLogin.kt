package com.example.schoolapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ParentLogin : AppCompatActivity() {
    //    private lateinit var auth: FirebaseAuth
    var btnRegLogin: Button?=null
    var mTxtLogPemail:TextView?=null
    var mTxtLogPpassword:TextView?=null
    var mBtnPLogin:Button?=null
    var auth: FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_login)
        btnRegLogin=findViewById(R.id.btnRegLogin)
        mTxtLogPemail=findViewById(R.id.mTxtLogPemail)
        mTxtLogPpassword=findViewById(R.id.mTxtLogPpassword)
        mBtnPLogin=findViewById(R.id.mBtnPLogin)
        auth = FirebaseAuth.getInstance()

        mBtnPLogin!!.setOnClickListener{
            if(mTxtLogPemail!!.text.trim().toString().isNotEmpty() || mTxtLogPpassword!!.text.trim().toString().isNotEmpty()){
                Log.e("Login", "Login Clicked")
                Toast.makeText(this, "Details submitted",Toast.LENGTH_LONG).show()
                loginUser(
                    mTxtLogPemail!!.text.trim().toString(),
                    mTxtLogPpassword!!.text.trim().toString()
                )
            }else {
                Toast.makeText(this, "Please fill all the inputs required", Toast.LENGTH_LONG).show()
            }
        }

        btnRegLogin!!.setOnClickListener {
            startActivity(Intent(this,ParentRegistryActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
    }
    private fun loginUser(email:String, password:String){
        auth!!.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    var ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(snap:DataSnapshot in snapshot.children){
                                var user = snap.getValue(User::class.java)
                                if (user!!.id.equals(auth!!.currentUser!!.uid) && user!!.status.equals("0")){
                                    startActivity(Intent(applicationContext, Dashboard::class.java))
                                    finish()
                                }else{
                                    startActivity(Intent(applicationContext, ParentDashboard::class.java))
                                    finish()
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(applicationContext,"Failed to retrieve user data",Toast.LENGTH_LONG).show()
                        }
                    })
                }else{
                    Log.e("Task", "Login failed" + task.exception)
                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        var user=auth!!.currentUser
        if(user!=null){
            var intent = Intent(this, ParentDashboard::class.java)
            startActivity(Intent(this, ParentDashboard::class.java))
            onBackPressed()
        }

    }
}
