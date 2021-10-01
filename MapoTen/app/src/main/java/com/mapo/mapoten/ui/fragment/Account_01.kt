package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.PersonalProfile
import com.mapo.mapoten.databinding.FragmentAccount01Binding
import com.mapo.mapoten.service.AccountManageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Account_01 : Fragment() {

    lateinit var binding: FragmentAccount01Binding
    lateinit var service : AccountManageService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccount01Binding.inflate(inflater, container, false)

        val view = binding.root

        getUserInfo()

        // Inflate the layout for this fragment

        // back button
        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        view.findViewById<View>(R.id.info_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.account_01_01)
        } //회원정보 화면으로 이동
        view.findViewById<View>(R.id.resume_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.account_01_02)
        } //비번 번경 화면으로 이동
        view.findViewById<View>(R.id.scrapList_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.account_01_03)
        } //스크랩 리스트 화면으로 이동




        return view
    }

    private fun getUserInfo() {
        service =RetrofitBuilder.getInstance().create(AccountManageService::class.java)
        service.getUserProfile().enqueue(object : Callback<PersonalProfile>{
            override fun onResponse(
                call: Call<PersonalProfile>,
                response: Response<PersonalProfile>
            ) {
                if(response.isSuccessful){
                    val myProfile = response.body()
                    binding.nameMyPage.setText(myProfile?.data?.MBER_NM)
                    binding.emailMyPage.setText(myProfile?.data?.MBER_EMAIL_ADRES)
                }
            }

            override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
               Log.d("mypage", "error ${t.message}")
            }
        })

    }
}