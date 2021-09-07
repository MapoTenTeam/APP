package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.mapo.mapoten.R

class Login_01 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login_01, container, false)
        view.findViewById<Button>(R.id.btn_sign_in).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.home_01)
        } //로그인 성공시 홈화면으로 이동
        view.findViewById<Button>(R.id.btn_signUp_personal).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_02_01)
        } //개인 회원가입 화면으로 이동
        view.findViewById<Button>(R.id.btn_signUp_business).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_02_02)
        } //기업 회원가입 화면으로 이동
        view.findViewById<TextView>(R.id.tv_findId_personal).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_01_01)
        } //개인 아이디 찾기 화면으로 이동
        view.findViewById<TextView>(R.id.tv_findId_business).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_01_02)
        } //비밀번호 찾기 화면으로 이동
        return view
    }
}