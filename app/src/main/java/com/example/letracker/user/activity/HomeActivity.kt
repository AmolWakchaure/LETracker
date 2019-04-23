package com.example.letracker.user.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.letracker.R
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.other.M
import com.example.letracker.payment_history.PaymentHistoryActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        checkPermission();
        setClickListner()
        //set labour count
        setLaborCount()
    }
    fun checkPermission()
    {

        if(M.checkPermission(this, android.Manifest.permission.CALL_PHONE))
        {

        }
        else M.askPermission(this, android.Manifest.permission.CALL_PHONE)
    }

    private fun setLaborCount() {

        var LABOR_INFO = TABLE_LABOR.getLabor()
        if(!LABOR_INFO.isEmpty())
        {
            labor_count.setText("("+LABOR_INFO.size+")")
        }

    }


    private fun setClickListner() {



        all_labor_li.setOnClickListener {

            var LABOR_INFO = TABLE_LABOR.getLabor()
            if(LABOR_INFO.isEmpty())
            {
                M.t("Oops ! labor details not found")
            }
            else
            {
                startActivity(Intent(this,LaborListActivity::class.java))
            }

        }
        make_payment_li.setOnClickListener {

            startActivity(Intent(this,MakePaymentActivity::class.java))
        }
        payment_history_li.setOnClickListener {

            startActivity(Intent(this,PaymentHistoryActivity::class.java))
        }
        site_details_li.setOnClickListener {

            var i = Intent(this,SiteListActivity::class.java)
            startActivity(i)
        }
    }
}
