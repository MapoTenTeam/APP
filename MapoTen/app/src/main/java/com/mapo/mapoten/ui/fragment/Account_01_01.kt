package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.DuplicateIdInfoItem
import com.mapo.mapoten.databinding.FragmentAccount0101Binding
import com.mapo.mapoten.service.AccountManageService
import com.mapo.mapoten.data.PersonalProfile
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
        val view = binding.root

        //뒤로가기
        binding.backBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

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

                    setProfile(myProfile!!)
                    val error = response.errorBody()
                    val header = response.headers()
                    Log.d("profile", "header : $header")
                }

            }

            override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
                Log.d("profile", "error" + t.message)
            }
        })


        //회원 정보 업데이트
        binding.saveButton.setOnClickListener {
            val name = binding.userNameText.text.toString()
            val mobile = binding.userPhoneText.text.toString()
            val email = binding.userEmailText.text.toString()
            val address = binding.userAddressText.text.toString()
            val detailAd = binding.addressDetailText.text.toString()
            service.updateUserProfile(name,mobile,email,address,detailAd).enqueue(object :Callback<DuplicateIdInfoItem>{
                override fun onResponse(
                    call: Call<DuplicateIdInfoItem>,
                    response: Response<DuplicateIdInfoItem>
                ) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<DuplicateIdInfoItem>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }


        return view
    }



       private fun setProfile(myProfile: PersonalProfile) {

            val name = myProfile?.data?.MBER_NM
            binding.mypageUserName.setText(name)
            binding.userNameText.setText(name)
            binding.userIdText.setText(myProfile?.data?.MBER_ID)
            binding.userPhoneText.setText(myProfile?.data?.MBTLNUM)
            binding.userEmailText.setText(myProfile?.data?.MBER_EMAIL_ADRES)
            binding.userAddressText.setText(myProfile?.data?.ADRES)
            binding.addressDetailText.setText(myProfile!!.data.DETAIL_ADRES)

        }

}