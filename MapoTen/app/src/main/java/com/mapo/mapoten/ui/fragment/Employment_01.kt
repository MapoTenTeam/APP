package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.mapo.mapoten.R

class Employment_01 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employment_01, container, false)
        view.findViewById<Button>(R.id.public_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.employment_01_01)
        } //공공일자리 화면으로 이동
        view.findViewById<Button>(R.id.private_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.employment_01_02)
        } //일반일자리 화면으로 이동
        return view
    }
}