package com.example.letracker.other

import android.support.design.widget.Snackbar
import android.support.v4.util.LogWriter
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast

class M {

    companion object {

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
        }
    }

}