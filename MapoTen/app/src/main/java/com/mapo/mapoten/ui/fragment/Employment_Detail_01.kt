package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayout
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentEmployment0101Binding
import com.mapo.mapoten.databinding.FragmentEmploymentDetail01Binding

class Employment_Detail_01 : Fragment() {
    lateinit var binding: FragmentEmploymentDetail01Binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmploymentDetail01Binding.inflate(inflater, container, false)
        val view = binding.root
        binding.title.text = arguments?.getString("title")
        binding.date.text = arguments?.getString("date")

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }
}