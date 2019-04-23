package com.example.letracker.user.adapters

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import com.example.letracker.R
import com.example.letracker.database.TABLE_ATTENDANCE
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.payment_history.PaymentHistoryActivity
import com.example.letracker.user.activity.MakePaymentActivity
import com.example.letracker.user.activity.AddNewLaborActivity
import com.example.letracker.user.activity.AttendanceActivity
import com.example.letracker.user.pojo.LaborInfo
import java.util.*

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
            val address_tv = itemView.findViewById(R.id.address_tv) as TextView
            val charges_tv = itemView.findViewById(R.id.charges_tv) as TextView

            val edite_labor_btn = itemView.findViewById(R.id.edite_labor_btn) as Button
            val call_labor_btn = itemView.findViewById(R.id.call_labor_btn) as Button
            val attendance_btn = itemView.findViewById(R.id.attendance_btn) as Button
            val avance_btn = itemView.findViewById(R.id.avance_btn) as Button
            val payment_btn = itemView.findViewById(R.id.payment_btn) as Button


            name_tv.setText(user.name)
            mobile_tv.setText(Html.fromHtml("<b>Mobile : </b>"+user.mobile))
            address_tv.setText(Html.fromHtml("<b>Address : </b>"+user.address))
            charges_tv.setText(Html.fromHtml("<b>Charges (per Day in \u20B9) : </b>"+user.charges))



            itemView.setOnClickListener {

                //creating a popup menu
                var popup = PopupMenu(itemView.context,itemView)
                //inflating menu from xml resource
                popup.inflate(R.menu.labor_option_menu)
                //adding click listener
                popup.setOnMenuItemClickListener {

                    when(it.itemId)
                    {
                        R.id.edt_lbr_menu ->
                        {
                            //edite labor details
                            var i = Intent(itemView.context,AddNewLaborActivity::class.java)

                            i.putExtra(Constants.FLAG, Constants.FLAG_UPDATE)
                            i.putExtra(TABLE_LABOR.ID, user.id)
                            i.putExtra(TABLE_LABOR.NAME, user.name)
                            i.putExtra(TABLE_LABOR.MOBILE_NUMBER, user.mobile)
                            i.putExtra(TABLE_LABOR.ADDRESS, user.address)
                            i.putExtra(TABLE_LABOR.CHARGES, user.charges)

                            itemView.context.startActivity(i)
                        }
                        R.id.make_pay_menu ->
                        {
                            //make labor payment
                            var i = Intent(itemView.context,MakePaymentActivity::class.java)
                            i.putExtra(TABLE_LABOR.ID, user.id)
                            i.putExtra(TABLE_LABOR.NAME, user.name)
                            itemView.context.startActivity(i)
                        }
                        R.id.pay_details_menu ->
                        {
                            //view labor payment history
                            var i = Intent(itemView.context,PaymentHistoryActivity::class.java)
                            i.putExtra(TABLE_LABOR.ID, user.id)
                            itemView.context.startActivity(i)
                        }
                    }
                    true
                }
                popup.show()
            }
            avance_btn.setOnClickListener {


                var i = Intent(itemView.context,MakePaymentActivity::class.java)

                i.putExtra(Constants.FLAG,Constants.PAYMENT_ADVANCE)
                i.putExtra(TABLE_LABOR.ID,user.id)
                i.putExtra(TABLE_LABOR.NAME,user.name)

                itemView.context.startActivity(i)
            }

            payment_btn.setOnClickListener {

                var i = Intent(itemView.context,MakePaymentActivity::class.java)

                i.putExtra(Constants.FLAG,Constants.PAYMENT_PAYMENT)
                i.putExtra(TABLE_LABOR.ID,user.id)
                i.putExtra(TABLE_LABOR.NAME,user.name)

                itemView.context.startActivity(i)

            }

            edite_labor_btn.setOnClickListener {

                var i = Intent(itemView.context,AddNewLaborActivity::class.java)

                i.putExtra(Constants.FLAG,Constants.FLAG_UPDATE)
                i.putExtra(TABLE_LABOR.ID,user.id)
                i.putExtra(TABLE_LABOR.NAME,user.name)
                i.putExtra(TABLE_LABOR.MOBILE_NUMBER,user.mobile)
                i.putExtra(TABLE_LABOR.ADDRESS,user.address)
                i.putExtra(TABLE_LABOR.CHARGES,user.charges)

                itemView.context.startActivity(i)
            }
            call_labor_btn.setOnClickListener {

                var i = Intent(Intent.ACTION_CALL)
                i.setData(Uri.parse("tel:"+user.mobile))
                itemView.context.startActivity(i)
            }
            attendance_btn.setOnClickListener {


                //first check attendance string is not present in db of this labor
                var month_year = ""+ M.returnMonthandYear();
                var attendanceInfo = TABLE_ATTENDANCE.getAttendance(user.id,month_year)
                if(attendanceInfo.isEmpty())
                {

                    var i = Intent(itemView.context,AttendanceActivity::class.java)

                    i.putExtra(TABLE_ATTENDANCE.LABOR_ID,user.id)
                    i.putExtra(TABLE_ATTENDANCE.MONTH_YEAR,month_year)

                    itemView.context.startActivity(i)

                }
                else
                {
                    var i = Intent(itemView.context,AttendanceActivity::class.java)

                    i.putExtra(TABLE_ATTENDANCE.LABOR_ID,user.id)
                    i.putExtra(TABLE_ATTENDANCE.MONTH_YEAR,month_year)

                    itemView.context.startActivity(i)
                }

            }

        }
    }
}