package com.example.letracker.payment_history

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.letracker.R
import com.example.letracker.database.TABLE_LABOR
import com.example.letracker.database.TABLE_LABOR_PAYMENT
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.other.MyApplication
import com.example.letracker.user.adapters.PaymentAdapter
import kotlinx.android.synthetic.main.activity_payment_history.*

class PaymentHistoryActivity : AppCompatActivity() {

    var labor_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_history)


        setListner()

        //get labor id
        var bundle = intent.extras
        labor_id = bundle.getString(TABLE_LABOR.ID)



        paid_rb.setChecked(true)
        getPaymentData(Constants.PAYMENT_PAYMENT);
    }

    private fun setListner() {


        go_back.setOnClickListener {

            finish()
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            if(checkedId == R.id.advance_rb)
            {
                getPaymentData(Constants.PAYMENT_ADVANCE)

            }
            else if(checkedId == R.id.paid_rb)
            {
                getPaymentData(Constants.PAYMENT_PAYMENT)
            }
            else if(checkedId == R.id.return_advance_rb)
            {
                getPaymentData(Constants.RETURN_ADVANCE)
            }
        })

    }
    private fun getPaymentData(payment_type : String) {

        var PAYMENT_INFO = TABLE_LABOR_PAYMENT.getPayments(payment_type,labor_id)

        if(PAYMENT_INFO.isEmpty())
        {
            hide_amt_li.setVisibility(View.GONE)
            hide_layout.setVisibility(View.VISIBLE)
            hide_img.setImageResource(R.drawable.ic_money_notfound)
            hide_tv.setText("Oops ! payment details not found")
        }
        else
        {
            hide_layout.setVisibility(View.GONE)
            payment_history_rl.layoutManager = LinearLayoutManager(MyApplication.context, LinearLayout.VERTICAL, false)
            val adapter = PaymentAdapter(PAYMENT_INFO);
            payment_history_rl.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        //set amount
        //get total advance amount
        if(payment_type.equals(Constants.PAYMENT_ADVANCE))
        {
            hide_amt_li.setVisibility(View.VISIBLE)
            var advance_amount = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.PAYMENT_ADVANCE,labor_id,"all",""+M.getSystemDateTime())
            amount_tv.setText("Advance Amount : ₹"+advance_amount)
        }
        else if(payment_type.equals(Constants.PAYMENT_PAYMENT))
        {
            hide_amt_li.setVisibility(View.VISIBLE)
            var paid_amount = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.PAYMENT_PAYMENT,labor_id,"all",""+M.getSystemDateTime())
            amount_tv.setText("Paid Amount : ₹"+paid_amount)
        }
        else if(payment_type.equals(Constants.RETURN_ADVANCE))
        {
            hide_amt_li.setVisibility(View.VISIBLE)
            var return_advance_amount = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.RETURN_ADVANCE,labor_id,"all",""+M.getSystemDateTime())
            amount_tv.setText("Received Advance Amount : ₹"+return_advance_amount)
        }

    }
}
