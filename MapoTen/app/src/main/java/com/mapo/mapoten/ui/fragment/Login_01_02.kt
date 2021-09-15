package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentLogin0101Binding
import com.mapo.mapoten.databinding.FragmentLogin0102Binding

class Login_01_02 : Fragment() {
    private var _binding: FragmentLogin0102Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogin0102Binding.inflate(inflater,container,false)
        return binding.root
    }

}