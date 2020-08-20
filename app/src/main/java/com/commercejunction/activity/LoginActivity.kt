package com.commercejunction.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.commercejunction.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            val intent2 = Intent(this,BoardActivity::class.java)
            startActivity(intent2)
        }

        registerTV.setOnClickListener {
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }
        forgotPasswordTV.setOnClickListener {
            val intent1 = Intent(this,ForgotpasswordActivity::class.java)
            startActivity(intent1)
        }
    }

    
}
