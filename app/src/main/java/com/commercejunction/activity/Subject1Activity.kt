package com.commercejunction.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.adapter.Viewpager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_subject1.*

class Subject1Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject1)

        val adapter = Viewpager(this.supportFragmentManager, this)

        //Adding adapter to pager
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                viewPager.currentItem = p0.position
            }

        })
    }
}

