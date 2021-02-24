package com.commercejunction.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.commercejunction.R
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.model.BaseModel
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    var mobileNo = ""
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        mobileNo = intent.getStringExtra("mobileNo") ?: ""
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)

        submitButton.setOnClickListener {
            val password = newPassEdt.text.toString().trim()
            val confirmPassword = confirmPassEdt.text.toString().trim()

            if (password.isEmpty() || password.length < 6) {
                Global.displayToastMessage("Please enter valid password!", applicationContext)
            } else if (password != confirmPassword) {
                Global.displayToastMessage(
                    "Password and Confirm password are not similar!",
                    applicationContext
                )
            } else {
                changePassword(password)
            }
        }

    }


    private fun changePassword(password: String) {
        Global.showProgressDialog(progressDialog, true, this)
        val jsonRequest = JsonObject();

        jsonRequest.addProperty("MobileNo", mobileNo)
        jsonRequest.addProperty("Password", password)

        val response: Call<BaseModel>? = adminAPI?.ForgotPassword(jsonRequest)
        response?.enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                val data = response.body()
                if (response.code() == 200) {
                    val intent = Intent(this@ChangePasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                Global.displayToastMessage("Something went wrong!", applicationContext)
            }
        })
    }
}