package com.example.letracker.database

import android.content.ContentValues
import android.database.Cursor
import com.example.letracker.other.MyApplication
import com.example.letracker.user.pojo.LaborInfo

class TABLE_LABOR {

    companion object {


        //table name
        val TABLE_NAME : String = "tbl_labor"

        //table column
        val ID : String = "id"
        val NAME : String = "name"
        val MOBILE_NUMBER : String = "mobile"
        val ADDRESS : String = "address"
        val CHARGES : String = "charges"


        var CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + MOBILE_NUMBER + " TEXT, "
                + ADDRESS + " TEXT, "
                + CHARGES + " TEXT)")

        //function for add new labor details
        fun addLabor(name : String, mobile: String, address: String,charges : String)
        {

            val db = MyApplication.db!!.getWritableDatabase()

            val values = ContentValues()
            values.put(NAME, name)
            values.put(MOBILE_NUMBER, mobile)
            values.put(ADDRESS, address)
            values.put(CHARGES, charges)

            db.insert(TABLE_NAME, null, values)

        }
        fun updateLabor(id : String,name : String, mobile: String, address: String,charges : String)
        {
            val db = MyApplication.db!!.getReadableDatabase()
            val uQuery = "UPDATE $TABLE_NAME SET $NAME = '"+name+"', $MOBILE_NUMBER = '"+mobile+"',$ADDRESS = '"+address+"',$CHARGES = '"+charges+"' WHERE $ID = '"+id+"'"
            db.execSQL(uQuery)
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
                        var charges = cursor.getString(cursor.getColumnIndex(CHARGES))

                        laborInfo.add(LaborInfo(id,name,mobile,address,charges))

                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return laborInfo!!
        }
        //function for select labor charges
        fun getLaborCharger(labor_id : String): String {

            var laborDetails = "NA"
            var cursor: Cursor? = null

            try
            {
                val db = MyApplication.db!!.getReadableDatabase()
                val uQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = '"+labor_id+"'"
                cursor = db.rawQuery(uQuery, null)

                if (cursor.count > 0)
                {
                    if (cursor!!.moveToNext())
                    {
                        var laborCharges = cursor.getString(cursor.getColumnIndex(CHARGES))
                        var laborName = cursor.getString(cursor.getColumnIndex(NAME))

                        laborDetails = laborCharges+"#"+laborName;
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return laborDetails!!
        }
    }
}