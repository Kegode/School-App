package com.example.schoolapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.FirebaseDatabase



class ParentRegistryActivity : AppCompatActivity() {
    var auth: FirebaseAuth?=null

    var btnLogRegister: Button? = null
    var mBtnPRegister: Button? = null
    var mTxtPname: TextView? = null
    var mTxtPemail: TextView? = null
    var mTxtPpassword: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_registry)
        mBtnPRegister = findViewById(R.id.mBtnPRegister)
        btnLogRegister = findViewById(R.id.btnLogRegister)
        mTxtPname = findViewById(R.id.mTxtPname)
        mTxtPemail = findViewById(R.id.mTxtPemail)
        mTxtPpassword = findViewById(R.id.mTxtPpassword)
        auth = FirebaseAuth.getInstance()


        mBtnPRegister!!.setOnClickListener {
            var name = mTxtPname!!.text.toString().trim()
            var email = mTxtPemail!!.text.toString().trim()
            var password = mTxtPpassword!!.text.toString().trim()

            if ( mTxtPname!!.text.trim().toString().isNotEmpty() || mTxtPemail!!.text.trim().toString().isNotEmpty() ||
                mTxtPpassword!!.text.trim().toString().isNotEmpty()

            ) {
                Toast.makeText(this, "Details submitted", Toast.LENGTH_LONG).show()
                createUser(
                    name,
                    email,
                    password

                )


            } else {
                Toast.makeText(this, "Please fill all the inputs required", Toast.LENGTH_LONG)
                    .show()
            }

        }
        btnLogRegister!!.setOnClickListener {
            onBackPressed()
        }

    }

    private fun createUser(name: String, email: String, password: String) {
        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var uid = FirebaseAuth.getInstance().currentUser!!
                    var userData = User(name, email, password, uid.uid,"0")
                    var ref = FirebaseDatabase.getInstance().reference.child("Users/${uid.uid}")
                    ref.setValue(userData).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.e("Task", "Successful registration")
                            var intent = Intent(this, ParentDashboard::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.putExtra("email_id", email)
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }else{
                    Log.e("Task", "Registration failed"+ task.exception)
                    Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                }
            }
    }
    override fun onStart(){
        super.onStart()

        var user=auth!!.currentUser
        if(user!=null){
            startActivity(Intent(this, ParentDashboard::class.java))
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
}






