package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.mapo.mapoten.R

class BusinessAccount_01 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_business_account_01, container, false)
        view.findViewById<Button>(R.id.business_profile_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.businessAccount_01_01)
        } //기업프로필작성/수정  이동
        view.findViewById<Button>(R.id.person_profile_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.businessAccount_01_02)
        } //회원 정보 수정으로 이동
        view.findViewById<Button>(R.id.password_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.businessAccount_01_03)
        } //비번 변경 이동
        view.findViewById<Button>(R.id.career_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.businessAccount_01_04)
        } //채용공고 목록 이동

        return view
    }


}