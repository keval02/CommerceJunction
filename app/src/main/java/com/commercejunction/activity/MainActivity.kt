package com.commercejunction.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.commercejunction.R
import com.commercejunction.constants.SharedPreferenceHelper


class MainActivity : AppCompatActivity() {
    lateinit var preferenceHelper: SharedPreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceHelper = SharedPreferenceHelper(applicationContext)
        Handler().postDelayed({
            if(preferenceHelper.getBoolean("isLoggedIn" , false)){
                if(preferenceHelper.getInt("mediumSelection", 0) == 0){
                    val mediumSelection = preferenceHelper.getInt("mediumSelection", 0)
                    val intent = Intent(this, BoardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra("mediumSelection", mediumSelection)
                    startActivity(intent)
                    finish()
                }else if(preferenceHelper.getInt("standardSelection", 0) == 0){
                    val boardId : Int = preferenceHelper.getInt("mediumSelection", 0)
                    val intent = Intent(this, GujaratiMediumActivity::class.java)
                    intent.putExtra("mediumSelection", boardId)
                    startActivity(intent)
                }else {
                    val  intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }else {
                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }

}


