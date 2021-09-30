package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentLogin01Binding

class Login_01 : Fragment() {
    private var _binding: FragmentLogin01Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentLogin01Binding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        with(binding) {
            btnSignIn.setOnClickListener {
                textLengthChecker()
                if (autoLoginCheckBox.isChecked) {
                    findNavController().navigate(R.id.home_01)
                    //findNavController().navigate(R.id.businessAccount_01)
                } else {
                    findNavController().navigate(R.id.home_01)

                }
            } //로그인 성공시 홈화면으로 이동
            tvFindIdPersonal.setOnClickListener {
                findNavController().navigate(R.id.login_01_01)
            } //개인 아이디찾기 화면으로 이동
            tvFindIdBusiness.setOnClickListener {
                findNavController().navigate(R.id.login_01_02)
            } //기업 아이디찾기 화면으로 이동
            tvFindPwd.setOnClickListener {
                findNavController().navigate(R.id.login_01_03)
            } //비밀번호 찾기 화면으로 이동
            btnSignUpPersonal.setOnClickListener {
                findNavController().navigate(R.id.login_02_01)
            } //개인 회원가입 화면으로 이동
            btnSignUpBusiness.setOnClickListener {
                findNavController().navigate(R.id.login_02_02)
            } //기업 회원가입 화면으로 이동

        }


        return binding.root


    }

    private fun textLengthChecker() {
        with(binding) {
            pwdEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (pwdEditText.length() in 1..7) {
                        pwdEditTextInputLayout.error = "비밀번호를 8글자 이상 입력해주세요"
                    } else
                        pwdEditTextInputLayout.error = null
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (idEditText.text!!.isEmpty()) {
                        idEditTextInputLayout.error = "아이디 항목은 필수 정보입니다."
                    } else if (pwdEditText.text!!.isEmpty()) {
                        pwdEditTextInputLayout.error = "비밀번호 항목은 필수 정보입니다."
                    } else {
                        pwdEditTextInputLayout.error = null
                    }
                }

            })

        }

    }

}