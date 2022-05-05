package com.example.schoolapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class AdminPicsAdapter(var context: Context, var data:ArrayList<PicUploads>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var AdminImgPic:ImageView ?= null
        var mBtnDelete:Button ?= null
        var mBtnUpdate:Button ?= null
        init {
            this.AdminImgPic = row?.findViewById(R.id.AdminImgPic) as ImageView
            this.mBtnDelete = row?.findViewById(R.id.mBtnDelete) as Button
            this.mBtnUpdate = row?.findViewById(R.id.mBtnUpdate) as Button
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.admin_pics_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:PicUploads = getItem(position) as PicUploads
        Glide.with(context).load(item.imageUrl).into(viewHolder.AdminImgPic!!)
        viewHolder.mBtnDelete!!.setOnClickListener {
            var alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Deleting file")
            alertDialog.setMessage("Are you sure you want to delete this file?")
            alertDialog.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

            })
            alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                var ref = FirebaseDatabase.getInstance().getReference().child("Alluploads/"+item.photoid)
                ref.removeValue().addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(context,"File deleted successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,"File deletion failed",Toast.LENGTH_LONG).show()
                    }
                }
            })
            alertDialog.create().show()
        }
        viewHolder.mBtnUpdate!!.setOnClickListener {
            var tembea = Intent(context,UpdatepicActivity::class.java)
            tembea.putExtra("photo_id",item.photoid)
            context.startActivity(tembea)
        }

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