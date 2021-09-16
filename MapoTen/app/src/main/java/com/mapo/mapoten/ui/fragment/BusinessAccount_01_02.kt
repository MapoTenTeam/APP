package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentBusinessAccount0102Binding

class BusinessAccount_01_02 : Fragment() {

    lateinit var binding : FragmentBusinessAccount0102Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBusinessAccount0102Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }


}