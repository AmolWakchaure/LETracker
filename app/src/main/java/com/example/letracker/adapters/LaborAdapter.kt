package com.example.letracker.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.letracker.R
import com.example.letracker.pojo.LaborInfo

class LaborAdapter (val userList: ArrayList<LaborInfo>) : RecyclerView.Adapter<LaborAdapter.ViewHolder>()
{
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.labor_list_row, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int
    {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bindItems(user: LaborInfo)
        {
            val name_tv = itemView.findViewById(R.id.name_tv) as TextView
            val mobile_tv = itemView.findViewById(R.id.mobile_tv) as TextView

            name_tv.text = user.name
            mobile_tv.text = user.mobile

        }
    }
}