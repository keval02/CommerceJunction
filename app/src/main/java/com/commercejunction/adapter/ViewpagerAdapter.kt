package com.commercejunction.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.commercejunction.EBooksList
import com.commercejunction.VideosList


class Viewpager(fm: FragmentManager, context: Context, subjectId : Int) :
    FragmentStatePagerAdapter(fm) {
    val PAGE_COUNT = 2
    private val tabTitles = arrayOf("Videos", "E-Books")
    private val context: Context
    var subject = subjectId
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                VideosList(subject)
            }
            1 -> {
                EBooksList(subject)
            }
            else -> {
                VideosList(subject)
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