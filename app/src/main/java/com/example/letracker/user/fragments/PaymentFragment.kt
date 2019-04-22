package com.example.letracker.user.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.letracker.R
import com.example.letracker.database.TABLE_ATTENDANCE
import com.example.letracker.database.TABLE_LABOR_PAYMENT
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.other.MyApplication
import com.example.letracker.user.adapters.PaymentAdapter
import kotlinx.android.synthetic.main.layout_advance_fragment.*

class PaymentFragment : Fragment() {

    var labor_id = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.layout_advance_fragment, container, false)

        labor_id = arguments!!.getString(TABLE_ATTENDANCE.LABOR_ID)
        M.e("labor_id : "+labor_id)

        getPaymentData(view)

        return  view
    }
    private fun getPaymentData(view : View) {

        val advance_amt_tv = view.findViewById(R.id.advance_amt) as TextView
        val paid_amt_tv = view.findViewById(R.id.paid_amt) as TextView
        val remaining_amt_tv = view.findViewById(R.id.remaining_amt) as TextView

        //get total amount data
        var advance_amt = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.PAYMENT_ADVANCE,labor_id,"all",""+M.getSystemDateTime())
        if(!advance_amt.equals("NA"))
        {
            advance_amt_tv.setText(""+advance_amt)
        }
        var paid_amt = TABLE_LABOR_PAYMENT.getTotalPayments(Constants.PAYMENT_PAYMENT,labor_id,"all",""+M.getSystemDateTime())
        if(!paid_amt.equals("NA"))
        {
            paid_amt_tv.setText(""+paid_amt)
        }
        if(advance_amt != "NA" && paid_amt != "NA")
        {
            remaining_amt_tv.setText(""+(Integer.valueOf(advance_amt) - Integer.valueOf(paid_amt)))
        }

        var PAYMENT_INFO = TABLE_LABOR_PAYMENT.getPayments(Constants.PAYMENT_PAYMENT,labor_id)

        if(PAYMENT_INFO.isEmpty())
        {
            M.t("Payment info not found")
        }
        else
        {
            val payment_rv = view.findViewById(R.id.payment_rv) as RecyclerView
            payment_rv.layoutManager = LinearLayoutManager(MyApplication.context, LinearLayout.VERTICAL, false)
            val adapter = PaymentAdapter(PAYMENT_INFO);
            payment_rv.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}