package com.commercejunction.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.commercejunction.R
import com.commercejunction.adapter.ChapterListAdapter
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.constants.SharedPreferenceHelper
import com.commercejunction.model.CheapterListData
import com.commercejunction.model.CheapterModel
import kotlinx.android.synthetic.main.activity_chapter_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            chapterSelection = intent.getIntExtra("subjectId", 0)
        } catch (e: Exception) {
            Log.e("exception", e.message)
        }

        std_back.setOnClickListener {
            onBackPressed()
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
                                val intent =
                                    Intent(this@ChapterListActivity, Subject1Activity::class.java)
                                intent.putExtra("chapterId", id)
                                intent.putExtra("chapterName" , chapterName)
                                startActivity(intent)
                            }
                        }

                    val layoutManager = LinearLayoutManager(
                        this@ChapterListActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
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

