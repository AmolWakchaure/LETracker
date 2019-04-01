package com.example.letracker.admin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.example.letracker.R
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import kotlinx.android.synthetic.main.activity_device_firmware.*

class DeviceFirmwareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_firmware)

        //get firm ware details

        var bundle = intent.extras
        var device_firmware = bundle.getString(Constants.DEVICE_FIRMAWARE)

        if(device_firmware.contains("#"))
        {
            var device_data = device_firmware.split("#")

            var brand_data = device_data.get(0).split(":")
            var devicee_data = device_data.get(1).split(":")
            var model_data = device_data.get(2).split(":")
            var id_data = device_data.get(3).split(":")
            var product_data = device_data.get(4).split(":")
            var sdk_data = device_data.get(5).split(":")
            var release_data = device_data.get(6).split(":")
            var incremental_data = device_data.get(7).split(":")


            firmware_tv.setText(Html.fromHtml("<b>Brand : </b>"+brand_data.get(1)+"<br>"
            +"<b>Device : </b>"+devicee_data.get(1)+"<br>"
            +"<b>Model : </b>"+model_data.get(1)+"<br>"
            +"<b>Id : </b>"+id_data.get(1)+"<br>"
            +"<b>Product : </b>"+product_data.get(1)+"<br>"
            +"<b>SDK : </b>"+sdk_data.get(1)+"<br>"
            +"<b>Release : </b>"+release_data.get(1)+"<br>"
            +"<b>Incremental : </b>"+incremental_data.get(1)))
        }
        else
        {
            M.t("# not found")
        }


        go_back.setOnClickListener {

            finish()
        }
    }
}
