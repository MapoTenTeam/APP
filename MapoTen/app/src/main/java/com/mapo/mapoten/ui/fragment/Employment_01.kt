package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mapo.mapoten.R
import com.mapo.mapoten.data.EmployData
import com.mapo.mapoten.data.EmployItem
import com.mapo.mapoten.ui.adapter.EmploymentRecyclerViewAdapter
import com.mapo.mapoten.ui.adapter.EmploymentViewPagerAdapter

class Employment_01 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_employment_01, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayoutTextArray = resources.getStringArray(R.array.employ_array_job)

        val tabLayout = view.findViewById<TabLayout>(R.id.emp_tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.employment_viewPager2)

        viewPager.adapter = activity?.let { EmploymentViewPagerAdapter(it) }
        val mEmpAdapter = EmploymentRecyclerViewAdapter(EmployData())

        TabLayoutMediator(tabLayout, viewPager) {
            tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    when(tab.position) {
                        0 -> mEmpAdapter.filter.filter("")
                        1 -> mEmpAdapter.filter.filter("경영지원")
                        2 -> mEmpAdapter.filter.filter("마케팅")
                        3 -> mEmpAdapter.filter.filter("기획")
                        4 -> mEmpAdapter.filter.filter("인사교육")
                        5 -> mEmpAdapter.filter.filter("디자인")
                        6 -> mEmpAdapter.filter.filter("공간운영")
                        7 -> mEmpAdapter.filter.filter("컨텐츠")
                        8 -> mEmpAdapter.filter.filter("개발")
                        9 -> mEmpAdapter.filter.filter("고객서비스")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}