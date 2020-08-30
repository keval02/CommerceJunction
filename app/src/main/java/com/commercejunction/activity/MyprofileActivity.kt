package com.commercejunction.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.commercejunction.R
import com.commercejunction.R.*
import kotlinx.android.synthetic.main.activity_myprofile.*
import kotlinx.android.synthetic.main.change_password_dialog.*
import kotlinx.android.synthetic.main.change_password_dialog.view.*

class MyprofileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_myprofile)

        username.isEnabled = false
        userEmail.isEnabled = false
        userContact.isEnabled = false

        val change:Button = findViewById(R.id.changePassword)

        profile_back.setOnClickListener {
            val  intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

        change.setOnClickListener() {
            val changedialog = AlertDialog.Builder(this)

            val myview:View = layoutInflater.inflate(R.layout.change_password_dialog,null)

            changedialog.setView(myview)

            change_button.setOnClickListener {
                Toast.makeText(this,"Password Changed!",Toast.LENGTH_SHORT).show()
            }
            cancel_button.setOnClickListener {
                finish()
            }
        }
        editProfile.setOnClickListener {
            username.isEnabled = true
            userEmail.isEnabled = true
            userContact.isEnabled = true
        }
    }
}
