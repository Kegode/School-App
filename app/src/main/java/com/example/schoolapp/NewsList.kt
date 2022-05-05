package com.example.schoolapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NewsList : AppCompatActivity() {
    var newsListLV: ListView?=null
    var news:ArrayList<News> ?= null
    var adapter:NewsAdapter ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        newsListLV=findViewById(R.id.newsListLV)
        news = ArrayList()
        adapter = NewsAdapter(this,news!!)
        var ref = FirebaseDatabase.getInstance().getReference().child("News")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                news!!.clear()
                for (snap in snapshot.children){
                    var new = snap.getValue(News::class.java)
                    news!!.add(new!!)
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        newsListLV!!.adapter = adapter!!
        }
    }
