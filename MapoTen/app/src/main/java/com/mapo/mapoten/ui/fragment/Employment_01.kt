package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentEmployment01Binding
import com.mapo.mapoten.ui.adapter.EmploymentViewPagerAdapter

class Employment_01 : Fragment() {
    private lateinit var binding: FragmentEmployment01Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEmployment01Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.employmentViewPager2.apply {
            adapter = activity?.let { EmploymentViewPagerAdapter(it) }
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        return view
    }
}