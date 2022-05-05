package com.example.schoolapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import android.widget.TextView

class NewsAdapter(var context: Context, var data:ArrayList<News>):BaseAdapter() {
    private class ViewHolder(row:View?){


        var mTxtBody:TextView?=null

        init {


            this.mTxtBody = row?.findViewById(R.id.mTvBody) as TextView

        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.news_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:News = getItem(position) as News

        viewHolder.mTxtBody!!.text = item.body

        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}