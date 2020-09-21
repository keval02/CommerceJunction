package com.commercejunction.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.appcompat.widget.Toolbar
import com.commercejunction.R
import com.commercejunction.apis.AdminAPI
import com.commercejunction.apis.ServiceGenerator
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_subject1.*

class BoardActivity : AppCompatActivity() {

    var adminAPI: AdminAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        adminAPI = ServiceGenerator.getAPIClass()



        board_back.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        board_Select.setOnClickListener{
            val intent = Intent(this,GujaratiMediumActivity::class.java)
            startActivity(intent)
        }
    }
}