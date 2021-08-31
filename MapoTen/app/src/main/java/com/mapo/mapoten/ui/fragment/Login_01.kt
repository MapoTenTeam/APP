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
        view.findViewById<Button>(R.id.person_login_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.home_01)
        } //로그인 성공시 홈화면으로 이동
        view.findViewById<Button>(R.id.business_login_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.businessAccount_01)
        } //기업 로그인 성공시 기업마이페이지으로 이동
        view.findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_02)
        } //회원가입 화면으로 이동
        view.findViewById<Button>(R.id.forgot_id_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_01_01)
        } //아이디 찾기 화면으로 이동
        view.findViewById<Button>(R.id.forgot_pwd_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.login_01_02)
        } //비밀번호 찾기 화면으로 이동
        return view
    }
}