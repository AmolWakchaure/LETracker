package com.example.letracker.user.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.example.letracker.R
import com.example.letracker.database.TABLE_ATTENDANCE
import com.example.letracker.other.Constants
import com.example.letracker.other.M
import com.example.letracker.user.fragments.AdvanceFragment
import com.example.letracker.user.fragments.PaymentFragment
import java.util.ArrayList

class AdvancePaymentReportActivity : AppCompatActivity() {


    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    val mFragmentList = ArrayList<Fragment>()
    val mFragmentTitleList = ArrayList<String>()

    var LABOR_ID = ""


    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_advance_payment)

        //get labor id from intent
        var bundle = intent.extras
        LABOR_ID = bundle.getString(TABLE_ATTENDANCE.LABOR_ID)

        viewPager = findViewById(R.id.viewpager) as ViewPager
        setupViewPager(viewPager!!)

        tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)







    }

    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = ViewPagerAdapter(supportFragmentManager)

        //store labor id in bundle
        var bundle = Bundle()
        bundle.putString(TABLE_ATTENDANCE.LABOR_ID,LABOR_ID)

        var advanceFragment = AdvanceFragment()
        advanceFragment.setArguments(bundle);

        var paymentFragment = PaymentFragment()
        paymentFragment.setArguments(bundle);

        adapter.addFragment(advanceFragment, "Advance Amt")
        adapter.addFragment(paymentFragment, "Paid Amt")

        viewPager.adapter = adapter

        //Log.e("SIZE_DATA",""+mFragmentList.size());
    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            // Log.e("shdjfdsjkjl",""+mFragmentList.size());
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }


}