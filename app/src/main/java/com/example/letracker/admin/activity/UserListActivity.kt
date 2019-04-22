package com.example.letracker.admin.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.letracker.R
import com.example.letracker.admin.adapter.UserListAdapter
import com.example.letracker.admin.pojo.UserModel
import com.example.letracker.authentication.LoginActivity
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.other.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_list.*
import java.util.ArrayList

class UserListActivity : AppCompatActivity() {

    lateinit var pd: ProgressDialog
    //declare firebase instance
    lateinit var db : FirebaseFirestore

    internal var modelList: ArrayList<UserModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        //initialise firebase instance
        db = FirebaseFirestore.getInstance()
        //Tm.refreshData(db)
        pd = ProgressDialog(this)

        setClickListner()
        //fetch user list from firestore
        if(M.isNetworkAvailable())
        {
            getUserList()
        }
        else
        {
            M.t(Constants.CONNECTION)
        }
    }

    private fun setClickListner() {


        go_back.setOnClickListener {

            finish()
        }
        logout_app.setOnClickListener {

            var snackbar = Snackbar.make(main_layout,"Do you want to logout?",Snackbar.LENGTH_LONG)
                .setAction("Logout",{

                    MyApplication.editor.clear().commit()
                    var i = Intent(this,LoginActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                    finish()

                });
            snackbar.show()
        }
    }

    private fun getUserList() {

        pd.setTitle("Getting User list, please wait...")
        pd.show()
        db.collection(Constants.DEVICE_MASTER)
            .get()
            .addOnCompleteListener { task ->
                modelList.clear()
                pd.dismiss()
                if(task.result!!.isEmpty)
                {
                    M.t("Oops ! user not found")
                }
                else
                {
                    for (doc in task.result!!)
                    {
                        var id = doc.getString(Constants.ID);
                        var user_name = doc.getString(Constants.USER_NAME);
                        var password = doc.getString(Constants.PASSWORD);
                        var device_mac_id = doc.getString(Constants.DEVICE_MAC_ID);
                        var device_firmware = doc.getString(Constants.DEVICE_FIRMAWARE);

                        modelList.add(UserModel(id!!,user_name!!,password!!,device_mac_id!!,device_firmware!!))
                    }

                    //set layout manager to recycler view
                    user_list_rl.layoutManager = LinearLayoutManager(MyApplication.context, LinearLayout.VERTICAL,false)
                    val adapter = UserListAdapter(modelList);
                    user_list_rl.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                pd.dismiss()

            }
    }
}
