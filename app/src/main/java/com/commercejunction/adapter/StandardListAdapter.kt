package com.commercejunction.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.commercejunction.R
import com.commercejunction.model.StandardListData
import kotlinx.android.synthetic.main.layout_standard_list_items.view.*

abstract class StandardListAdapter(var context: Activity, var standardList: MutableList<StandardListData>): RecyclerView.Adapter<StandardListAdapter.StandardListViewHolder>()  {

    class StandardListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandardListViewHolder {
        val itemView = context.layoutInflater.inflate(R.layout.layout_standard_list_items, parent, false)
        return StandardListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return standardList.size
    }

    override fun onBindViewHolder(holder: StandardListViewHolder, position: Int) {

        holder.itemView.std1.text = standardList[position].StandardName

        if(standardList[position].isSelected)
            holder.itemView.standardCV.setCardBackgroundColor(context.resources.getColor(R.color.colorAccent))
        else
            holder.itemView.standardCV.setCardBackgroundColor(context.resources.getColor(R.color.white))


        holder.itemView.std1.setOnClickListener {
            if(standardList[position].isSelected){
                holder.itemView.standardCV.setCardBackgroundColor(context.resources.getColor(R.color.white))
            }else {
                holder.itemView.standardCV.setCardBackgroundColor(context.resources.getColor(R.color.colorAccent))
            }

            standardList.forEach {
                it.isSelected = it.StandardId == standardList[position].StandardId
            }

            notifyDataSetChanged()
            onStandardSelection(standardList[position].StandardId, standardList[position].StandardName)

        }

    }

    abstract fun onStandardSelection(id: Int, standardName: String)

}