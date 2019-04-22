package com.example.letracker.user.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.letracker.R
import com.example.letracker.database.TABLE_ATTENDANCE
import com.example.letracker.other.*
import com.example.letracker.user.pojo.CalenderInfo

class CalenderAdapter (val userList: ArrayList<CalenderInfo>, val listnerCallbacks: DateSelectedListnerCallbacks) : RecyclerView.Adapter<CalenderAdapter.ViewHolder>()
{

    var stringBuffer : StringBuffer
    init {

        stringBuffer = StringBuffer()
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.calender_row, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        var labor_id = MyApplication.prefs.getString(TABLE_ATTENDANCE.LABOR_ID,"0")
        var month_year = MyApplication.prefs.getString(TABLE_ATTENDANCE.MONTH_YEAR,"0")
        var status = TABLE_ATTENDANCE.getAttendanceString(labor_id,month_year)

        if(!status.equals("NA"))
        {
            stringBuffer.append(status)
        }
        holder.bindItems(userList[position],stringBuffer,listnerCallbacks)


    }
    //this method is giving the size of the list
    override fun getItemCount(): Int
    {
        return userList.size
    }
    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {


        fun bindItems(user: CalenderInfo,
                      stringBuffer : StringBuffer,
                      listnerCallbacks: DateSelectedListnerCallbacks)
        {
            val date_tv = itemView.findViewById(R.id.date_tv) as TextView
            val day_tv = itemView.findViewById(R.id.day_tv) as TextView
            val date_cell_li = itemView.findViewById(R.id.date_cell_li) as LinearLayout
            date_tv.setText(user.date)
            day_tv.setText(user.day)
            if(user.todayDate.equals("true"))
            {

                date_tv.setTextColor(Color.parseColor("#ff0000"))
                day_tv.setTextColor(Color.parseColor("#ff0000"))
            }
            if(user.attendance_status.equals("P"))
            {
                date_cell_li.setBackgroundColor(Color.parseColor("#339933"))
                date_tv.setTextColor(Color.parseColor("#ffffff"))
                day_tv.setTextColor(Color.parseColor("#ffffff"))
            }

            date_cell_li.setOnClickListener {

                //get todays date
                var timeStampData = M.getSystemDateTime()!!.split(" ")
                var dateData = timeStampData.get(0).split("-")

                if(!(Integer.valueOf(dateData.get(2)) < Integer.valueOf(user.date)))
                {
                    if(!user.attendance_status.equals("P"))
                    {
                        //listnerCallbacks.returnDetails(userList,position,"P")
                        if(!stringBuffer.toString().contains(user.date))
                        {
                            stringBuffer.append(user.date+",")
                        }
                        var attendance_str = stringBuffer.toString().substring(0,stringBuffer.toString().length - 1)
                        M.e("attendance_str : "+attendance_str)

                        var labor_id = MyApplication.prefs.getString(TABLE_ATTENDANCE.LABOR_ID,"0")
                        var month_year = MyApplication.prefs.getString(TABLE_ATTENDANCE.MONTH_YEAR,"0")
                        //check already
                        var status = TABLE_ATTENDANCE.getAttendanceString(labor_id,month_year)
                        if(status.equals("NA"))
                        {

                            M.e("attendance_str : insert")
                            //insert
                            TABLE_ATTENDANCE.addAttendance(labor_id,attendance_str,month_year)

                            date_cell_li.setBackgroundColor(Color.parseColor("#339933"))
                            date_tv.setTextColor(Color.parseColor("#ffffff"))
                            day_tv.setTextColor(Color.parseColor("#ffffff"))

                            //for refresh calculations
                            listnerCallbacks.returnDetails();
                        }
                        else
                        {
                            M.e("attendance_str : update")
                            //update
                            TABLE_ATTENDANCE.updateAttendance(labor_id,attendance_str,month_year)

                            date_cell_li.setBackgroundColor(Color.parseColor("#339933"))
                            date_tv.setTextColor(Color.parseColor("#ffffff"))
                            day_tv.setTextColor(Color.parseColor("#ffffff"))

                            //for refresh calculations
                            listnerCallbacks.returnDetails();
                        }

                    }

                }

            }
        }
    }
}