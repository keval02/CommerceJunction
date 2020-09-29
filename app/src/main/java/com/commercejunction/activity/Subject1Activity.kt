package com.commercejunction.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.adapter.Viewpager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_subject1.*

@Suppress("DEPRECATION")
class Subject1Activity : AppCompatActivity() {
    var subjectId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject1)
        try{
            subjectId = intent.getIntExtra("subjectId" , 0);
        }catch (e: Exception){

        }

        try{
            val adapter = Viewpager(this.supportFragmentManager, this, subjectId)

            //Adding adapter to pager
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
            tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(p0: TabLayout.Tab?) {
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {
                }

                override fun onTabSelected(p0: TabLayout.Tab) {
                    viewPager.currentItem = p0.position
                }

            })
        }catch (e : Exception){
            Log.e("error" , e.message)
        }
    }

}

