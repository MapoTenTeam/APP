package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.BusinessProfile
import com.mapo.mapoten.databinding.FragmentBusinessAccount0102Binding
import com.mapo.mapoten.service.AccountManageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessAccount_01_02 : Fragment() {

    lateinit var binding : FragmentBusinessAccount0102Binding
    lateinit var service : AccountManageService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBusinessAccount0102Binding.inflate(inflater, container, false)
        val view = binding.root

        init()
        service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.saveButton.setOnClickListener {

        }




        return view
    }

    private fun init(){
        service.getCompanyProfile().enqueue(object : Callback<BusinessProfile>{
            override fun onResponse(
                call: Call<BusinessProfile>,
                response: Response<BusinessProfile>
            ) {
                if(response.isSuccessful){
                    Log.d("profile ", "회원정보수정: "+response.code())
                    val profile = response.body()
                    if (profile !=null) {
                        setUserInfo(profile)
                    }
                }
            }

            override fun onFailure(call: Call<BusinessProfile>, t: Throwable) {
                Log.d("profile", "error" + t.message)
            }
        })

    }

    private fun setUserInfo(profile: BusinessProfile) {
        binding.mypageUserName.setText(profile.data.CMPNY_NM)
        binding.userNameText.setText(profile.data.APPLCNT_NM)
        binding.userIdText.setText(profile.data.ENTRPRS_MBER_ID)
        binding.userEmailText.setText(profile.data.APPLCNT_EMAIL_ADRES)
    }





}