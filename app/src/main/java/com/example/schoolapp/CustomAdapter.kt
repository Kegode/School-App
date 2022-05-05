package com.example.schoolapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(var context: Context, var data:ArrayList<Student>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtName:TextView
        var mTxtGrade:TextView
        var mTxtContact:TextView
        var mTxtCategory:TextView
        var mTxtGender:TextView
        var mTxtDateOfBirth:TextView
        init {
            this.mTxtName = row?.findViewById(R.id.mTvName) as TextView
            this.mTxtGrade = row?.findViewById(R.id.mTvGrade) as TextView
            this.mTxtContact = row?.findViewById(R.id.mTvContact) as TextView
            this.mTxtCategory = row?.findViewById(R.id.mTvCategory) as TextView
            this.mTxtGender = row?.findViewById(R.id.mTvGender) as TextView
            this.mTxtDateOfBirth = row?.findViewById(R.id.mTvDateOfBirth) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.item_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:Student = getItem(position) as Student
        viewHolder.mTxtName.text = item.name
        viewHolder.mTxtGrade.text = item.grade
        viewHolder.mTxtContact.text = item.contact
        viewHolder.mTxtCategory.text = item.category
        viewHolder.mTxtGender.text = item.gender
        viewHolder.mTxtDateOfBirth.text = item.dob
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