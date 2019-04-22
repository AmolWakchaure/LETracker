package com.example.letracker.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import java.lang.Exception

class DBHelper (context : Context) : SQLiteOpenHelper(context, Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {

        try
        {
            db!!.execSQL(TABLE_LABOR.CREATE_TABLE)
            db!!.execSQL(TABLE_SITE.CREATE_TABLE)
            db!!.execSQL(TABLE_ATTENDANCE.CREATE_TABLE)
            db!!.execSQL(TABLE_LABOR_PAYMENT.CREATE_TABLE)
        }
        catch (e : Exception)
        {
            M.e(""+e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {



    }


}