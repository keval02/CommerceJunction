package com.commercejunction.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import com.commercejunction.R
import com.commercejunction.adapter.ChapterListAdapter
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.constants.SharedPreferenceHelper
import com.commercejunction.model.CheapterListData
import com.commercejunction.model.CheapterModel
import kotlinx.android.synthetic.main.activity_gujarati_medium.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ChapterListActivity : AppCompatActivity() {
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    var chapterSelection: Int = 0
    lateinit var ChapterListAdapter: ChapterListAdapter
    var ChapterList: ArrayList<CheapterListData> = ArrayList()
    lateinit var preferenceHelper: SharedPreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_list)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)
        preferenceHelper = SharedPreferenceHelper(applicationContext)
        try {
            chapterSelection = preferenceHelper.getInt("subjectId", 0)
        } catch (e: Exception) {
            Log.e("exception", e.message)
        }

        std_back.setOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            startActivity(intent)
        }
        getAllChapters(chapterSelection)
    }

    private fun getAllChapters(mediumId: Int) {
        Global.showProgressDialog(progressDialog, true, this)
        val response: Call<CheapterModel>? = adminAPI?.ChaptersList(mediumId)
        response?.enqueue(object : Callback<CheapterModel> {
            override fun onResponse(call: Call<CheapterModel>, response: Response<CheapterModel>) {
                Global.showProgressDialog(progressDialog, false, applicationContext)
                val ChapterListData = response.body()
                if (ChapterListData?.ResponseCode == 1) {
                    ChapterListData.ResponseData.Data.let { ChapterList.addAll(it) }
                    ChapterListAdapter =
                        object : ChapterListAdapter(this@ChapterListActivity, ChapterList) {
                            override fun onchapterSelection(id: Int, chapterName: String) {

                            }
                        }

                    val layoutManager = GridLayoutManager(this@ChapterListActivity, 2)
                    rv_location.layoutManager = layoutManager
                    rv_location.adapter = ChapterListAdapter
                    rv_location.visibility = View.VISIBLE

                } else {
                    txt_no_data_found.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<CheapterModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                txt_no_data_found.visibility = View.VISIBLE
                Global.showProgressDialog(progressDialog, false, applicationContext)
            }
        })
    }

}

