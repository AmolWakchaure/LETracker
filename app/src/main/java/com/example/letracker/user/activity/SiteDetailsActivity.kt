package com.example.letracker.user.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.letracker.R
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.database.TABLE_SITE
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import kotlinx.android.synthetic.main.activity_site_details.*

class SiteDetailsActivity : AppCompatActivity() {

    lateinit var ID : String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_details)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setClickListner();
        getIntentDetails()
    }
    private fun getIntentDetails() {

        //set mac address
        var bundle = intent.extras
        var flag = bundle.getString(Constants.FLAG)

        if(flag.equals(Constants.FLAG_UPDATE))
        {
            submit_btn.setText("Update")
            ID = bundle.getString(TABLE_SITE.ID)
            var NAME = bundle.getString(TABLE_SITE.NAME)
            var MOBILE_NUMBER = bundle.getString(TABLE_SITE.MOBILE_NUMBER)
            var ADDRESS = bundle.getString(TABLE_SITE.ADDRESS)

            name_edt.setText(NAME)
            mobile_edt.setText(MOBILE_NUMBER)
            address_edt.setText(ADDRESS)
        }
    }
    private fun setClickListner() {

        submit_btn.setOnClickListener {

            validateDetails()

        }
        go_back.setOnClickListener {

            finish()

        }

    }
    private fun validateDetails() {

        //first we need to check validations
        if(!M.validateEmptyField(parrent_layout,name_edt,"Enter site name"))
        {
            return
        }
        if(!M.validateEmptyField(parrent_layout,mobile_edt,"Enter owner mobile"))
        {
            return
        }
        if(!M.validateEmptyField(parrent_layout,address_edt,"Enter owner address"))
        {
            return
        }

        //check update or insert
        if(submit_btn.text.toString().equals("Update"))
        {
            //update labor
            TABLE_SITE.updateSite(
                ID,
                name_edt.text.toString(),
                mobile_edt.text.toString(),
                address_edt.text.toString())
            M.t("Site updated successfully")
        }
        else
        {
            //save labor details
            TABLE_SITE.addSite(
                name_edt.text.toString(),
                mobile_edt.text.toString(),
                address_edt.text.toString())
            //finally empty editext
            name_edt.setText("")
            mobile_edt.setText("")
            address_edt.setText("")

            M.t("Site added successfully")
        }

    }



}
