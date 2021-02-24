package com.commercejunction

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.commercejunction.adapter.VideosListAdapter
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.model.VideoListData
import com.commercejunction.model.VideoModel
import kotlinx.android.synthetic.main.youtube_video_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideosList(subjectId: Int) : Fragment() {


    private lateinit var root: View
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
    val chapterId = subjectId;
    lateinit var videoListAdapter: VideosListAdapter
    var standardList : ArrayList<VideoListData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.youtube_video_list, container, false)
        adminAPI = ServiceGenerator.getAPIClass()
        progressDialog = Dialog(root.context)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)

        getAllVideos(chapterId)

        return root
    }

    private fun getAllVideos(chapterId: Int) {
        Global.showProgressDialog(progressDialog, true, root.context)
        val response: Call<VideoModel>? = adminAPI?.VideoList(chapterId)
        response?.enqueue(object : Callback<VideoModel> {
            override fun onResponse(call: Call<VideoModel>, response: Response<VideoModel>) {
                Global.showProgressDialog(progressDialog, false, root.context)
                val standardListData = response.body()
                if (standardListData?.ResponseCode == 1) {
                    standardListData.ResponseData.Data.let { standardList.addAll(it) }
                    videoListAdapter =
                        object : VideosListAdapter(activity!!, standardList) {

                        }

                    val layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
                    rv_location.layoutManager = layoutManager
                    rv_location.adapter = videoListAdapter
                    rv_location.visibility = View.VISIBLE

                } else {
                    txt_no_data_found.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<VideoModel>, t: Throwable) {
                Log.e("Failure", "" + t.message)
                txt_no_data_found.visibility = View.VISIBLE
                Global.showProgressDialog(progressDialog, false, root.context)
            }
        })

    }
}