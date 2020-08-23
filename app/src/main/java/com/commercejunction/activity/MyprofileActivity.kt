package com.commercejunction.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.commercejunction.R
import com.commercejunction.R.*
import kotlinx.android.synthetic.main.activity_myprofile.*
import kotlinx.android.synthetic.main.change_password_dialog.*
import kotlinx.android.synthetic.main.change_password_dialog.view.*

class MyprofileActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(layout.activity_myprofile)

        username.isEnabled = false
        userEmail.isEnabled = false
        userContact.isEnabled = false

        changePassword.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this).inflate(R.layout.change_password_dialog, null)
            val mBuilder =
                AlertDialog.Builder(this).setView(mDialogView).setTitle("Change Password")
            val mAlertDialog = mBuilder.show()
            mDialogView.change_button.setOnClickListener {
                mAlertDialog.dismiss()
            }

            cancel_button.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
        editProfile.setOnClickListener {
            username.isEnabled = true
            userEmail.isEnabled = true
            userContact.isEnabled = true
        }
    }
}
