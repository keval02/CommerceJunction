package com.commercejunction.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.commercejunction.EBooksList
import com.commercejunction.VideosList


class Viewpager(fm: FragmentManager, context: Context) :
    FragmentStatePagerAdapter(fm) {
    val PAGE_COUNT = 2
    private val tabTitles = arrayOf("Videos", "E-Books")
    private val context: Context
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                VideosList()
            }
            1 -> {
                EBooksList()
            }
            else -> {
                VideosList()
            }
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    init {
        this.context = context
    }
}