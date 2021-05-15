package com.commercejunction.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.commercejunction.ContentPageActivity
import com.commercejunction.R
import com.commercejunction.R.layout
import com.commercejunction.adapter.SubjectListAdapter
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.constants.Global.contentPageDescription
import com.commercejunction.constants.Global.contentPageTitle
import com.commercejunction.constants.SharedPreferenceHelper
import com.commercejunction.model.ContentPageModel
import com.commercejunction.model.LoginModel
import com.commercejunction.model.SubjectListData
import com.commercejunction.model.SubjectModel
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    var standardId: Int = 0
    var standardName: String? = ""
    lateinit var standardListAdapter: SubjectListAdapter
    var standardList: ArrayList<SubjectListData> = ArrayList()
    lateinit var userData: LoginModel
    lateinit var preferenceHelper: SharedPreferenceHelper
    private var aboutUsDetails: String = ""
    private var contactUsDetails: String = ""
    private var privacyPolicyDetails: String = ""
    private var termsConditionsDetails: String = ""

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)
        preferenceHelper = SharedPreferenceHelper(applicationContext)
        try {
            standardId = preferenceHelper.getInt("standardSelection", 0)
            standardName = preferenceHelper.getString("standardName", "")
            userData =
                Gson().fromJson(preferenceHelper.getString("userData", ""), LoginModel::class.java)
            username.text = userData.ResponseData.Data[0].Name
            userstd.text = standardName
        } catch (e: Exception) {

        }
        nav_view.bringToFront()
        setSupportActionBar(StdToolbar)
        StdToolbar.title = "Dashboard"
        shareIV.visibility = View.VISIBLE

        shareIV.setOnClickListener {
            Global.shareAppViaOtherApps(this)
        }

        notificationIV.setOnClickListener {
            val intent = Intent(this, NotificationListActivity::class.java)
            startActivity(intent)
        }
        val mDrawerToggle: ActionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                this, drawer_layout, StdToolbar, R.string.app_name,
                R.string.app_name
            ) {
                override fun onDrawerSlide(
                    drawerView: View,
                    slideOffset: Float
                ) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    val moveFactor: Float = nav_view.width * slideOffset
                    containerView.translationX = slideOffset * drawerView.width
                    drawer_layout.bringChildToFront(drawerView)
                    drawer_layout.requestLayout()
                    //below line used to remove shadow of drawer
                    drawer_layout.setScrimColor(Color.TRANSPARENT)
                }
            }

        drawer_layout.setDrawerListener(mDrawerToggle)
        nav_view.setNavigationItemSelectedListener(this)
        mDrawerToggle.syncState()

        editStd.setOnClickListener {
            val intent = Intent(applicationContext, GujaratiMediumActivity::class.java)
            startActivity(intent)
        }
        getAllSubjects(standardId)
        getAllDetailsForStaticPages()
    }

    @SuppressLint("ResourceType")
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val id: Int = p0.itemId
        if (id == R.id.my_profile) {
            val intent = Intent(applicationContext, MyprofileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        } else if (id == R.id.logout) {

            val logout = AlertDialog.Builder(this)
            logout.setTitle("Logout")
            //set message for alert dialog
            logout.setMessage("Are you sure?")
            //logout.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            logout.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                preferenceHelper.clearAll()
            })
            //performing negative action
            logout.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })

            // Create the AlertDialog
            val alertDialog: AlertDialog = logout.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

        }else if(id == R.id.aboutus){
            val intent = Intent(this, ContentPageActivity::class.java)
            intent.putExtra(contentPageTitle, "About Us")
            intent.putExtra(contentPageDescription, aboutUsDetails)
            startActivity(intent)
        }else if(id == R.id.contactus){
            val intent = Intent(this, ContentPageActivity::class.java)
            intent.putExtra(contentPageTitle, "Contact Us")
            intent.putExtra(contentPageDescription, contactUsDetails)
            startActivity(intent)
        }else if(id == R.id.privacyPolicy){
            val intent = Intent(this, ContentPageActivity::class.java)
            intent.putExtra(contentPageTitle, "Privacy Policy")
            intent.putExtra(contentPageDescription, privacyPolicyDetails)
            startActivity(intent)
        }else if(id == R.id.termsConditions){
            val intent = Intent(this, ContentPageActivity::class.java)
            intent.putExtra(contentPageTitle, "Terms & Conditions")
            intent.putExtra(contentPageDescription, termsConditionsDetails)
            startActivity(intent)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    private fun getAllSubjects(standardId: Int) {
        Global.showProgressDialog(progressDialog, true, this)
        val response: Call<SubjectModel>? = adminAPI?.SubjectList(standardId)
        Log.e("Request", "" + response?.request()?.url())
        response?.enqueue(object : Callback<SubjectModel> {
            override fun onResponse(call: Call<SubjectModel>, response: Response<SubjectModel>) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                val standardListData = response.body()
                if (standardListData?.ResponseCode == 1) {
                    standardListData.ResponseData.Data.let { standardList.addAll(it) }
                    standardListAdapter =
                        object : SubjectListAdapter(this@HomeActivity, standardList) {
                            override fun onStandardSelection(id: Int) {
                                val intent =
                                    Intent(this@HomeActivity, ChapterListActivity::class.java)
                                intent.putExtra("subjectId", id)
                                startActivity(intent)
                            }
                        }

                    val layoutManager = GridLayoutManager(this@HomeActivity, 2)
                    rv_location.layoutManager = layoutManager
                    rv_location.adapter = standardListAdapter
                    rv_location.visibility = View.VISIBLE

                } else {
                    txt_no_data_found.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<SubjectModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                txt_no_data_found.visibility = View.VISIBLE
                Global.showProgressDialog(progressDialog, false, applicationContext)
            }
        })

    }

    private fun getAllDetailsForStaticPages() {
        val response: Call<ContentPageModel>? = adminAPI?.GetContentForStaticPages("1")
        Log.e("Request", "" + response?.request()?.url())
        response?.enqueue(object : Callback<ContentPageModel> {
            override fun onResponse(
                call: Call<ContentPageModel>,
                response: Response<ContentPageModel>
            ) {
                val standardListData = response.body()
                if (standardListData?.ResponseCode == 1) {
                    standardListData.ResponseData.Data.let {
                        it.forEachIndexed { index, contentPageListDataModel ->
                            when (contentPageListDataModel.DynamicPageId) {
                                1 -> {
                                    aboutUsDetails = it[index].Description
                                }
                                2 -> {
                                    termsConditionsDetails = it[index].Description
                                }
                                3 -> {
                                    contactUsDetails = it[index].Description
                                }
                                4 -> {
                                    privacyPolicyDetails = it[index].Description
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ContentPageModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
            }
        })

    }
}