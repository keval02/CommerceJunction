package com.commercejunction.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.commercejunction.R
import com.commercejunction.R.*
import com.commercejunction.R.layout.activity_myprofile
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.appbar.*

open class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var profileFragment: MyprofileActivity

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)

        nav_view.bringToFront()
        setSupportActionBar(StdToolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            StdToolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        profileFragment = MyprofileActivity()
        supportFragmentManager.beginTransaction().replace(activity_myprofile, profileFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    @SuppressLint("ResourceType")
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (menu.main_menu) {
            R.id.my_profile -> {
                profileFragment = MyprofileActivity()
                supportFragmentManager.beginTransaction()
                    .replace(activity_myprofile, profileFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
            }
        }
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