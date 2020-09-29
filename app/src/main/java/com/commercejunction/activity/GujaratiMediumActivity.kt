package com.commercejunction.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import com.commercejunction.R
import com.commercejunction.adapter.StandardListAdapter
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.constants.SharedPreferenceHelper
import com.commercejunction.model.StandardListData
import com.commercejunction.model.StandardModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_gujarati_medium.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class GujaratiMediumActivity : AppCompatActivity() {
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    var mediumSelection : Int = 0
    lateinit var standardListAdapter : StandardListAdapter
    var standardList : ArrayList<StandardListData> = ArrayList()
    lateinit var preferenceHelper: SharedPreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gujarati_medium)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)
        preferenceHelper = SharedPreferenceHelper(applicationContext)
        try{
            mediumSelection = preferenceHelper.getInt("mediumSelection" , 0)
        }catch (e: Exception){
            Log.e("exception" , e.message)
        }

        std_back.setOnClickListener {
            val intent = Intent(this,BoardActivity::class.java)
            startActivity(intent)
        }
        getAllBoards(mediumSelection)
    }

    private fun getAllBoards(mediumId : Int)  {
        Global.showProgressDialog(progressDialog, true, this)
        val response: Call<StandardModel>? = adminAPI?.StandardList(mediumId)
        response?.enqueue(object : Callback<StandardModel> {
            override fun onResponse(call: Call<StandardModel>, response: Response<StandardModel>) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                val standardListData = response.body()
                if (standardListData?.ResponseCode == 1) {
                    standardListData.ResponseData.Data.let { standardList.addAll(it) }
                    standardListAdapter = object : StandardListAdapter(this@GujaratiMediumActivity , standardList){
                        override fun onStandardSelection(
                            id: Int,
                            standardName: String
                        ) {
                            val  intent = Intent(this@GujaratiMediumActivity,HomeActivity::class.java)
                            preferenceHelper.setInt("standardSelection" , id)
                            preferenceHelper.setString("standardName" , standardName)

                            startActivity(intent)
                            finish()
                        }
                    }

                    val layoutManager = GridLayoutManager(this@GujaratiMediumActivity, 2)
                    rv_location.layoutManager = layoutManager
                    rv_location.adapter = standardListAdapter
                    rv_location.visibility = View.VISIBLE

                } else {
                    txt_no_data_found.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<StandardModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                txt_no_data_found.visibility = View.VISIBLE
                Global.showProgressDialog(progressDialog, false, applicationContext)
            }
        })
    }
}
