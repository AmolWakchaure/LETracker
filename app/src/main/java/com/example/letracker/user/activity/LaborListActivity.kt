package com.example.letracker.user.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.letracker.R
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.other.MyApplication
import com.example.letracker.user.adapters.LaborAdapter
import kotlinx.android.synthetic.main.activity_labor_list.*

class LaborListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labor_list)

        setClickListner()

        setLaborCount();
        getLaborData()
    }

    private fun setLaborCount() {

        var LABOR_INFO = TABLE_LABOR.getLabor()
        if(!LABOR_INFO.isEmpty())
        {
            title_tv.setText("All Labors ("+LABOR_INFO.size+")")
        }

    }
    private fun setClickListner() {

        go_back.setOnClickListener {

            finish()
        }
        new_labor_btn.setOnClickListener {


            var i = Intent(this,AddNewLaborActivity::class.java)
            i.putExtra(Constants.FLAG, Constants.FLAG_ADD)
            startActivity(i)

        }
    }
    private fun getLaborData() {

        var LABOR_INFO = TABLE_LABOR.getLabor()
        if(LABOR_INFO.isEmpty())
        {
            M.t("Labor not found")
        }
        else
        {
            //labor_list.setVisibility(View.VISIBLE)
            labor_list_rl.layoutManager = LinearLayoutManager(MyApplication.context, LinearLayout.VERTICAL, false)
            val adapter = LaborAdapter(LABOR_INFO);
            labor_list_rl.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()

        getLaborData()
    }
}
