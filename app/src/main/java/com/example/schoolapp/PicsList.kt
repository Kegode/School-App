package com.example.schoolapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PicsList : AppCompatActivity() {
    var picsListLV: ListView?=null
    var pics:ArrayList<Pics> ?= null
    var adapter:PicsAdapter ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pics_list)
        picsListLV=findViewById(R.id.picsListLV)
        pics = ArrayList()
        adapter = PicsAdapter(this,pics!!)
        var ref = FirebaseDatabase.getInstance().getReference().child("Alluploads")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pics!!.clear()
                for (snap in snapshot.children){
                    var pic = snap.getValue(Pics::class.java)
                    pics!!.add(pic!!)
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        picsListLV!!.adapter = adapter!!
    }
}
