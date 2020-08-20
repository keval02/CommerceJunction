package com.commercejunction.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.commercejunction.R
import kotlinx.android.synthetic.main.activity_gujarati_medium.*

class GujaratiMediumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gujarati_medium)

        std1.setOnClickListener(){
            val  intent = Intent(this,Std1Activity::class.java)
            startActivity(intent)
            finish()
        }

        }
}
