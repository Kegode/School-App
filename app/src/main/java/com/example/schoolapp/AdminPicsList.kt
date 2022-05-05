package com.example.schoolapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminPicsList : AppCompatActivity() {
    var adminPicsListLV: ListView?=null
    var adminpics:ArrayList<PicUploads> ?= null
    var adapter:AdminPicsAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_pics_list)
        adminPicsListLV=findViewById(R.id.adminPicsListLV)
        adminpics = ArrayList()
        adapter = AdminPicsAdapter(this,adminpics!!)
        var ref = FirebaseDatabase.getInstance().getReference().child("Alluploads")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adminpics!!.clear()
                for (snap in snapshot.children){
                    var pic = snap.getValue(PicUploads::class.java)
                    adminpics!!.add(pic!!)
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        adminPicsListLV!!.adapter = adapter!!
    }
}
