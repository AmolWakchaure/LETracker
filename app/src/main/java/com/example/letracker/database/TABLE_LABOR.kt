package com.example.letracker.database

import android.content.ContentValues
import android.database.Cursor
import com.example.letracker.other.MyApplication
import com.example.letracker.pojo.LaborInfo

class TABLE_LABOR {

    companion object {


        //table name
        val TABLE_NAME : String = "tbl_labor"

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

        //function for add new labor details
        fun addLabor(name : String, mobile: String, address: String)
        {

            val db = MyApplication.db!!.getWritableDatabase()

            val values = ContentValues()
            values.put(NAME, name)
            values.put(MOBILE_NUMBER, mobile)
            values.put(ADDRESS, address)

            db.insert(TABLE_NAME, null, values)

        }
        //function for select labor details
        fun getLabor(): ArrayList<LaborInfo> {

            var laborInfo = ArrayList<LaborInfo>()
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

                        laborInfo.add(LaborInfo(id,name,mobile,address))

                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return laborInfo!!
        }
    }
}