package com.example.letracker.user.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.letracker.R
import com.example.letracker.database.TABLE_ATTENDANCE
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.database.TABLE_LABOR_PAYMENT
import com.example.letracker.other.Constants
import com.example.letracker.other.DateSelectedListnerCallbacks
import com.example.letracker.other.M
import com.example.letracker.other.MyApplication
import com.example.letracker.user.adapters.CalenderAdapter
import com.example.letracker.user.adapters.SiteAdapter
import com.example.letracker.user.pojo.CalenderInfo
import com.example.letracker.user.pojo.SiteInfo
import kotlinx.android.synthetic.main.activity_attendance.*
import java.util.*
import kotlin.collections.ArrayList

class AttendanceActivity : AppCompatActivity() {



    var labor_id = ""
    var month_year = ""

    var  total_advance_amt = "0"
    var  total_paid_amt = "0"
    var  advance_amt = "0"
    var  paid_amt = "0"
   // var  remaining_amount = "0"

    var  present_days = 0

    var calenderInfo = ArrayList<CalenderInfo>()
    lateinit var adapter : CalenderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)


        //get intent data
        var bundle = intent.extras

        if(bundle != null)
        {
            labor_id = bundle.getString(TABLE_ATTENDANCE.LABOR_ID)
            month_year = bundle.getString(TABLE_ATTENDANCE.MONTH_YEAR)
        }
        go_back.setOnClickListener {

            finish()
        }

        setCalculatedData()

        calender_rv.layoutManager = GridLayoutManager(MyApplication.context,7)
        adapter = CalenderAdapter(calenderInfo, object : DateSelectedListnerCallbacks{
            override fun returnDetails()
            {

                setCalculatedData();
            }
        });
        calender_rv.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    private fun setCalculatedData() {

        //set current month and year
        monthYr_tv.setText(""+M.returnMonthandYear())

        //get attendace string of given labor details
        var attendance_string = TABLE_ATTENDANCE.getAttendanceString(labor_id,month_year)
        M.e("attendance_string : "+attendance_string)

        MyApplication.editor.putString(TABLE_ATTENDANCE.LABOR_ID,labor_id).commit()
        MyApplication.editor.putString(TABLE_ATTENDANCE.MONTH_YEAR,month_year).commit()
        //get clender days info
        calenderInfo = M.returnMonthDetails(attendance_string)

        //get total advance amount
        total_advance_amt = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.PAYMENT_ADVANCE,labor_id,"all",""+M.getSystemDateTime())

        //get advance amount
        advance_amt = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.PAYMENT_ADVANCE,labor_id,"current",""+M.getSystemDateTime())

        //get total paid amount
        total_paid_amt = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.PAYMENT_PAYMENT,labor_id,"all",""+M.getSystemDateTime())

        //get current month paid amount
        paid_amt = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.PAYMENT_PAYMENT,labor_id,"current",""+M.getSystemDateTime())

        //get remaining amount
        //remaining_amount = ""+(Integer.valueOf(total_advance_amt) - Integer.valueOf(paid_amt))

        //calculate present days
        for(calenderInfo : CalenderInfo in calenderInfo)
        {
            var attStatus = calenderInfo.attendance_status
            if(attStatus.equals("P"))
            {
                present_days++
            }
        }
        //get labor charges
        var labor_charges = TABLE_LABOR.getLaborCharger(labor_id).split("#")
        if(labor_charges.size > 0)
        {
            //set labor name
            labor_name_tv.setText("Name : "+labor_charges.get(1))
            //set labor current assigned charges
            labor_charges_tv.setText("Charges : \u20B9"+labor_charges.get(0)+" per day")
            //set total advance amount
            total_advance_amt_tv.setText("₹"+total_advance_amt)
            //set advance amount
            advance_amt_tv.setText("₹"+advance_amt)
            //set total paid amount
            total_paid_amt_tv.setText("₹"+total_paid_amt)
            //set paid amount
            paid_amt_tv.setText("₹"+paid_amt)
            //set present days
            present_days_tv.setText(""+present_days)
            //set day wise charges
            var day_wise_charges = (Integer.valueOf(labor_charges.get(0)) * Integer.valueOf(present_days))
            day_wise_charges_tv.setText("₹"+day_wise_charges)
            //set remaining pay amount
            //var advance_amount = Integer.valueOf(total_advance_amt)

            if(day_wise_charges > Integer.valueOf(paid_amt))
            {

                //check remaining monthly paid amount
                var remaining_maonthy_paid_amt = day_wise_charges - Integer.valueOf(paid_amt);

                //check total advance amount and monthly adavance amount
                if(remaining_maonthy_paid_amt > Integer.valueOf(advance_amt))
                {
                    var remaining_month_amt_aft_mamt = remaining_maonthy_paid_amt - Integer.valueOf(advance_amt);
                    //set remaining amount to pay
                    remaining_pay_amt_tv.setText("₹"+remaining_month_amt_aft_mamt)

                    //set monthly advance
                    advance_amt_minus_tv.setText("₹0")

                    //set total advance
                    total_advance_amt_minus_tv.setText("₹0")
                }
                else
                {
                    //set remaining monthly advance amount
                    var monthly_remaining_advance = (Integer.valueOf(advance_amt)  - Integer.valueOf(paid_amt) - Integer.valueOf(remaining_maonthy_paid_amt))

                    //set monthly advance
                    advance_amt_minus_tv.setText("₹"+monthly_remaining_advance)

                    //set total advance
                    //set remaining total advance amount
                    var total_remaining_advance = (Integer.valueOf(total_advance_amt)  - Integer.valueOf(total_paid_amt) - Integer.valueOf(remaining_maonthy_paid_amt))
                    total_advance_amt_minus_tv.setText("₹"+total_remaining_advance)

                    //set remaining amount to pay
                    remaining_pay_amt_tv.setText("₹0")
                }

            }
            else
            {
                //set remaining amount to pay
                remaining_pay_amt_tv.setText("₹0")
                //check remaining monthly paid amount
                var remaining_maonthy_paid_amt = day_wise_charges - Integer.valueOf(paid_amt);

                //check total advance amount and monthly adavance amount
                if(remaining_maonthy_paid_amt > Integer.valueOf(advance_amt))
                {
                    var remaining_month_amt_aft_mamt = remaining_maonthy_paid_amt - Integer.valueOf(advance_amt);
                    //set remaining amount to pay
                    remaining_pay_amt_tv.setText("₹"+remaining_month_amt_aft_mamt)

                    //set monthly advance
                    advance_amt_minus_tv.setText("₹0")

                    //set total advance
                    total_advance_amt_minus_tv.setText("₹0")
                }
                else
                {
                    //set remaining monthly advance amount
                    var monthly_remaining_advance = Integer.valueOf(advance_amt)  - Integer.valueOf(paid_amt)

                    //set monthly advance
                    advance_amt_minus_tv.setText("₹"+monthly_remaining_advance)

                    //set total advance
                    //set remaining total advance amount
                    var total_remaining_advance = Integer.valueOf(total_advance_amt)  - Integer.valueOf(total_paid_amt)
                    total_advance_amt_minus_tv.setText("₹"+total_remaining_advance)

                    //set remaining amount to pay
                    remaining_pay_amt_tv.setText("₹0")
                }
            }


        }
    }
}
