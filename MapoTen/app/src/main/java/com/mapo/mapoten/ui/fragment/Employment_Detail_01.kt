package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentEmploymentDetail01Binding

class Employment_Detail_01 : Fragment() {
    private lateinit var binding: FragmentEmploymentDetail01Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEmploymentDetail01Binding.inflate(inflater,container,false)
        val view = binding.root

        binding.detailToolbar.apply {
            
        }

        binding.includeDetailTop.apply {
            tvDetailTitle.text = arguments?.getString("title")
            tvDetailContent.text = arguments?.getString("content")
        }

        binding.btFocusUp.setOnClickListener {
            binding.detailScrollView.fullScroll(ScrollView.FOCUS_UP)
        }


        return view
    }
}