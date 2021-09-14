package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapo.mapoten.R
import java.io.File


class BusinessProfile_01 : Fragment() {

    val file = File("파일의 경로")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_business_profile_01, container, false)
        // Inflate the layout for this fragment
        return view
    }


}