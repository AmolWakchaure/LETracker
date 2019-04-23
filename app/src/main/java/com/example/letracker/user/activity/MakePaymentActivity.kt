package com.example.letracker.user.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.example.letracker.R
import com.example.letracker.database.TABLE_ATTENDANCE
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.database.TABLE_LABOR_PAYMENT
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import kotlinx.android.synthetic.main.activity_adavance_payment.*

class MakePaymentActivity : AppCompatActivity() {


    var LABOR_ID = "2"
    var NAME = "fghfdgffg"
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
        LABOR_ID = bundle.getString(TABLE_LABOR.ID)
        NAME = bundle.getString(TABLE_LABOR.NAME)
        name_edt.setText(NAME)

    }

    private fun setListner() {


        flag = Constants.PAYMENT_ADVANCE
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            if(checkedId == R.id.advance_rb)
            {
               flag = Constants.PAYMENT_ADVANCE
            }
            else if(checkedId == R.id.paid_rb)
            {
                flag = Constants.PAYMENT_PAYMENT
            }
            else if(checkedId == R.id.received_adv_rb)
            {
                flag = Constants.RETURN_ADVANCE
            }
        })

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
