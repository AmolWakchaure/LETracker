package com.example.letracker.other

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.letracker.R
import com.example.letracker.admin.activity.UserListActivity
import com.example.letracker.authentication.LoginActivity
import com.example.letracker.user.activity.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        //check navigation
        var username = MyApplication.prefs.getString(Constants.USER_NAME,"0")
        var password = MyApplication.prefs.getString(Constants.PASSWORD,"0")

        if(username.equals("0") && password.equals("0"))
        {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        else
        {
            if(username.equals("amol123") && password.equals("amol123"))
            {
                startActivity(Intent(this, UserListActivity::class.java))
                finish()
            }
            else
            {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }
}
