package com.commercejunction

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.commercejunction.R
import com.commercejunction.activity.Subject1Activity
import com.commercejunction.adapter.SubjectListAdapter
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import com.commercejunction.constants.Global
import com.commercejunction.model.SubjectModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_std1.*
import kotlinx.android.synthetic.main.activity_subject1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.zip.Inflater

class VideosList(subjectId : Int) : Fragment() {


    private lateinit var root: View
    var adminAPI: AdminAPI? = null
    lateinit var progressDialog: Dialog
       override  fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            root =  inflater.inflate(R.layout.youtube_video_list, container, false)
           adminAPI = ServiceGenerator.getAPIClass()
           progressDialog = Dialog(root.context)
           progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
           progressDialog.setContentView(R.layout.custom_dialog_progress)
           return root
        }
}