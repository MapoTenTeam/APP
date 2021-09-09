package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

        careerSpinner()
        jobSpinner()
        countrySpinner()
        return view
    }

    private fun careerSpinner() {
        val career = resources.getStringArray(R.array.employ_array_career)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, career)
        binding.empSpinnerCareer.adapter = adapter
    }

    private fun jobSpinner() {
        val job = resources.getStringArray(R.array.employ_array_job)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, job)
        binding.empSpinnerJob.adapter = adapter
    }

    private fun countrySpinner() {
        val country = resources.getStringArray(R.array.employ_array_country)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, country)
        binding.empSpinnerCountry.adapter = adapter
    }

    private fun careerSpinnerHandler() {
        binding.empSpinnerCareer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}