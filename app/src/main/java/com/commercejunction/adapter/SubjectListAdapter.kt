package com.commercejunction.adapter

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commercejunction.R
import com.commercejunction.model.SubjectListData
import kotlinx.android.synthetic.main.layout_subject_list_items.view.*

abstract class SubjectListAdapter(var context: Activity, var standardList: MutableList<SubjectListData>): RecyclerView.Adapter<SubjectListAdapter.SubjectListViewHolder>()  {

    class SubjectListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectListViewHolder {
        val itemView = context.layoutInflater.inflate(R.layout.layout_subject_list_items, parent, false)
        return SubjectListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return standardList.size
    }

    override fun onBindViewHolder(holder: SubjectListViewHolder, position: Int) {

        holder.itemView.subject1.text = standardList[position].SubjectName

        if(standardList[position].Color != null)
                holder.itemView.mainLayout.setBackgroundColor(Color.parseColor(standardList[position].Color))
        else
            holder.itemView.mainLayout.setBackgroundColor(context.resources.getColor(R.color.colorAccent))

        Glide.with(context)
            .load(standardList[position].Icon)
            .error(R.drawable.ic_calculator)
            .placeholder(R.drawable.ic_calculator)
            .into(holder.itemView.iv)

        holder.itemView.subject1.setOnClickListener {
            onStandardSelection(standardList[position].SubjectId)
        }



    }

    abstract fun onStandardSelection(id: Int)

}