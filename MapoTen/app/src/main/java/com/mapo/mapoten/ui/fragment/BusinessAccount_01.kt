package com.mapo.mapoten.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.BusinessProfile
import com.mapo.mapoten.databinding.FragmentBusinessAccount01Binding
import com.mapo.mapoten.service.AccountManageService
import com.mapo.mapoten.ui.activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessAccount_01 : Fragment() {

    lateinit var binding : FragmentBusinessAccount01Binding
    lateinit var mainActivity: MainActivity
    lateinit var service : AccountManageService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            binding = FragmentBusinessAccount01Binding.inflate(inflater, container, false)
            val view = binding.root
            service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)
            getCompanyInfo()


            binding.statusButton.setOnClickListener{
                statusButtonClicked()
            }
            binding.businessProfileButton.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.businessAccount_01_01)
            } //기업프로필작성/수정  이동

            binding.personProfileButton.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.businessAccount_01_02)
            } //회원 정보 수정으로 이동
            binding.passwordChangeButton.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.businessAccount_01_03)
            } //비번 변경 이동
            binding.careerButton.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.businessAccount_01_04)
            } //채용공고 목록 이동

           binding.backButton.setOnClickListener {
                Navigation.findNavController(view).navigateUp()
            } //뒤로가기


            // logout 처리
            binding.logoutBtn.setOnClickListener {

                // login 화면 이동
                Navigation.findNavController(view).navigate(R.id.login_01)
            }



            return view
        }


        //프로필 -이미지, 이름, 메일 가져오기
        private fun getCompanyInfo() {
            Log.d("profile", "여기까지옴----")
            service.getCompanyProfile().enqueue(object : Callback<BusinessProfile>{
                override fun onResponse(
                    call: Call<BusinessProfile>,
                    response: Response<BusinessProfile>
                ) {
                    if (response.isSuccessful){
                        Log.d("profile", "res: "+response.code())
                        val img = response.body()?.data?.CMPNY_IM
                        val cmpnyName = response.body()?.data?.CMPNY_NM
                        val cmpnyEmail = response.body()?.data?.APPLCNT_EMAIL_ADRES
                        Glide.with(this@BusinessAccount_01).load(img).into(binding.myPageImageview)
                        binding.nameMyPage.setText(cmpnyName)
                        binding.emailMyPage.setText(cmpnyEmail)

                        status = response.body()?.data?.PROFILE_STTUS
                        Log.d("profile", "status: "+status)

                        if (response.body()?.data?.PROFILE_STTUS == 1){
                            val profile = response.body()?.data
                            binding.businessProfileButton.setOnClickListener {
                                val bundle = bundleOf("compNm" to profile?.CMPNY_NM, "compNum" to profile?.BIZRNO)
                                Navigation.findNavController(view!!).navigate(R.id.businessProfile_01, bundle)

                                //사업체명, 사업자등록번호 전달하기.
                            } //기업프로필작성/수정  이동

                        }
                    }
                }

                override fun onFailure(call: Call<BusinessProfile>, t: Throwable) {
                    Log.d("profile", "error" + t.message)
                }
            })
        }


        //승인여부 팝업
        private fun statusButtonClicked() {
            // 팝업 띄우기
            AlertDialog.Builder(requireContext())
                .setTitle("승인여부 확인")
                .setMessage("승인 되었습니다.")
                .setPositiveButton("확인", { _, _ -> })
                .create()
                .show()


        }


    }