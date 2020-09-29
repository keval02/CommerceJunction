package com.commercejunction.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.commercejunction.R
import com.commercejunction.model.CheapterListData
import kotlinx.android.synthetic.main.layout_chapter_list_items.view.*

abstract class ChapterListAdapter(var context: Activity, var chapterList: MutableList<CheapterListData>): RecyclerView.Adapter<ChapterListAdapter.chapterListViewHolder>()  {

    class chapterListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chapterListViewHolder {
        val itemView = context.layoutInflater.inflate(R.layout.layout_chapter_list_items, parent, false)
        return chapterListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(holder: chapterListViewHolder, position: Int) {

        holder.itemView.ch1.text = chapterList[position].ChapterName

        holder.itemView.ch1.setOnClickListener {
            onchapterSelection(chapterList[position].ChapterId, chapterList[position].ChapterName)

        }

    }

    abstract fun onchapterSelection(id: Int, chapterName: String)

}