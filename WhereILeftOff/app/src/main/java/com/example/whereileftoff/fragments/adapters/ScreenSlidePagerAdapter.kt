package com.example.whereileftoff.fragments.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whereileftoff.fragments.MoviesFragment
import com.example.whereileftoff.fragments.SeriesFragment


class ScreenSlidePagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    private val fragments = arrayListOf(SeriesFragment.newInstance(), MoviesFragment.newInstance())
    private val titles = arrayOf("Series", "Movies")


    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}