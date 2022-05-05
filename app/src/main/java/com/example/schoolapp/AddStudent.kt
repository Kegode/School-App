package com.example.schoolapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AddStudent : AppCompatActivity() {
    var auth: FirebaseAuth?=null
    var nameET: EditText? = null
    var classET: EditText? = null
    var contactET: EditText? = null
    var dobET: EditText? = null
    var genderET: EditText? = null
    var categoryET: EditText? = null
    var mBtnSubmit:Button?=null
    var mBtnViewStudents:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        nameET = findViewById(R.id.nameET)
        classET = findViewById(R.id.classET)
        contactET = findViewById(R.id.contactET)
        dobET = findViewById(R.id.dobET)
        genderET = findViewById(R.id.genderET)
        categoryET = findViewById(R.id.categoryET)
        mBtnSubmit=findViewById(R.id.mBtnSubmit)
        mBtnViewStudents=findViewById(R.id.mBtnViewStudents)

        auth = FirebaseAuth.getInstance()

        mBtnViewStudents!!.setOnClickListener {
            var intent = Intent(this, StudentListActivity::class.java)
            startActivity(intent)
        }


        mBtnSubmit!!.setOnClickListener {
            var name = nameET!!.text.toString().trim()
            var grade = classET!!.text.toString().trim()
            var contact = contactET!!.text.toString().trim()
            var category = categoryET!!.text.toString().trim()
            var gender = genderET!!.text.toString().trim()
            var dob = dobET!!.text.toString().trim()

            if (nameET!!.text.trim().toString().isNotEmpty() ||
                classET!!.text.trim().toString().isNotEmpty() ||
                contactET!!.text.trim().toString().isNotEmpty() ||
                categoryET!!.text.trim().toString().isNotEmpty() ||
                genderET!!.text.trim().toString().isNotEmpty() ||
                dobET!!.text.trim().toString().isNotEmpty()

            ) {
                Toast.makeText(this, "Details submitted", Toast.LENGTH_LONG).show()
                createStudent(
                    name,
                    grade,
                    contact,
                    category,
                    gender,
                    dob




                )


            } else {
                Toast.makeText(this, "Please fill all the inputs required", Toast.LENGTH_LONG)
                    .show()
            }


        }
    }

    private fun createStudent(name: String, grade: String, contact: String, category: String, gender: String, dob: String) {

                    var uid = FirebaseAuth.getInstance().currentUser!!
                    var userData = Student(uid.uid, name, grade, contact, category,gender,dob )
                    var ref = FirebaseDatabase.getInstance().reference.child("Students/"+System.currentTimeMillis())
                    ref.setValue(userData).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.e("Task", "Successful registration")
                            var intent = Intent(this, StudentListActivity::class.java)
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