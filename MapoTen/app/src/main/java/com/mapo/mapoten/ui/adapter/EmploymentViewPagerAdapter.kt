package com.mapo.mapoten.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mapo.mapoten.ui.fragment.Employment_01_01
import com.mapo.mapoten.ui.fragment.Employment_01_02

/**
 * @author hj
 * @email syk01132@gmail.com
 * @created 2021-09-08
 * @desc
 */
class EmploymentViewPagerAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position) {
            0 -> fragment = Employment_01_01()
            1 -> fragment = Employment_01_02()
        }
        return fragment
    }
}