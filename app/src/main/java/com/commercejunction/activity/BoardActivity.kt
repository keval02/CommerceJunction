package com.commercejunction.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.commercejunction.R
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_subject1.*

class BoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

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