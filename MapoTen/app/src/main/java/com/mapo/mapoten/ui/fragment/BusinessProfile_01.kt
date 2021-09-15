package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentBusinessProfile01Binding
import java.io.File


class BusinessProfile_01 : Fragment() {

    lateinit var binding: FragmentBusinessProfile01Binding

    val file = File("파일의 경로")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessProfile01Binding.inflate(inflater, container, false)

        val view = binding.root

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }


}