package com.commercejunction.adapter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.commercejunction.R
import com.commercejunction.activity.PdfViewActivity
import com.commercejunction.constants.Global
import com.commercejunction.constants.Global.keyWebView
import com.commercejunction.model.PDFListData
import kotlinx.android.synthetic.main.layout_ebook_list_items.view.*


abstract class EBooksListAdapter(
    var context: FragmentActivity,
    var chapterList: MutableList<PDFListData>
) : RecyclerView.Adapter<EBooksListAdapter.EBooksListViewHolder>() {

    class EBooksListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EBooksListViewHolder {
        val itemView =
            context.layoutInflater.inflate(R.layout.layout_ebook_list_items, parent, false)
        return EBooksListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(holder: EBooksListViewHolder, position: Int) {

        holder.itemView.textViewCountry.text = chapterList[position].MaterialName

        val url = chapterList[position].MaterialPath
        holder.itemView.viewEbook.setOnClickListener {
            /*val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(keyWebView, url)
            intent.putExtra(Global.keyWebViewTitle, chapterList[position].MaterialName)
            context.startActivity(intent)*/
            val intent = Intent(context, PdfViewActivity::class.java)
            intent.putExtra(keyWebView, url)
            intent.putExtra(Global.keyWebViewTitle, chapterList[position].MaterialName)
            context.startActivity(intent)

            //openPDF(url)
        }

    }

    open fun openPDF(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        try {
            context.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
        }
    }
}