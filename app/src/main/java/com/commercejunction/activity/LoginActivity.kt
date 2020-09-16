package com.commercejunction.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    private val sharedPrefFile: String = "LoginDataNew"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)

        loginButton.setOnClickListener {
            val userEmail = emailTV.text.toString().trim()
            val password = password.text.toString().trim()
            if (userEmail.isEmpty()) {
                Global.displayToastMessage("Please enter Email-Id!", applicationContext)
            } else if (password.isEmpty() || password.length < 6) {
                Global.displayToastMessage("Please enter valid password!", applicationContext)
            } else {
                CheckLoginDetails(userEmail, password)
            }
        }

        registerTV.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        forgotPasswordTV.setOnClickListener {
            val intent1 = Intent(this, ForgotpasswordActivity::class.java)
            startActivity(intent1)
        }
    }

    private fun CheckLoginDetails(userEmail: String, password: String) {
        Global.showProgressDialog(progressDialog, true, this)
        val jsonRequest = JsonObject();
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        jsonRequest.addProperty("EmailId", userEmail)
        jsonRequest.addProperty("Password", password)

        val response: Call<ResponseBody>? = adminAPI?.LoginUserName(jsonRequest)
        response?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Response", "" + response.body())
                Global.showProgressDialog(progressDialog, false, applicationContext)
                if (response.code() == 200) {
                    val gson = Gson()
                    val json = gson.toJson(jsonRequest) // myObject - instance of MyObject
                    editor.putString("MyObject", json)
                    editor.commit()
                    val intent = Intent(this@LoginActivity, BoardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Global.displayToastMessage("Something went wrong!", applicationContext)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                Global.showProgressDialog(progressDialog, false, applicationContext)
                Global.displayToastMessage("Something went wrong!", applicationContext)
            }
        })

    }


}
