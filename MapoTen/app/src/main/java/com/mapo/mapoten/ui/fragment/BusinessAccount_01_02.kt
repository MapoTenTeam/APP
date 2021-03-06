package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.BusinessProfile
import com.mapo.mapoten.data.BusinessProfileItems
import com.mapo.mapoten.data.ImageResponse
import com.mapo.mapoten.data.UpdateBusinessProfileItems
import com.mapo.mapoten.databinding.FragmentBusinessAccount0102Binding
import com.mapo.mapoten.service.AccountManageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class BusinessAccount_01_02 : Fragment() {

    lateinit var binding : FragmentBusinessAccount0102Binding
    lateinit var service : AccountManageService
    private lateinit var dialog: Dialog
   lateinit var updateProfile : BusinessProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBusinessAccount0102Binding.inflate(inflater, container, false)
        val view = binding.root
        service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)
        init()


        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.saveButton.setOnClickListener {
            updateBizProfile(updateProfile)
        }

        binding.dismiss.setOnClickListener {
            dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.popup_delete_guide)
            showDialog()
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
                    Log.d("profile ", "??????????????????: "+response.code())
                    val profile = response.body()
                    if (profile !=null) {
                        setUserInfo(profile)
                    }
                    updateProfile = response.body()!!
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

    private fun updateBizProfile(updateProfile:BusinessProfile) {
        val userName = binding.userNameText.text.toString()
        val email = updateProfile.data.APPLCNT_EMAIL_ADRES
        val code = updateProfile.data.BSNNM_APRVL_NAME
        val compName = updateProfile.data.CMPNY_NM
        val compNum = updateProfile.data.BIZRNO
        val ceoName = updateProfile.data.CEO
        val address = updateProfile.data.ADRES
        val detailAd = updateProfile.data.DETAIL_ADRES
        val category = updateProfile.data.INDUTY
        val empNum = updateProfile.data.NMBR_WRKRS
        val homepage = updateProfile.data.WEB_ADRES
        val ceoEmail = updateProfile.data.CEO_EMAIL_ADRES

        val newProfile = UpdateBusinessProfileItems(userName,email,code,compName,compNum,
        ceoName,address,detailAd,category,empNum,homepage,ceoEmail)

            service.updateBusinessProfile(newProfile).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "??????", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(binding.root).navigate(R.id.action_businessAccount_01_02_to_businessAccount_01)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("password", "???????????? ?????? ")
                }
            })
        }



    private fun showDialog() {
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val deleteBtn: AppCompatButton = dialog.findViewById(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            loading(true)
            deleteEnterpriseAccount()
            dialog.dismiss()
        }

        val closeBtn: ImageView = dialog.findViewById(R.id.closeBtn)
        closeBtn.setOnClickListener {
            dialog.dismiss()
        }
    }

    // laoding
    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.visibility = View.VISIBLE
            binding.scrollView.visibility = View.INVISIBLE
        } else binding.loading.visibility = View.GONE
    }


    // ?????? ?????? ?????? api ??????
    private fun deleteEnterpriseAccount() {
        service.deleteEnterpriseAccount().enqueue(object : Callback<ImageResponse>{
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                Log.d("UserAccount", "code: " + response.code())

                if (response.isSuccessful) {
                    Log.d("UserAccount", "code: " + response.code())
                    Log.d("UserAccount", "msg: " + response.body()?.message)

                    thread(start = true) {
                        Thread.sleep(2000)

                        requireActivity().runOnUiThread {
                            loading(false)
                            Toast.makeText(
                                requireContext(),
                                "??????????????? ???????????? ???????????????. :)",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            view?.let {
                                findNavController().navigate(R.id.login_01)
                            }

                        }
                    }


                }            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.d("EnterpriseAccount", "error" + t.message)
            }

        })
    }

}