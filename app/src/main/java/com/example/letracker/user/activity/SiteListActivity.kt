package com.example.letracker.user.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.letracker.R
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.database.TABLE_SITE
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.other.MyApplication
import com.example.letracker.user.adapters.LaborAdapter
import com.example.letracker.user.adapters.SiteAdapter
import kotlinx.android.synthetic.main.activity_site_list.*

class SiteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list)

        setClickListner()
        setLaborCount();
        getSiteData()
    }
    private fun getSiteData() {

        var LABOR_INFO = TABLE_SITE.getSite()
        if(LABOR_INFO.isEmpty())
        {
            M.t("Site not found")
        }
        else
        {
            //labor_list.setVisibility(View.VISIBLE)
            labor_list_rl.layoutManager = LinearLayoutManager(MyApplication.context, LinearLayout.VERTICAL, false)
            val adapter = SiteAdapter(LABOR_INFO);
            labor_list_rl.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
    private fun setLaborCount() {

        var LABOR_INFO = TABLE_SITE.getSite()
        if(!LABOR_INFO.isEmpty())
        {
            title_tv.setText("All Sites ("+LABOR_INFO.size+")")
        }

    }
    private fun setClickListner() {

        go_back.setOnClickListener {

            finish()
        }
        new_labor_btn.setOnClickListener {


            var i = Intent(this,SiteDetailsActivity::class.java)
            i.putExtra(Constants.FLAG, Constants.FLAG_ADD)
            startActivity(i)

        }
    }
    override fun onResume() {
        super.onResume()

        getSiteData()
    }
}
