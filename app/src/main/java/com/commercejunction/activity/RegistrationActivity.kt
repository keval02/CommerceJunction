package com.commercejunction.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.commercejunction.R
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global.displayToastMessage
import com.commercejunction.constants.Global.showProgressDialog
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_registrartion.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    var adminAPI: AdminAPI? = null
    lateinit var progressDialog :Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrartion)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)


        registrationButton.setOnClickListener {

            val userName = userNameTV.text.toString().trim()
            val userEmail = userEmailIdTV.text.toString().trim()
            val mobileNumber = userContactTV.text.toString().trim()
            val password = userSetPasswordTV.text.toString().trim()
            val confirmPassword = userConfirmPasswordTV.text.toString().trim()

            if (userName.isEmpty()) {
                displayToastMessage("Please enter user name!", applicationContext)
            } else if (userEmail.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(userEmail)
                    .matches()
            ) {
                displayToastMessage("Please enter valid email address!", applicationContext)
            } else if (mobileNumber.isEmpty() || mobileNumber.length != 10) {
                displayToastMessage("Please enter valid mobile number!", applicationContext)
            } else if (password.isEmpty() || password.length < 6) {
                displayToastMessage("Please enter valid password!", applicationContext)
            } else if (password != confirmPassword) {
                displayToastMessage(
                    "Password and Confirm password are not similar!",
                    applicationContext
                )
            } else {
                CheckUserDetails(userName, userEmail, mobileNumber, password)
            }
        }

        signinLinkTV.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun CheckUserDetails(
        userName: String,
        userEmail: String,
        mobileNumber: String,
        password: String
    ) {
        showProgressDialog(progressDialog,true,this)
        val jsonRequest = JsonObject();

        jsonRequest.addProperty("EmailId", userEmail)
        jsonRequest.addProperty("Mobile", mobileNumber)
        jsonRequest.addProperty("Name", userName)
        jsonRequest.addProperty("Password", password)

        val response: Call<ResponseBody>? = adminAPI?.RegisterUserMobileNumber(jsonRequest)
        response?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Response",""+response.body())
                showProgressDialog(progressDialog,false,applicationContext)
                if (response.code() == 200) {
                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    displayToastMessage("Something went wrong!", applicationContext)
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Failure",""+t.message)
                showProgressDialog(progressDialog,false,applicationContext)
                displayToastMessage("Something went wrong!", applicationContext)
            }
        })
    }
}

