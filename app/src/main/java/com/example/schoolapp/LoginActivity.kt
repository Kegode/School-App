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


class LoginActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth
    var btnRegLogin: Button?=null
    var mTxtLogemail:TextView?=null
    var mTxtLogpassword:TextView?=null
    var mBtnLogin:Button?=null
    var auth: FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnRegLogin=findViewById(R.id.btnRegLogin)
        mTxtLogemail=findViewById(R.id.mTxtLogemail)
        mTxtLogpassword=findViewById(R.id.mTxtLogpassword)
        mBtnLogin=findViewById(R.id.mBtnLogin)
        auth = FirebaseAuth.getInstance()

        mBtnLogin!!.setOnClickListener{
            if(mTxtLogemail!!.text.trim().toString().isNotEmpty() || mTxtLogpassword!!.text.trim().toString().isNotEmpty()){
                Log.e("Login", "Login Clicked")
                Toast.makeText(this, "Details submitted",Toast.LENGTH_LONG).show()
                loginUser(
                    mTxtLogemail!!.text.trim().toString(),
                    mTxtLogpassword!!.text.trim().toString()
                )
            }else {
                Toast.makeText(this, "Please fill all the inputs required", Toast.LENGTH_LONG).show()
            }
        }

        btnRegLogin!!.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
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
                            for(snap: DataSnapshot in snapshot.children){
                                var user = snap.getValue(User::class.java)
                                if (user!!.id.equals(auth!!.currentUser!!.uid) && user!!.status.equals("1")){
                                    startActivity(Intent(applicationContext, ParentDashboard::class.java))
                                    finish()
                                }else{
                                    startActivity(Intent(applicationContext, Dashboard::class.java))
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
            var intent = Intent(this, Dashboard::class.java)
            startActivity(Intent(this, Dashboard::class.java))
            onBackPressed()
        }

    }
}
