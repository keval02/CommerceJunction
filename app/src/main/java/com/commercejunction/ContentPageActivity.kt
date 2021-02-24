package com.commercejunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.commercejunction.constants.Global.contentPageDescription
import com.commercejunction.constants.Global.contentPageTitle
import kotlinx.android.synthetic.main.activity_content_page.*

class ContentPageActivity : AppCompatActivity() {
    var title : String = ""
    var description : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_page)
        title = intent.getStringExtra(contentPageTitle)
        description = intent.getStringExtra(contentPageDescription)
        contentPageToolbar.title = title
        contentPageToolbar.setTitleTextColor(android.graphics.Color.WHITE)

        contentPageToolbar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
        detailsTextView.text = description
        contentPageToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}