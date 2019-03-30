package com.example.letracker.other

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.letracker.database.DBHelper

class MyApplication : Application() {

    companion object {

        lateinit var context : Context
        lateinit var prefs : SharedPreferences
        lateinit var editor : SharedPreferences.Editor

        var db: DBHelper? = null
    }

    override fun onCreate() {
        super.onCreate()

        //initialise context
        context = applicationContext
        //create db object
        if(db == null)
        {
            db = DBHelper(context)
            db!!.writableDatabase
        }
        //initialise shared prefrences
        prefs = getSharedPreferences(Constants.PREF_NAME,0)
        editor = prefs.edit()
        editor.commit()
    }
}