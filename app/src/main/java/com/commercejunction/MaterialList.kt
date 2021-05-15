package com.commercejunction

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.commercejunction.R
import com.commercejunction.adapter.EBooksListAdapter
import com.commercejunction.adapter.VideosListAdapter
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.model.PDFListData
import com.commercejunction.model.PDFModel
import com.commercejunction.model.VideoListData
import com.commercejunction.model.VideoModel
import kotlinx.android.synthetic.main.youtube_video_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaterialList(subjectId : Int): Fragment() {

    private lateinit var root: View
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    val chapterId = subjectId;
    lateinit var videoListAdapter: EBooksListAdapter
    var standardList : ArrayList<PDFListData> = ArrayList()
    override  fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.ebooks_list, container, false)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(root.context)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)

        getAllBooks(chapterId)

        return root
    }

    private fun getAllBooks(chapterId: Int) {
        Global.showProgressDialog(progressDialog, true, root.context)
        val response: Call<PDFModel>? = adminAPI?.MaterialList(chapterId)
        response?.enqueue(object : Callback<PDFModel> {
            override fun onResponse(call: Call<PDFModel>, response: Response<PDFModel>) {
                Global.showProgressDialog(progressDialog, false, root.context)
                val standardListData = response.body()
                if (standardListData?.ResponseCode == 1) {
                    standardListData.ResponseData.Data.let { standardList.addAll(it) }
                    videoListAdapter =
                        object : EBooksListAdapter(activity!!, standardList) {

                        }

                    val layoutManager = GridLayoutManager(root.context, 1)
                    rv_location.layoutManager = layoutManager
                    rv_location.adapter = videoListAdapter
                    rv_location.visibility = View.VISIBLE

                } else {
                    txt_no_data_found.text = "No Materials found!"
                    txt_no_data_found.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<PDFModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                txt_no_data_found.visibility = View.VISIBLE
                Global.showProgressDialog(progressDialog, false, root.context)
            }
        })

    }
}