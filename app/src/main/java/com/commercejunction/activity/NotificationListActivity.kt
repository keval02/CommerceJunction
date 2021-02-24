package com.commercejunction.activity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.commercejunction.R
import com.commercejunction.adapter.NotificationAdapter
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.constants.SharedPreferenceHelper
import com.commercejunction.model.LoginModel
import com.commercejunction.model.NotificationListData
import com.commercejunction.model.NotificationModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_notification_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationListActivity : AppCompatActivity() {
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    lateinit var ChapterListAdapter: NotificationAdapter
    var ChapterList: ArrayList<NotificationListData> = ArrayList()
    lateinit var preferenceHelper: SharedPreferenceHelper
    lateinit var userData: LoginModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)
        preferenceHelper = SharedPreferenceHelper(applicationContext)
        try {
            userData =
                Gson().fromJson(preferenceHelper.getString("userData", ""), LoginModel::class.java)
        } catch (e: Exception) {

        }
        getAllNotification()
    }

    private fun getAllNotification() {
        Global.showProgressDialog(progressDialog, true, this)
        val response: Call<NotificationModel>? =
            adminAPI?.GetNotificationList(userData.ResponseData.Data[0].StudentId.toString())
        response?.enqueue(object : Callback<NotificationModel> {
            override fun onResponse(
                call: Call<NotificationModel>,
                response: Response<NotificationModel>
            ) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                val ChapterListData = response.body()
                if (ChapterListData?.ResponseCode == 1) {
                    ChapterListData.ResponseData.Data.let { ChapterList.addAll(it) }
                    ChapterListAdapter =
                        object : NotificationAdapter(this@NotificationListActivity, ChapterList) {

                        }

                    val layoutManager = LinearLayoutManager(
                        this@NotificationListActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    rv_notifications.layoutManager = layoutManager
                    rv_notifications.adapter = ChapterListAdapter
                    rv_notifications.visibility = View.VISIBLE

                } else {
                    txt_no_data_found.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<NotificationModel>, t: Throwable) {
                txt_no_data_found.visibility = View.VISIBLE
                Global.showProgressDialog(progressDialog, false, applicationContext)
            }
        })
    }
}