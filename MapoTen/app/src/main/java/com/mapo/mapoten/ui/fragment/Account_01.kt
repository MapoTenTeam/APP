package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.mapo.mapoten.R

class Account_01 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account_01, container, false)
        view.findViewById<View>(R.id.info_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.account_01_01)
        } //회원정보 화면으로 이동
        view.findViewById<View>(R.id.resume_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.account_01_02)
        } //지원서 화면으로 이동
        view.findViewById<View>(R.id.scrapList_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.account_01_03)
        } //구직신청서 화면으로 이동
        return view
    }
}