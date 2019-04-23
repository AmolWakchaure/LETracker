package com.example.letracker.database

import android.content.ContentValues
import android.database.Cursor
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.other.MyApplication
import com.example.letracker.user.pojo.LaborInfo
import com.example.letracker.user.pojo.PaymentInfo
import com.example.letracker.user.pojo.SiteInfo

class TABLE_LABOR_PAYMENT {

    companion object {


        //table name
        val TABLE_NAME : String = "tbl_labor_payment"

        //table column
        val ID : String = "id"
        val LABOR_ID : String = "labor_id"
        val PAYMENT_TYPE : String = "payment_type"
        val PAYMENT_AMT : String = "payment_amt"
        val PAYMENT_TIME_STAMP : String = "payment_time_stamp"


        var CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME
                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LABOR_ID + " TEXT, "
                + PAYMENT_TYPE + " TEXT, "
                + PAYMENT_AMT + " TEXT, "
                + PAYMENT_TIME_STAMP + " TEXT)")


        fun addPayment(labor_id : String, payment_type: String, payment_amt: String,payment_time_stamp: String)
        {

            val db = MyApplication.db!!.getWritableDatabase()

            val values = ContentValues()
            values.put(LABOR_ID, labor_id)
            values.put(PAYMENT_TYPE, payment_type)
            values.put(PAYMENT_AMT, payment_amt)
            values.put(PAYMENT_TIME_STAMP, payment_time_stamp)

            db.insert(TABLE_NAME, null, values)

        }
        //function for select labor details
        fun getPayments(paymentType : String,labor_id: String): ArrayList<PaymentInfo> {

            var paymentInfo = ArrayList<PaymentInfo>()

            try
            {
                val db = MyApplication.db!!.getReadableDatabase()
                var uQuery = "SELECT * FROM $TABLE_NAME WHERE $PAYMENT_TYPE = '"+paymentType+"' AND $LABOR_ID = '"+labor_id+"' ORDER BY $ID DESC"

                var cursor = db.rawQuery(uQuery, null)
                if (cursor.count > 0)
                {
                    while (cursor!!.moveToNext())
                    {
                        var id = cursor.getString(cursor.getColumnIndex(ID))
                        var labor_id = cursor.getString(cursor.getColumnIndex(LABOR_ID))
                        var payment_type = cursor.getString(cursor.getColumnIndex(PAYMENT_TYPE))
                        var payment_amt = cursor.getString(cursor.getColumnIndex(PAYMENT_AMT))
                        var payment_time_stamp = cursor.getString(cursor.getColumnIndex(PAYMENT_TIME_STAMP))

                        paymentInfo.add(PaymentInfo(id,labor_id,payment_type,payment_amt,payment_time_stamp))
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return paymentInfo!!
        }
        //function for select labor details
        fun getTotalPayments(paymentType : String, labor_id: String,flag : String, timeStamp : String): String {//timeStamp = yyyy-MM-dd timedata

            var TOTAL_AMT = "0"
            var uQuery = ""
            try
            {
                val db = MyApplication.db!!.getReadableDatabase()
                if(flag.equals("all"))
                {
                    uQuery = "SELECT SUM($PAYMENT_AMT) AS TOTAL_AMT FROM $TABLE_NAME WHERE $PAYMENT_TYPE = '"+paymentType+"' AND $LABOR_ID = '"+labor_id+"'"
                }
                else//month and year wise
                {
                    var month_year_raw = timeStamp.split(" ")
                    var month_year = month_year_raw.get(0).substring(0,month_year_raw.get(0).length - 3)
                    uQuery = "SELECT SUM($PAYMENT_AMT) AS TOTAL_AMT FROM $TABLE_NAME WHERE $PAYMENT_TYPE = '"+paymentType+"' AND $LABOR_ID = '"+labor_id+"' AND $PAYMENT_TIME_STAMP LIKE '%"+month_year+"%'"
                }

                var cursor = db.rawQuery(uQuery, null)

                if (cursor.count > 0)
                {
                    if (cursor!!.moveToNext())
                    {
                        TOTAL_AMT = cursor.getString(cursor.getColumnIndex("TOTAL_AMT"))


                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return TOTAL_AMT!!
        }
    }
}