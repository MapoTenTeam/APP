package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentEmployment01Binding

class Employment_01 : Fragment() {
    private lateinit var binding: FragmentEmployment01Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEmployment01Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.publicButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.employment_01_01)
        } //공공일자리로 이동
        binding.privateButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.employment_01_02)
        } //일반일자리로 이동

        return view
    }
}