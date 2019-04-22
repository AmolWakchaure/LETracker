package com.example.letracker.user.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.letracker.R
import com.example.letracker.database.TABLE_ATTENDANCE
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.database.TABLE_LABOR_PAYMENT
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import kotlinx.android.synthetic.main.activity_adavance_payment.*

class AdavancePaymentActivity : AppCompatActivity() {


    var LABOR_ID = ""
    var NAME = ""
    var flag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adavance_payment)

        //set click listner
        setListner()
        getIntentData();
    }

    private fun getIntentData() {

        //set mac address
        var bundle = intent.extras
        flag = bundle.getString(Constants.FLAG)
        LABOR_ID = bundle.getString(TABLE_LABOR.ID)
        NAME = bundle.getString(TABLE_LABOR.NAME)
        name_edt.setText(NAME)

        if(flag.equals("advance"))
        {
            title_tv.setText("Make Adavance Payment")
        }
        else if(flag.equals("payment"))
        {
            title_tv.setText("Make Labor Payment")
        }
        else
        {
            title_tv.setText("Ooops ! error occured")
        }
    }

    private fun setListner() {


       /* var fm = supportFragmentManager
        var advancePaymentFragment = AdvancePaymentReportActivity()
        fm.beginTransaction().replace(R.id.main_contenier,advancePaymentFragment).commit()
*/
        payment_report_btn.setOnClickListener {

            var i = Intent(this,AdvancePaymentReportActivity::class.java)
            i.putExtra(TABLE_ATTENDANCE.LABOR_ID,LABOR_ID)
            startActivity(i)
        }
        go_back.setOnClickListener {

            finish()
        }
        submit_btn.setOnClickListener {

            validateDetails()
        }
    }
    private fun validateDetails() {


        if(!M.validateEmptyField(parrent_layout,amt_to_paid_edt,"Enter amount to paid"))
        {
            return
        }

        //check update or insert
        //save labor details
        TABLE_LABOR_PAYMENT.addPayment(
            LABOR_ID,
            flag,
            amt_to_paid_edt.text.toString().trim(),
            ""+M.getSystemDateTime())
        //finally empty editext
        amt_to_paid_edt.setText("")
        M.t("Labor payment added successfully")

    }
}
