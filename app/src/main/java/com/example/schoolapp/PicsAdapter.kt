package com.example.schoolapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

import android.widget.TextView
import com.bumptech.glide.Glide

class PicsAdapter(var context: Context, var data:ArrayList<Pics>):BaseAdapter() {
    private class ViewHolder(row:View?){


        var mImgPic:ImageView ?= null

        init {


            this.mImgPic = row?.findViewById(R.id.mImgPic) as ImageView

        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.pics_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:Pics = getItem(position) as Pics
        Glide.with(context).load(item.imageUrl).into(viewHolder.mImgPic!!)

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