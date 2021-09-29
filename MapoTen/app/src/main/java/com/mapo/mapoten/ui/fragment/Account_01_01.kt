package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.databinding.FragmentAccount0101Binding
import com.mapo.mapoten.service.AccountManageService
import com.mapo.mapoten.ui.data.PersonalProfile
import com.mapo.mapoten.ui.data.PersonalProfileItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Account_01_01 : Fragment() {

    lateinit var binding: FragmentAccount0101Binding
    lateinit var service: AccountManageService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccount0101Binding.inflate(inflater, container, false)

        //회원정보 불러오기
        service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)
       // val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVU0VSX0lEIjoiMTEiLCJpYXQiOjE2MzI4MTkxMzB9.L7r4dwUxbKhSboeo15Pq-LkMZR3sUOTy-QX6Sx1oRW4"
        service.getUserProfile().enqueue(object : Callback<PersonalProfile>{
            override fun onResponse(
                call: Call<PersonalProfile>,
                response: Response<PersonalProfile>
            ) {

                Log.d("profile", "res: "+response.code())

                if(response.isSuccessful){
                    val myProfile = response.body()
                    Log.d("profile", "msg: "+ response.message())
                    Log.d("profile", "res: "+ response.body())
                    binding.userNameText.setText(myProfile?.data?.MBER_NM)
                    binding.userIdText.setText(myProfile?.data?.MBER_ID)
                    binding.userPhoneText.setText(myProfile?.data?.MBTLNUM)
                    binding.userEmailText.setText(myProfile?.data?.MBER_EMAIL_ADRES)


                    val error = response.errorBody()
                    val header = response.headers()
                    Log.d("profile", "header : $header")
                }

            }

            override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
                Log.d("profile", "error" + t.message)
            }
        })

        return inflater.inflate(R.layout.fragment_account_01_01, container, false)
    }
}