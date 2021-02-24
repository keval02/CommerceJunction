package com.commercejunction.activity

import `in`.aabhasjindal.otptextview.OTPListener
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.model.BaseModel
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_verify_otp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyOtpActivity : AppCompatActivity() {
    var mobileNo = ""
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)
        mobileNo = intent.getStringExtra("mobileNo") ?: ""
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)

        resendOtp.setOnClickListener {
            getOTP(mobileNo)
        }

        otp_view.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                verifyOTP(otp)
            }

        }
    }

    private fun verifyOTP(otp: String) {
        Global.showProgressDialog(progressDialog, true, this)
        val jsonRequest = JsonObject();

        jsonRequest.addProperty("MobileNo", mobileNo)
        jsonRequest.addProperty("OTP", otp)

        val response: Call<BaseModel>? = adminAPI?.ValidateOTP(jsonRequest)
        response?.enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                val data = response.body()
                if (response.code() == 200) {
                    val intent = Intent(this@VerifyOtpActivity, ChangePasswordActivity::class.java)
                    intent.putExtra("mobileNo", mobileNo)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                Global.displayToastMessage("Something went wrong!", applicationContext)
            }
        })
    }

    private fun getOTP(mobileNo: String) {
        Global.showProgressDialog(progressDialog, true, this)
        val response: Call<BaseModel>? = adminAPI?.GetOtp(mobileNo)
        response?.enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                Global.showProgressDialog(progressDialog, false, this@VerifyOtpActivity)
                if (response.code() == 200) {
                    Global.displayToastMessage(
                        "OTP sent on your mobile number!",
                        applicationContext
                    )
                } else {
                    Global.displayToastMessage("Something went wrong!", applicationContext)
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                Global.showProgressDialog(progressDialog, false, this@VerifyOtpActivity)
            }
        })

    }
}