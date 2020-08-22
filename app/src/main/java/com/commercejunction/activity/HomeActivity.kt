package com.commercejunction.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.commercejunction.R
import com.commercejunction.R.layout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.appbar.*

open class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)

        nav_view.bringToFront()
        setSupportActionBar(StdToolbar)
        StdToolbar.title = "Dashboard"
        val mDrawerToggle: ActionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                this, drawer_layout, StdToolbar, R.string.app_name,
                R.string.app_name
            ) {
                override fun onDrawerSlide(
                    drawerView: View,
                    slideOffset: Float
                ) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    val moveFactor: Float = nav_view.width * slideOffset
                    containerView.translationX = slideOffset * drawerView.width
                    drawer_layout.bringChildToFront(drawerView)
                    drawer_layout.requestLayout()
                    //below line used to remove shadow of drawer
                    drawer_layout.setScrimColor(Color.TRANSPARENT)
                }
            }

        drawer_layout.setDrawerListener(mDrawerToggle)
        nav_view.setNavigationItemSelectedListener(this)
        mDrawerToggle.syncState()
    }

    @SuppressLint("ResourceType")
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val id: Int = p0.getItemId()
        if (id == R.id.my_profile) {
            val intent = Intent(applicationContext, MyprofileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        } else if (id == R.id.logout) {

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}