package com.example.schoolapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StudentListActivity : AppCompatActivity() {

    var studentListLV:ListView ?=null
    var students:ArrayList<Student> ?= null
    var adapter:CustomAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentlist)

        studentListLV=findViewById(R.id.studentListLV)
        students = ArrayList()
        adapter = CustomAdapter(this,students!!)
        var ref = FirebaseDatabase.getInstance().getReference().child("Students")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                students!!.clear()
                for (snap in snapshot.children){
                    var student = snap.getValue(Student::class.java)
                    students!!.add(student!!)
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        studentListLV!!.adapter = adapter!!


    }
}