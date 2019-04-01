package com.example.letracker.user.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.letracker.R
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.other.M
import kotlinx.android.synthetic.main.activity_add_new_labor.*

class AddNewLaborActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_labor)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setClickListner();
    }

    private fun setClickListner() {

        submit_btn.setOnClickListener {

            validateDetails()

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
        //save labor details
        TABLE_LABOR.addLabor(
            name_edt.text.toString(),
            mobile_edt.text.toString(),
            address_edt.text.toString())
        //finally empty editext
        name_edt.setText("")
        mobile_edt.setText("")
        address_edt.setText("")
        M.t("Labor details save successfully")
    }
}
