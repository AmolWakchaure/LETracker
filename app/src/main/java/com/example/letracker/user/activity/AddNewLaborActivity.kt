package com.example.letracker.user.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.letracker.R
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import kotlinx.android.synthetic.main.activity_add_new_labor.*

class AddNewLaborActivity : AppCompatActivity() {


    lateinit var ID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_labor)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        getIntentDetails()
        setClickListner();
    }

    private fun getIntentDetails() {

        //set mac address
        var bundle = intent.extras
        var flag = bundle.getString(Constants.FLAG)

        if(flag.equals(Constants.FLAG_UPDATE))
        {
            title_tv.setText("Update Labor details")
            submit_btn.setText("Update")
            ID = bundle.getString(TABLE_LABOR.ID)
            var NAME = bundle.getString(TABLE_LABOR.NAME)
            var MOBILE_NUMBER = bundle.getString(TABLE_LABOR.MOBILE_NUMBER)
            var ADDRESS = bundle.getString(TABLE_LABOR.ADDRESS)
            var CHARGES = bundle.getString(TABLE_LABOR.CHARGES)

            name_edt.setText(NAME)
            mobile_edt.setText(MOBILE_NUMBER)
            address_edt.setText(ADDRESS)
            charges_edt.setText(CHARGES)
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
        if(!M.validateEmptyField(parrent_layout,name_edt,"Enter labor name"))
        {
            return
        }
        if(!M.validateEmptyField(parrent_layout,mobile_edt,"Enter labor mobile"))
        {
            return
        }
        if(!M.validateEmptyField(parrent_layout,address_edt,"Enter labor address"))
        {
            return
        }
        if(!M.validateEmptyField(parrent_layout,charges_edt,"Enter labor charges"))
        {
            return
        }
        //check update or insert
        if(submit_btn.text.toString().equals("Update"))
        {
            //update labor
            TABLE_LABOR.updateLabor(
                ID,
                name_edt.text.toString(),
                mobile_edt.text.toString(),
                address_edt.text.toString(),
                charges_edt.text.toString().trim())
            M.t("Labor updated successfully")
        }
        else
        {
            //save labor details
            TABLE_LABOR.addLabor(
                name_edt.text.toString(),
                mobile_edt.text.toString(),
                address_edt.text.toString(),
                charges_edt.text.toString().trim())
            //finally empty editext
            name_edt.setText("")
            mobile_edt.setText("")
            address_edt.setText("")
            charges_edt.setText("")
            M.t("Labor added successfully")
        }

    }
}
