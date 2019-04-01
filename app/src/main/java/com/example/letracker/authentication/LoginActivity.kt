package com.example.letracker.authentication

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.letracker.R
import com.example.letracker.admin.activity.UserListActivity
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.other.MyApplication
import com.example.letracker.user.activity.HomeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {



    //firestore instance
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        //get device id first
        var deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID + "");
        mac_id_tv.setText(""+deviceID)


        //firestore
        db = FirebaseFirestore.getInstance()

        //send mac id to firebase
        if(M.isNetworkAvailable())
        {

            //first check mac id already exists
            userNameExistsAlready(deviceID);

        }
        verify_btn.setOnClickListener {

            validateData()
        }
    }
    private fun userNameExistsAlready(mac_id : String)
    {
        try
        {
            val mQuery = db.collection(Constants.DEVICE_MASTER)
                .whereEqualTo(Constants.DEVICE_MAC_ID, mac_id)

            mQuery.addSnapshotListener { queryDocumentSnapshots, e ->

                if(queryDocumentSnapshots!!.isEmpty)
                {
                    //if user not found for this mac_id
                    //then create new user
                    createNewUser(mac_id)
                }
                else
                {
                    for (ds in queryDocumentSnapshots!!)
                    {
                        if (ds.exists())
                        {
                            //if found for this mac_id
                            M.t("Oops ! this device is already registerd before")
                            break

                        }
                    }
                }

            }
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }

    }

    private fun createNewUser(mac_id: String) {

        //progress dialog
        var pd = ProgressDialog(this)
        pd.setTitle("Creating user, please wait...")
        pd.show()

        //random id for each data to be stored
        val id = UUID.randomUUID().toString()

        val doc = HashMap<String, Any>()

        doc[Constants.ID] = id
        doc[Constants.DEVICE_MAC_ID] = mac_id
        doc[Constants.DEVICE_FIRMAWARE] = M.getFirmwareDetails()
        doc[Constants.USER_NAME] = ""
        doc[Constants.PASSWORD] = ""
        doc[Constants.CREATED_TIMESTAMP] = M.getSystemDateTime().toString()


        //add this data
        db.collection(Constants.DEVICE_MASTER).document(id).set(doc)
            .addOnCompleteListener(OnCompleteListener<Void> {
                pd.dismiss()
                M.t("User created")
            })
            .addOnFailureListener(OnFailureListener {
                pd.dismiss()
                Toast.makeText(this, "unable to upload data ...", Toast.LENGTH_LONG).show()
            })
    }

    private fun validateData() {


        if(!M.validateEmptyField(main_layout,user_name_tv,"Enter user name"))
        {
            return
        }
        if(!M.validateEmptyField(main_layout,password_tv,"Enter password"))
        {
            return
        }


        var username = user_name_tv.text.toString()
        var password = password_tv.text.toString()

        //validate admin first
        if(username.equals("amol123") && password.equals("amol123"))
        {
            //follow admin login
            MyApplication.editor.putString(Constants.USER_NAME,username).commit()
            MyApplication.editor.putString(Constants.PASSWORD,password).commit()

            startActivity(Intent(this,UserListActivity::class.java))
            finish()
        }
        else
        {
            //follow admin login
            MyApplication.editor.putString(Constants.USER_NAME,username).commit()
            MyApplication.editor.putString(Constants.PASSWORD,password).commit()
            //follow user login
            //follow admin login
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }


    }
}
