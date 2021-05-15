package com.commercejunction.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.adapter.Viewpager
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.model.PDFModel
import com.commercejunction.model.VideoModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_subject1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class Subject1Activity : AppCompatActivity() {
    var subjectId = 0
    var subjectName = ""
    var adminAPI: AdminAPI? = null
    var totalVideos : Int = 0
    var totalBooks : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject1)
        adminAPI = ServiceGenerator.getAPIClass()
        try {
            subjectId = intent.getIntExtra("chapterId", 0);
            subjectName = intent.getStringExtra("chapterName")
        } catch (e: Exception) {

        }

        cheapterNameTV.text = subjectName
        subject1_back.setOnClickListener {
            onBackPressed()
        }

        try {
            val adapter = Viewpager(this.supportFragmentManager, this, subjectId)

            //Adding adapter to pager
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
            tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(p0: TabLayout.Tab?) {
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {
                }

                override fun onTabSelected(p0: TabLayout.Tab) {
                    viewPager.currentItem = p0.position
                }

            })
        } catch (e: Exception) {
            Log.e("error", e.message)
        }
    }

}

