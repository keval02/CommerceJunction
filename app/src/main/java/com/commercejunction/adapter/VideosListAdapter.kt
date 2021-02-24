package com.commercejunction.adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commercejunction.R
import com.commercejunction.activity.VideoDisplayActivity
import com.commercejunction.constants.Global
import com.commercejunction.model.VideoListData
import kotlinx.android.synthetic.main.layout_youtube_list_items.view.*


abstract class VideosListAdapter(
    var context: FragmentActivity,
    var chapterList: MutableList<VideoListData>
) : RecyclerView.Adapter<VideosListAdapter.VideosListViewHolder>() {

    class VideosListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosListViewHolder {
        val itemView =
            context.layoutInflater.inflate(R.layout.layout_youtube_list_items, parent, false)
        return VideosListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(holder: VideosListViewHolder, position: Int) {

        holder.itemView.textViewCountry.text = chapterList[position].VideoName

        val url = chapterList[position].VideoURL
        val videoId =
            url.split("v=".toRegex()).toTypedArray()[1]
        val videoIds = videoId.split("&").toTypedArray()[0]
        val thumbnailHq =
            "http://img.youtube.com/vi/$videoIds/hqdefault.jpg"

        Glide.with(context)
            .load(thumbnailHq)
            .error(R.drawable.video1)
            .placeholder(R.drawable.video1)
            .into(holder.itemView.imageViewFlag)

        holder.itemView.watchVideoLayout.setOnClickListener {
            /*val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(Global.keyWebView, url)
            intent.putExtra(keyWebViewTitle, chapterList[position].VideoName)
            context.startActivity(intent)*/

            val intent = Intent(context, VideoDisplayActivity::class.java)
            intent.putExtra(Global.videoId, videoIds)
            context.startActivity(intent)
        }

    }

    open fun watchYoutubeVideo(context: Context, id: String, url: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }
}