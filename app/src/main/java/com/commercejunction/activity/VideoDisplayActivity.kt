package com.commercejunction.activity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.constants.Global
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener
import kotlinx.android.synthetic.main.activity_video_display.*


class VideoDisplayActivity : AppCompatActivity() {
    var videoId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_video_display)
        try{
            videoId = intent.getStringExtra(Global.videoId)
            Log.e("videoId", videoId)
        }catch (e: Exception){

        }

        youtube_player_view.initialize(YouTubePlayerInitListener { initializedYouTubePlayer ->
            initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {

                    initializedYouTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }, true)
    }
}