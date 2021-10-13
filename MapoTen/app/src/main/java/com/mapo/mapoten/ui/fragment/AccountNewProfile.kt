package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.PersonalProfile
import com.mapo.mapoten.data.UpdatePersonalProfileItems
import com.mapo.mapoten.databinding.FragmentAccountNewProfileBinding
import com.mapo.mapoten.service.AccountManageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountNewProfile : Fragment() {
    lateinit var binding : FragmentAccountNewProfileBinding
    lateinit var service : AccountManageService
    private var myProfile :PersonalProfile? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountNewProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)
        getUserInfo()

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.saveButton.setOnClickListener {
            val profile :UpdatePersonalProfileItems
            with(binding) {
                val name = userNameText.text.toString()
                val mobile = userPhoneText.text.toString()
                val email = userEmailText.text.toString()
                val address = userAddressText.text.toString()
                val detailad = detailAddressText.text.toString()
                profile = UpdatePersonalProfileItems(name,email,mobile,address,detailad)
            }
            updateUserInfo(profile)
       }


        return view
    }



    private fun getUserInfo() {

        service.getUserProfile().enqueue(object : Callback<PersonalProfile> {
            override fun onResponse(
                call: Call<PersonalProfile>,
                response: Response<PersonalProfile>
            ) {
                if(response.isSuccessful){
                    Log.d("user", "마이페이지 : ${response.body()}")
                    myProfile = response.body()
                    val userName = myProfile?.data?.MBER_NM.toString()
                    binding.mypageUserName.setText(userName)
                    binding.userNameText.setText(userName)
                    binding.userIdText.setText(myProfile?.data?.MBER_ID)
                    binding.userEmailText.setText(myProfile?.data?.MBER_EMAIL_ADRES)

                }
            }

            override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
                Log.d("mypage", "error ${t.message}")
            }
        })

    }

    private fun updateUserInfo(profile : UpdatePersonalProfileItems) {

        service.updateUserProfile(profile).enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if(response.isSuccessful){
                    Log.d("user", "마이페이지 : ${response.body()}")
                   Toast.makeText(requireContext(), "회원프로필 등록 성공!", Toast.LENGTH_SHORT).show()


                    Navigation.findNavController(binding.root).navigate(R.id.account_01)

                }
            }


            override fun onFailure(call: Call<Void>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }


}