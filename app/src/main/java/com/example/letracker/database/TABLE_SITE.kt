package com.example.letracker.database

import android.content.ContentValues
import android.database.Cursor
import com.example.letracker.other.MyApplication
import com.example.letracker.user.pojo.LaborInfo
import com.example.letracker.user.pojo.SiteInfo

class TABLE_SITE {

    companion object {


        //table name
        val TABLE_NAME : String = "tbl_site"

        //table column
        val ID : String = "id"
        val NAME : String = "name"
        val MOBILE_NUMBER : String = "mobile"
        val ADDRESS : String = "address"


        var CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + MOBILE_NUMBER + " TEXT, "
                + ADDRESS + " TEXT)")


        fun addSite(name : String, mobile: String, address: String)
        {

            val db = MyApplication.db!!.getWritableDatabase()

            val values = ContentValues()
            values.put(NAME, name)
            values.put(MOBILE_NUMBER, mobile)
            values.put(ADDRESS, address)

            db.insert(TABLE_NAME, null, values)

        }
        fun updateSite(id : String,name : String, mobile: String, address: String)
        {
            val db = MyApplication.db!!.getReadableDatabase()
            val uQuery = "UPDATE $TABLE_NAME SET $NAME = '"+name+"', $MOBILE_NUMBER = '"+mobile+"',$ADDRESS = '"+address+"' WHERE $ID = '"+id+"'"
            db.execSQL(uQuery)
        }
        //function for select labor details
        fun getSite(): ArrayList<SiteInfo> {

            var siteInfo = ArrayList<SiteInfo>()
            var cursor: Cursor? = null

            try
            {
                val db = MyApplication.db!!.getReadableDatabase()
                val uQuery = "SELECT * FROM $TABLE_NAME"
                cursor = db.rawQuery(uQuery, null)

                if (cursor.count > 0)
                {
                    while (cursor!!.moveToNext())
                    {
                        var id = cursor.getString(cursor.getColumnIndex(ID))
                        var name = cursor.getString(cursor.getColumnIndex(NAME))
                        var mobile = cursor.getString(cursor.getColumnIndex(MOBILE_NUMBER))
                        var address = cursor.getString(cursor.getColumnIndex(ADDRESS))

                        siteInfo.add(SiteInfo(id,name,mobile,address))

                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return siteInfo!!
        }
    }
}