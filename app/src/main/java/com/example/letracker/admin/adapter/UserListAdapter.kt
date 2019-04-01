package com.example.letracker.admin.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.letracker.R
import com.example.letracker.admin.activity.AssignUserActivity
import com.example.letracker.admin.activity.DeviceFirmwareActivity
import com.example.letracker.admin.pojo.UserModel
import com.example.letracker.other.Constants

class UserListAdapter (val userList: ArrayList<UserModel>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>()
{
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_list_row, parent, false)
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

        fun bindItems(user: UserModel)
        {
            val id_tv = itemView.findViewById(R.id.id_tv) as TextView
            val u_user_name_tv = itemView.findViewById(R.id.u_user_name_tv) as TextView
            val u_password_tv = itemView.findViewById(R.id.u_password_tv) as TextView
            val devie_mac_id_tv = itemView.findViewById(R.id.devie_mac_id_tv) as TextView
            val device_firmware_id = itemView.findViewById(R.id.device_firmware_id) as Button
            val assign_credentials_tv = itemView.findViewById(R.id.assign_credentials_tv) as Button

            id_tv.text = user.id
            u_password_tv.text = user.password
            devie_mac_id_tv.text = user.device_mac_id
            u_user_name_tv.text = user.user_name


            device_firmware_id.setOnClickListener {


                var i = Intent(itemView.context,DeviceFirmwareActivity::class.java)
                i.putExtra(Constants.DEVICE_FIRMAWARE,user.device_firmware)
                itemView.context.startActivity(i)

            }
            assign_credentials_tv.setOnClickListener {

                var i = Intent(itemView.context,AssignUserActivity::class.java)
                i.putExtra(Constants.DEVICE_MAC_ID,user.device_mac_id)
                i.putExtra(Constants.ID,user.id)
                i.putExtra(Constants.USER_NAME,user.user_name)
                i.putExtra(Constants.PASSWORD,user.password)
                itemView.context.startActivity(i)

            }

        }


    }
}