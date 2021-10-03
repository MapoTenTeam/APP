package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.databinding.FragmentAccount0101Binding
import com.mapo.mapoten.service.AccountManageService
import com.mapo.mapoten.data.PersonalProfile
import com.mapo.mapoten.data.PersonalProfileItems
import com.mapo.mapoten.data.UpdatePersonalProfileItems
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
        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        //회원 정보 업데이트
        binding.saveButton.setOnClickListener {
            Log.d("profile", "눌림")
            updateProfile()
            Toast.makeText(requireContext(), "수정 완료 되었습니다.", Toast.LENGTH_SHORT).show()
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


        private fun updateProfile() {

            val name = binding.userNameText.text.toString()
            val mobile = binding.userPhoneText.text.toString()
            val email = binding.userEmailText.text.toString()
            val address = binding.userAddressText.text.toString()
            val detailAd = binding.addressDetailText.text.toString()


            val profile = UpdatePersonalProfileItems(name,email,mobile,address,detailAd)
            Log.d("profile 수정", "profile : $profile")

            service.updateUserProfile(profile).enqueue(object :Callback<Void>{
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.d("profile 수정", "code : ${response.code()}")

                    if(response.isSuccessful){
                        val msg = response.message()
                        Log.d("profile 수정", "msg : $msg")
                    }

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("profile 수정", "error" + t.message)
                }
            })
        }

}