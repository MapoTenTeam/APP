package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import com.google.android.material.tabs.TabLayout
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentEmploymentDetail01Binding

class Employment_Detail_01 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_employment__detail_01, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upButton = view.findViewById<Button>(R.id.bt_focus_up)
        val scrollView = view.findViewById<ScrollView>(R.id.detail_scrollView)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout_detail)
        val tabs = tabLayout.getTabAt(0)

        upButton.setOnClickListener {
            scrollView.fullScroll(ScrollView.FOCUS_UP)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {
                    0 -> {
                    }
                    1 -> {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                        tabs!!.select()
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