package com.mapo.mapoten.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mapo.mapoten.R
import com.mapo.mapoten.ui.fragment.Employment_01_01
import com.mapo.mapoten.ui.fragment.Employment_01_02

/**
 * @author hj
 * @email syk01132@gmail.com
 * @created 2021-09-08
 * @desc
 */
class EmploymentViewPagerAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    private val ITEM_COUNT = 10

    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position) {
            0 -> fragment = Employment_01_01()
            1 -> fragment = Employment_01_01()
            2 -> fragment = Employment_01_01()
            3 -> fragment = Employment_01_01()
            4 -> fragment = Employment_01_01()
            5 -> fragment = Employment_01_01()
            6 -> fragment = Employment_01_01()
            7 -> fragment = Employment_01_01()
            8 -> fragment = Employment_01_01()
            9 -> fragment = Employment_01_01()
        }
        return fragment
    }
}