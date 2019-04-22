package com.example.letracker.user.adapters

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.letracker.R
import com.example.letracker.user.pojo.PaymentInfo

class PaymentAdapter (val userList: ArrayList<PaymentInfo>) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>()
{
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_payment_row, parent, false)
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
        fun bindItems(user: PaymentInfo)
        {
            val amt_tv = itemView.findViewById(R.id.amt_tv) as TextView
            val tmestamp_tv = itemView.findViewById(R.id.tmestamp_tv) as TextView


            amt_tv.setText(user.payment_amt)
            tmestamp_tv.setText(user.payment_time_stamp)



        }
    }
}