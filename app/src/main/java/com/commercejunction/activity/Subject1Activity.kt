package com.commercejunction.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.commercejunction.R
import kotlinx.android.synthetic.main.activity_subject1.*

class Subject1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject1)

        youtube_video.setOnClickListener {
            setContentView(R.layout.youtube_video_link)
        }

        textbook_pdf.setOnClickListener {
            Toast.makeText(this,"Textbook will be downloaded!", Toast.LENGTH_SHORT).show()
        }
    }
}