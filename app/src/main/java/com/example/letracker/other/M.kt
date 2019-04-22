package com.example.letracker.other

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.LogWriter
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.letracker.user.pojo.CalenderInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList



class M {

    companion object {

        //return month and year
        fun returnMonthandYear() : String
        {
            //yyyy-MM-dd HH:mm:ss

            var monthName = "NA"
            var dateTime = getSystemDateTime()!!.split(" ")
            var date = dateTime.get(0).split("-")

            var current_month = date.get(1)
            var current_year = date.get(0)
            try
            {


                if(current_month.equals("01") || current_month.equals("01"))
                {
                    monthName =  "January"
                }
                else if(current_month.equals("02") || current_month.equals("02"))
                {
                    monthName =  "February"
                }
                else if(current_month.equals("03") || current_month.equals("03"))
                {
                    monthName =  "March"
                }
                else if(current_month.equals("04") || current_month.equals("04"))
                {
                    monthName =  "April"
                }
                else if(current_month.equals("05") || current_month.equals("05"))
                {
                    monthName =  "May"
                }
                else if(current_month.equals("06") || current_month.equals("06"))
                {
                    monthName =  "June"
                }
                else if(current_month.equals("07") || current_month.equals("07"))
                {
                    monthName =  "July"
                }
                else if(current_month.equals("08") || current_month.equals("08"))
                {
                    monthName =  "August"
                }
                else if(current_month.equals("09") || current_month.equals("09"))
                {
                    monthName =  "September"
                }
                else if(current_month.equals("10"))
                {
                    monthName =  "October"
                }
                else if(current_month.equals("11"))
                {
                    monthName =  "November"
                }
                else if(current_month.equals("12"))
                {
                    monthName =  "December"
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
            return  monthName+" "+current_year;

        }
        //return moth calender details
        fun returnMonthDetails(attendance_string : String) : ArrayList<CalenderInfo>
        {
            var calender_info = ArrayList<CalenderInfo>();


            try {
                var dateTimeArray = getSystemDateTime()!!.split(" ")
                var dateArray = dateTimeArray.get(0).split("-")

                val date = SimpleDateFormat("dd/MM/yyyy").parse(""+dateArray[2]+"/"+dateArray[1]+"/"+dateArray[0])
                val cal = Calendar.getInstance()
                cal.time = date
                cal.set(Calendar.DAY_OF_MONTH, 1)
                val myMonth = cal.get(Calendar.MONTH)

                while (myMonth == cal.get(Calendar.MONTH)) {
                    val dateString = cal.time.toString().replace("00:00:00 IST ", "").split(" ")

                    val day = dateString[0]
                    val month = dateString[1]
                    val datee = dateString[2]
                    val year = dateString[3]

                    var sysDate = dateArray.get(2)
                    if(datee.equals(sysDate))
                    {
                        if(attendance_string.contains(datee))
                        {
                            calender_info.add(CalenderInfo(datee,month,year,day,"true","P"))
                        }
                        else
                        {
                            calender_info.add(CalenderInfo(datee,month,year,day,"true","A"))
                        }

                    }
                    else
                    {
                        if(attendance_string.contains(datee))
                        {
                            calender_info.add(CalenderInfo(datee,month,year,day,"false","P"))
                        }
                        else
                        {
                            calender_info.add(CalenderInfo(datee,month,year,day,"false","A"))
                        }

                    }
                    cal.add(Calendar.DAY_OF_MONTH, 1)

                }
            } catch (e: Exception) {

            }

            return calender_info

        }
        fun getFirmwareDetails() : String
        {

            var LINE_SEPARATOR  = "#"
            val deviceDetails = StringBuilder()

            deviceDetails.append("Brand:")
            deviceDetails.append(Build.BRAND)
            deviceDetails.append(LINE_SEPARATOR)
            deviceDetails.append("Device:")
            deviceDetails.append(Build.DEVICE)
            deviceDetails.append(LINE_SEPARATOR)
            deviceDetails.append("Model:")
            deviceDetails.append(Build.MODEL)
            deviceDetails.append(LINE_SEPARATOR)
            deviceDetails.append("Id:")
            deviceDetails.append(Build.ID)
            deviceDetails.append(LINE_SEPARATOR)
            deviceDetails.append("Product:")
            deviceDetails.append(Build.PRODUCT)
            deviceDetails.append(LINE_SEPARATOR)
            deviceDetails.append("SDK:")
            deviceDetails.append(Build.VERSION.SDK_INT)
            deviceDetails.append(LINE_SEPARATOR)
            deviceDetails.append("Release:")
            deviceDetails.append(Build.VERSION.RELEASE)
            deviceDetails.append(LINE_SEPARATOR)
            deviceDetails.append("Incremental:")
            deviceDetails.append(Build.VERSION.INCREMENTAL)
            deviceDetails.append(LINE_SEPARATOR)

            return  deviceDetails.toString()

        }
        //check network is available
        fun isNetworkAvailable(): Boolean
        {
            val connectivityManager = MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else false
        }

        //toast
        fun t(message : String)
        {
            Toast.makeText(MyApplication.context,message,Toast.LENGTH_LONG).show()
        }
        //log
        fun e(message : String)
        {
            Log.e("LETracker_LOG",message)
        }
        //snackbar
        fun s(view : View,message : String)
        {
            Snackbar.make(view,message,Snackbar.LENGTH_LONG).show()
        }
        //validation function goes from here
        fun validateEmptyField(view : View, editext : EditText, message: String) : Boolean
        {
            if(editext.text.toString().trim().isEmpty())
            {
                s(view,message)
                return false
            }
            else
            {
                return true
            }
        }//validation function goes from here
        fun validateConfirmPass(view : View, password : EditText,conf_password : EditText, message: String) : Boolean
        {
            if(password.text.toString().equals(conf_password.text.toString()))
            {
                return true

            }
            else
            {
                s(view,message)
                return false
            }
        }
        //get current date time
        fun getSystemDateTime(): String? {

            var systemTime: String? = null
            try {
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val cal = Calendar.getInstance()
                systemTime = df.format(cal.time)
            } catch (e: Exception) {
            }
            return systemTime
        }

        //for runtime permission
        fun checkPermission(context: Context, permission: String): Boolean {

            // Ask for permission if it wasn't granted yet
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

        }

        fun askPermission(context: Context, permission: String) {

            ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), 12)
        }

    }

}