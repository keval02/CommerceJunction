package com.commercejunction.constants

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.commercejunction.R

object Global {
    // preference Strings

    var isLoggedIn = "isLoggedIn"
    var userData = "userData"
    var isEnteredFirstTime = "isEnteredFirstTime"
    var keyWebView = "webViewURL"
    var keyWebViewTitle = "webViewTitle"
    var videoId = "videoID"
    var contentPageTitle = "title"
    var contentPageDescription = "description"


    fun displayToastMessage(message: String?, context: Context?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    fun showProgressDialog(progressDialog: Dialog,isShow:Boolean, context: Context){

        val progressTv = progressDialog.findViewById(R.id.progress_tv) as TextView
        progressTv.text = context.resources?.getString(R.string.loading)
        progressTv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        progressTv.textSize = 19F

        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)
        if (isShow){
            progressDialog.show()
        }
        else{
            progressDialog.dismiss()
        }
    }
}