package com.commercejunction.activity

import android.app.Dialog
import android.content.Context
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity;
import com.commercejunction.R
import com.commercejunction.constants.Global
import com.github.barteksc.pdfviewer.PDFView


import kotlinx.android.synthetic.main.activity_pdf_view.*

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


class PdfViewActivity : AppCompatActivity() {
    var webViewURL = ""
    var title = ""
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        );
        setContentView(R.layout.activity_pdf_view)
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)
        try {
            webViewURL = intent.getStringExtra(Global.keyWebView)
            title = intent.getStringExtra(Global.keyWebViewTitle)
            boardTV2.text = title
            Log.e("url", webViewURL)
            webViewURL = webViewURL.replace(" ", "%20")
            Log.e("url", webViewURL)
           // pdfView.fromUri(Uri.parse(webViewURL))
            RetrivePDFfromUrl(pdfView, progressDialog, applicationContext).execute(webViewURL)
        } catch (e: Exception) {

        }

        board_back.setOnClickListener {
                onBackPressed()
            }
    }

    internal class RetrivePDFfromUrl(
        pdfView: PDFView,
        progressDialog: Dialog,
        applicationContext: Context
    ) : AsyncTask<String?, Void?, InputStream?>() {
        lateinit var pdf : PDFView
        lateinit var progressDialog : Dialog
        lateinit var context: Context
        init {
            this.pdf = pdfView
            this.progressDialog = progressDialog
            this.context = applicationContext
        }

        override fun onPreExecute() {
            Global.showProgressDialog(progressDialog, true, context)
            super.onPreExecute()
        }

        override fun doInBackground(vararg strings: String?): InputStream? {
            // we are using inputstream
            // for getting out PDF.
            var inputStream: InputStream? = null
            try {
                val url = URL(strings[0])
                // below is the step where we are
                // creating our connection.
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.getResponseCode() === 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = BufferedInputStream(urlConnection.getInputStream())
                }
            } catch (e: IOException) {
                // this is the method
                // to handle errors.
                e.printStackTrace()
                return null
            }
            return inputStream
        }

        override fun onPostExecute(inputStream: InputStream?) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdf.fromStream(inputStream).load()
            val handler = Handler()
            handler.postDelayed(Runnable {
                Global.showProgressDialog(progressDialog, false, context)
            }, 10000)

        }
    }
}

