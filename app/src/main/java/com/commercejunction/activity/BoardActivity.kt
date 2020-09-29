package com.commercejunction.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.constants.SharedPreferenceHelper
import com.commercejunction.model.BoardModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class BoardActivity : AppCompatActivity() {

    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    var mediumSelection: Int = 0
    lateinit var preferenceHelper: SharedPreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)
        preferenceHelper = SharedPreferenceHelper(applicationContext)
        board_back.setOnClickListener {
            onBackPressed()
        }

        boardGujTV.setOnClickListener {
            gujCV.setCardBackgroundColor(this.resources.getColor(R.color.colorAccent))
            engMed.setCardBackgroundColor(this.resources.getColor(R.color.white))
            mediumSelection = 1
            preferenceHelper.setInt("mediumSelection" , mediumSelection)
        }


        boardEngTV.setOnClickListener {
            gujCV.setCardBackgroundColor(this.resources.getColor(R.color.white))
            engMed.setCardBackgroundColor(this.resources.getColor(R.color.colorAccent))
            mediumSelection = 2
            preferenceHelper.setInt("mediumSelection" , mediumSelection)
        }


        board_Select.setOnClickListener {
            if (mediumSelection == 0) {
                Global.displayToastMessage("Please select medium!", applicationContext)
            } else {
                val intent = Intent(this, GujaratiMediumActivity::class.java)
                intent.putExtra("mediumSelection", mediumSelection)
                startActivity(intent)
            }
        }
    }

    private fun getAllBoards() {
        Global.showProgressDialog(progressDialog, true, this)
        val response: Call<BoardModel>? = adminAPI?.MediumList()
        response?.enqueue(object : Callback<BoardModel> {
            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {
                Log.e("Response", "" + Gson().toJson(response.body()))
                Global.showProgressDialog(progressDialog, false, applicationContext)
                if (response.code() == 200) {

                } else {
                    Global.displayToastMessage("Something went wrong!", applicationContext)
                }
            }

            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                Global.showProgressDialog(progressDialog, false, applicationContext)
                Global.displayToastMessage("Something went wrong!", applicationContext)
            }
        })
    }
}