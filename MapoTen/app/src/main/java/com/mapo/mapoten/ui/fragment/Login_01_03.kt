package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentLogin0102Binding
import com.mapo.mapoten.databinding.FragmentLogin0103Binding

class Login_01_03 : Fragment() {
    private var _binding: FragmentLogin0103Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogin0103Binding.inflate(inflater,container,false)
        return binding.root

    }
}