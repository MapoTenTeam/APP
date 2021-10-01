package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentAccount0102Binding

class Account_01_02 : Fragment() {

    lateinit var binding : FragmentAccount0102Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAccount0102Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
        return view
    }
}