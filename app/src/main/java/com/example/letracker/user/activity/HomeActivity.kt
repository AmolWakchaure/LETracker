package com.example.letracker.user.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.letracker.R
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import kotlinx.android.synthetic.main.activity_home.*
import java.util.jar.Manifest

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

        create_labor_li.setOnClickListener {

            var i = Intent(this,AddNewLaborActivity::class.java)
            i.putExtra(Constants.FLAG, Constants.FLAG_ADD)
            startActivity(i)

        }
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
        work_history_li.setOnClickListener {

        }
        payment_history_li.setOnClickListener {

        }
        site_details_li.setOnClickListener {

            var i = Intent(this,SiteListActivity::class.java)
            startActivity(i)
        }
    }
}
