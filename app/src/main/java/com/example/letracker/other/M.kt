package com.example.letracker.other

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.util.LogWriter
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class M {

    companion object {

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

    }

}