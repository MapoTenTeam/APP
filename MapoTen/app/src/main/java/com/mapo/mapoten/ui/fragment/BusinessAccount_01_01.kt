package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.mapo.mapoten.R


class BusinessAccount_01_01 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_business_account_01_01, container, false)
        // Inflate the layout for this fragment
        view.findViewById<View>(R.id.backButton).setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        } //뒤로가기

        val cmpny_nm = arguments?.getString("cmpny_nm").toString()
        val bizrno = arguments?.getString("bizrno").toString()

        view.findViewById<Button>(R.id.business_profile_button).setOnClickListener {
            val bundle = bundleOf("cmpny_nm" to cmpny_nm, "bizrno" to bizrno)
            Navigation.findNavController(view).navigate(R.id.businessProfile_01, bundle)
        } //기업프로필작성/수정  이동

        return view
    }


}