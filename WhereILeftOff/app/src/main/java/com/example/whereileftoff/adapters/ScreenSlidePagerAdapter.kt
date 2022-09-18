package com.example.whereileftoff.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.whereileftoff.fragments.MoviesFragment
import com.example.whereileftoff.fragments.SeriesFragment

class ScreenSlidePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> { SeriesFragment() }
            1 -> { MoviesFragment() }
            else -> { throw Resources.NotFoundException("Position Not Found") }
        }
    }
}