package com.example.letracker.other

interface Constants {

    companion object {

        var FLAG = "flag"
        var FLAG_ADD = "flag_add"
        var FLAG_UPDATE = "flag_update"
        var ATTENDANCE_STRING = "att_string"
        var PAYMENT_ADVANCE = "advance"
        var PAYMENT_PAYMENT = "payment"


        //shared preferences parameter
        var PREF_NAME = "LETTracker"

        //sqlite database parameter
        var DATABASE_NAME = "LETTracker.db"
        var DATABASE_VERSION = 1

        //firebase tables
        var DEVICE_MASTER = "tbl_device_master"

        var ID = "id"
        var DEVICE_MAC_ID = "device_mac_id"
        var DEVICE_FIRMAWARE = "device_firmware"
        var USER_NAME = "user_name"
        var PASSWORD = "password"
        var CREATED_TIMESTAMP = "created_timestamp"

        var CONNECTION : String = "Oops ! internet connection off"


    }
}