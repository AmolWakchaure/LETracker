package com.example.letracker.user.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.letracker.R
import com.example.letracker.user.adapters.LaborAdapter
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.other.MyApplication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setClickListner()
        getLaborData()



    }

    private fun setClickListner() {

        new_labor_tv.setOnClickListener {

            startActivity(Intent(this,AddNewLaborActivity::class.java))
        }
    }

    private fun getLaborData() {

        var LABOR_INFO = TABLE_LABOR.getLabor()
        if(LABOR_INFO.isEmpty())
        {
            hide_layout.setVisibility(View.VISIBLE)
            labor_list.setVisibility(View.GONE)
        }
        else
        {
            labor_list.setVisibility(View.VISIBLE)
            labor_list.layoutManager = LinearLayoutManager(MyApplication.context, LinearLayout.VERTICAL, false)
            val adapter = LaborAdapter(LABOR_INFO);
            labor_list.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()

        getLaborData()
    }


}
