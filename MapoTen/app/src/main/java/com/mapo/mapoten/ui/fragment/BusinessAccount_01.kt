package com.mapo.mapoten.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.ui.activity.MainActivity

class BusinessAccount_01 : Fragment() {

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_business_account_01, container, false)
            view.findViewById<View>(R.id.business_profile_button).setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.businessAccount_01_01)
            } //기업프로필작성/수정  이동
            view.findViewById<View>(R.id.person_profile_button).setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.businessAccount_01_02)
            } //회원 정보 수정으로 이동
            view.findViewById<View>(R.id.password_change_button).setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.businessAccount_01_03)
            } //비번 변경 이동
            view.findViewById<View>(R.id.career_button).setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.businessAccount_01_04)
            } //채용공고 목록 이동

            view.findViewById<View>(R.id.backBtn).setOnClickListener {
                Navigation.findNavController(view).navigateUp()
            } //뒤로가기
            return view
        }





        fun statusButtonClicked() {
            // 팝업 띄우기
            AlertDialog.Builder(mainActivity).setTitle("승인여부 확인")
                .setMessage("승인 되었습니다.")
                .setPositiveButton("확인", { _, _ -> })
                .create()
                .show()


        }


    }