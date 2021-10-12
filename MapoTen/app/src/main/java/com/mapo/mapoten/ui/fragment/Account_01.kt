package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
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
    private var userName :String = ""
    private var myProfile :PersonalProfile? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccount01Binding.inflate(inflater, container, false)

        val view = binding.root

        getUserInfo()

        // Inflate the layout for this fragment



        view.findViewById<View>(R.id.resume_button).setOnClickListener {
            val bundle = bundleOf("name" to userName)
            Navigation.findNavController(view).navigate(R.id.account_01_02, bundle)
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
                    Log.d("user", "마이페이지 : ${response.body()}")
                    myProfile = response.body()
                    userName = myProfile?.data?.MBER_NM.toString()
                    binding.nameMyPage.setText(userName)
                    binding.emailMyPage.setText(myProfile?.data?.MBER_EMAIL_ADRES)

                    checkStatus(myProfile?.data?.PROFILE_STTUS)
                }
            }

            override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
               Log.d("mypage", "error ${t.message}")
            }
        })

    }


    private fun checkStatus(status: Int?){
        when (status) {
            0 -> {
                //프로필 없음
                binding.infoButton.setOnClickListener {
                    Navigation.findNavController(binding.root).navigate(R.id.accountCheckProfile)
                }

            }
            1 -> {
                //프로필 있음
                binding.infoButton.setOnClickListener {  val bundle = bundleOf(
                    "mber_nm" to myProfile?.data?.MBER_NM,
                    "mber_id" to myProfile?.data?.MBER_ID,
                    "mber_email" to myProfile?.data?.MBER_EMAIL_ADRES,
                    "mobile" to myProfile?.data?.MBTLNUM,
                    "address" to myProfile?.data?.ADRES,
                    "detailad" to myProfile?.data?.DETAIL_ADRES,
                )
                    Navigation.findNavController(binding.root).navigate(R.id.accountProfileView, bundle)
                }


            }
            else -> {
                return
            }
        }
    }
}