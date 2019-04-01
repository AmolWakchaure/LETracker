package com.example.letracker.admin.activity

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.letracker.R
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_assign_user.*
import java.util.HashMap

class AssignUserActivity : AppCompatActivity() {


    //firestore instance
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_user)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)


        //firestore
        db = FirebaseFirestore.getInstance()

        //set mac address
        var bundle = intent.extras
        var device_mac_id = bundle.getString(Constants.DEVICE_MAC_ID)
        var id = bundle.getString(Constants.ID)
        var user_name = bundle.getString(Constants.USER_NAME)
        var password = bundle.getString(Constants.PASSWORD)

        //set user data
        device_mac_id_tv.setText(device_mac_id)
        user_name_tv.setText(user_name)
        password_tv.setText(password)
        confirm_password_tv.setText(password)

        //set click listner
        go_back.setOnClickListener {

            finish()
        }
        submit_btn.setOnClickListener {


            if(M.isNetworkAvailable())
            {
                validateDetails(id)
            }
            else
            {
                M.t(Constants.CONNECTION)
            }

        }
    }

    private fun validateDetails(id : String) {

        if(!M.validateEmptyField(main_layout,user_name_tv,"Enter user name"))
        {
            return
        }
        if(!M.validateEmptyField(main_layout,password_tv,"Enter password"))
        {
            return
        }
        if(!M.validateEmptyField(main_layout,confirm_password_tv,"Enter confirm password"))
        {
            return
        }
        if(!M.validateConfirmPass(main_layout,password_tv,confirm_password_tv,"Password and confirm password should be same"))
        {
            return
        }
        //proceed to assign user
        assignUserNamePass(
            user_name_tv.text.toString(),
            password_tv.text.toString(),
            id)
    }

    fun assignUserNamePass(userName: String, password: String, id : String) {
        //progress dialog
        var pd = ProgressDialog(this)
        pd.setTitle("Assigning user, please wait...")
        pd.show()

        val doc = HashMap<String, Any>()

        doc[Constants.USER_NAME] = userName
        doc[Constants.PASSWORD] = password

        db.collection(Constants.DEVICE_MASTER).document(id)
            .update(doc)
            .addOnCompleteListener(OnCompleteListener<Void> {
                pd.dismiss()
                M.t("User assign successfully")
                finish()
            })
            .addOnFailureListener(OnFailureListener { e ->
                pd.dismiss()
                M.t("Problem to assign user")
            })
    }
}
