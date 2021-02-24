package com.commercejunction.activity

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.R.layout
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.constants.Global.displayToastMessage
import com.commercejunction.constants.SharedPreferenceHelper
import com.commercejunction.model.LoginModel
import com.commercejunction.model.ProfileModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_myprofile.*
import kotlinx.android.synthetic.main.change_password_dialog.*
import kotlinx.android.synthetic.main.change_password_dialog.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyprofileActivity : AppCompatActivity() {
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    lateinit var userData: LoginModel
    lateinit var preferenceHelper: SharedPreferenceHelper
    var isEnable: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_myprofile)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)
        preferenceHelper = SharedPreferenceHelper(applicationContext)
        try {
            userData =
                Gson().fromJson(preferenceHelper.getString("userData", ""), LoginModel::class.java)
            username.setText(userData.ResponseData.Data[0].Name)
            userEmail.setText(userData.ResponseData.Data[0].EmailId)
            userContact.setText(userData.ResponseData.Data[0].Mobile)
        } catch (e: Exception) {

        }
        username.isEnabled = false
        userEmail.isEnabled = false
        userContact.isEnabled = false

        val change: Button = findViewById(R.id.changePassword)

        profile_back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        change.setOnClickListener() {
            val changedialog = AlertDialog.Builder(this)

            val myview: View = layoutInflater.inflate(R.layout.change_password_dialog, null)

            changedialog.setView(myview)
            changedialog.setCancelable(false)

            changedialog.setPositiveButton("Change",
                DialogInterface.OnClickListener { dialog, which ->
                    val password = myview.newPassword.text.toString().trim()
                    val confirmPassword = myview.confirm_new_Password.text.toString().trim()

                    if (password.isEmpty() || password.length < 6) {
                        displayToastMessage("Please enter valid password!", applicationContext)
                    } else if (password != confirmPassword) {
                        displayToastMessage(
                            "Password and Confirm password are not similar!",
                            applicationContext
                        )
                    } else {
                        ChangePassword(password)
                    }
                })

            changedialog.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })

            changedialog.show()
        }
        editProfile.setOnClickListener {
            if (!isEnable) {
                username.isEnabled = true
                userContact.isEnabled = true
                isEnable = true
            } else {
                val userName = username.text.toString().trim()
                val userEmail = userEmail.text.toString().trim()
                val mobileNumber = userContact.text.toString().trim()

                if (userName.isEmpty()) {
                    Global.displayToastMessage("Please enter user name!", applicationContext)
                } else if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail)
                        .matches()
                ) {
                    Global.displayToastMessage(
                        "Please enter valid email address!",
                        applicationContext
                    )
                } else if (mobileNumber.isEmpty() || mobileNumber.length != 10) {
                    Global.displayToastMessage(
                        "Please enter valid mobile number!",
                        applicationContext
                    )
                } else {
                    CheckUserDetails(userName, mobileNumber)
                }
            }

        }

    }

    private fun ChangePassword(
        password: String
    ) {
        Global.showProgressDialog(progressDialog, true, this)
        val jsonRequest = JsonObject();

        jsonRequest.addProperty("Password", password)
        jsonRequest.addProperty("StudentId", userData.ResponseData.Data[0].StudentId)

        val response: Call<ResponseBody>? = adminAPI?.ChangePassword(jsonRequest)
        response?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Global.showProgressDialog(progressDialog, false, applicationContext)

                if (response.code() == 200) {
                    displayToastMessage("Password successfully changed!", applicationContext)
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                displayToastMessage("Something went wrong!", applicationContext)
            }
        })
    }

    private fun CheckUserDetails(
        userName: String,
        mobileNumber: String,
    ) {
        Global.showProgressDialog(progressDialog, true, this)
        val jsonRequest = JsonObject();

        jsonRequest.addProperty("Mobile", mobileNumber)
        jsonRequest.addProperty("Name", userName)
        jsonRequest.addProperty("IsActive", true)
        jsonRequest.addProperty("IsDelete", false)
        jsonRequest.addProperty("StudentId", userData.ResponseData.Data[0].StudentId)

        val response: Call<ProfileModel>? = adminAPI?.EditProfile(jsonRequest)
        response?.enqueue(object : Callback<ProfileModel> {
            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                val data = response.body()
                if (data?.ResponseCode == 1) {
                    userData.ResponseData.Data[0].Mobile = mobileNumber
                    userData.ResponseData.Data[0].Name = userName
                    val updatedUserData = Gson().toJson(userData)
                    preferenceHelper.setString("userData", updatedUserData)
                    val intent = Intent(this@MyprofileActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    displayToastMessage(data?.ResponseMsg, applicationContext)
                }
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                displayToastMessage("Something went wrong!", applicationContext)
            }
        })
    }

}
