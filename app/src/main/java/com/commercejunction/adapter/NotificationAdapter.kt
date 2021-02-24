package com.commercejunction.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.commercejunction.R
import com.commercejunction.model.NotificationListData
import kotlinx.android.synthetic.main.layout_notification_list_items.view.*

abstract class NotificationAdapter(
    var context: Activity,
    var notificationList: MutableList<NotificationListData>
) : RecyclerView.Adapter<NotificationAdapter.NotificationListViewHolder>() {

    class NotificationListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListViewHolder {
        val itemView =
            context.layoutInflater.inflate(R.layout.layout_notification_list_items, parent, false)
        return NotificationListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationListViewHolder, position: Int) {

        holder.itemView.notificationTitleTV.text = notificationList[position].NotificationTitle
        holder.itemView.notificationDescriptionTV.text =
            notificationList[position].NotificationString

    }
}