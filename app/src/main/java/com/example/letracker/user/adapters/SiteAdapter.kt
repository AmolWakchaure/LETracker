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
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.database.TABLE_SITE
import com.example.letracker.other.Constants
import com.example.letracker.user.activity.AddNewLaborActivity
import com.example.letracker.user.activity.SiteDetailsActivity
import com.example.letracker.user.pojo.LaborInfo
import com.example.letracker.user.pojo.SiteInfo

class SiteAdapter (val userList: ArrayList<SiteInfo>) : RecyclerView.Adapter<SiteAdapter.ViewHolder>()
{
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.site_list_row, parent, false)
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
        fun bindItems(user: SiteInfo)
        {
            val name_tv = itemView.findViewById(R.id.name_tv) as TextView
            val mobile_tv = itemView.findViewById(R.id.mobile_tv) as TextView
            val address_tv = itemView.findViewById(R.id.address_tv) as TextView

            val edite_labor_btn = itemView.findViewById(R.id.edite_labor_btn) as Button
            val call_labor_btn = itemView.findViewById(R.id.call_labor_btn) as Button

            name_tv.setText(user.name)
            mobile_tv.setText(Html.fromHtml("<b>Mobile : </b>"+user.mobile))
            address_tv.setText(Html.fromHtml("<b>Address : </b>"+user.address))

            edite_labor_btn.setOnClickListener {

                var i = Intent(itemView.context,SiteDetailsActivity::class.java)

                i.putExtra(Constants.FLAG,Constants.FLAG_UPDATE)
                i.putExtra(TABLE_SITE.ID,user.id)
                i.putExtra(TABLE_SITE.NAME,user.name)
                i.putExtra(TABLE_SITE.MOBILE_NUMBER,user.mobile)
                i.putExtra(TABLE_SITE.ADDRESS,user.address)

                itemView.context.startActivity(i)
            }
            call_labor_btn.setOnClickListener {

                var i = Intent(Intent.ACTION_CALL)
                i.setData(Uri.parse("tel:"+user.mobile))
                itemView.context.startActivity(i)
            }

        }
    }
}