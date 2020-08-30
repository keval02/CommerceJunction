package com.commercejunction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.commercejunction.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_subject1.*
import java.util.zip.Inflater

class VideosList : Fragment() {

       override  fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // replace if you already have a layout
            return inflater.inflate(R.layout.youtube_video_list, container, false)
        }

}