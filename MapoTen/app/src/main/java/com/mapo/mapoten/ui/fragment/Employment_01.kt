package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentBusinessAccount0104Binding
import com.mapo.mapoten.databinding.FragmentEmployment01Binding

class Employment_01 : Fragment() {

    lateinit var binding : FragmentEmployment01Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        binding = FragmentEmployment01Binding.inflate(inflater, container, false)

        val view = binding.root


        // 일반 채용 버튼 눌렀을 때
        binding.GeneralJopBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.employment_01_02)

        }

        // 공공 채용 버튼 눌렀을 때
        binding.publicJopBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.employment_01_01)

        }

        return view
    }
}