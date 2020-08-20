package com.commercejunction.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.commercejunction.R
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        boardGujTV.setOnClickListener{
            val intent = Intent(this,GujaratiMediumActivity::class.java)
            startActivity(intent)
        }
    }
}