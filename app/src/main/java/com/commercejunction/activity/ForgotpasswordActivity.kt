package com.commercejunction.activity

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
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotpasswordActivity : AppCompatActivity() {
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)

        forgotButton.setOnClickListener {
            val getPhoneNumber = userPhoneIV.text.toString().trim()
            if(getPhoneNumber.length != 10){
                Global.displayToastMessage("Please enter valid mobile number!", applicationContext)
            }else {
                getOTP(getPhoneNumber)
            }
        }

    }


    private fun getOTP(mobileNo: String) {
        Global.showProgressDialog(progressDialog, true, this)
        val response: Call<BaseModel>? = adminAPI?.GetOtp(mobileNo)
        response?.enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                Global.showProgressDialog(progressDialog, false, this@ForgotpasswordActivity)
                if(response.code() == 200){
                    val intent = Intent(this@ForgotpasswordActivity, VerifyOtpActivity::class.java)
                    intent.putExtra("mobileNo" , mobileNo)
                    startActivity(intent)
                }else {
                    Global.displayToastMessage("Something went wrong!", applicationContext)
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                Global.showProgressDialog(progressDialog, false, this@ForgotpasswordActivity)
            }
        })

    }
}