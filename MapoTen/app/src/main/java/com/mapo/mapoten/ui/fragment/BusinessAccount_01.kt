package com.mapo.mapoten.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.DialogTitle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.BusinessProfile
import com.mapo.mapoten.data.BusinessProfileItems
import com.mapo.mapoten.databinding.FragmentBusinessAccount01Binding
import com.mapo.mapoten.service.AccountManageService
import com.mapo.mapoten.ui.activity.MainActivity
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessAccount_01 : Fragment() {

    lateinit var binding: FragmentBusinessAccount01Binding
    lateinit var mainActivity: MainActivity
    lateinit var service: AccountManageService
    private var status: Int? = 0
    private var businessApproval: String? = ""
    private var profile: BusinessProfileItems? = null
    private lateinit var dialog: Dialog

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



        binding.businessProfileButton.setOnClickListener {
            checkStatus(status)
        } //기업프로필작성/수정  이동

        binding.personProfileButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.businessAccount_01_02)
        } //회원 정보 수정으로 이동
        binding.passwordChangeButton.setOnClickListener {
            val bundle = bundleOf("cmpny_nm" to profile?.CMPNY_NM)
            Navigation.findNavController(view).navigate(R.id.businessAccount_01_03, bundle)
        } //비번 변경 이동
        binding.careerButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.businessAccount_01_04)
        } //채용공고 목록 이동

       /* binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        } //뒤로가기*/


        // logout 처리
        binding.logoutBtn.setOnClickListener {

            // login 화면 이동
            Navigation.findNavController(view).navigate(R.id.login_01)
        }



        return view
    }


    //프로필 -이미지, 이름, 메일 가져오기
    private fun getCompanyInfo() {
        Log.d("profile", "프로필 가져오기----")
        service.getCompanyProfile().enqueue(object : Callback<BusinessProfile> {
            override fun onResponse(
                call: Call<BusinessProfile>,
                response: Response<BusinessProfile>
            ) {
                if (response.isSuccessful) {
                    Log.d("profile", "res: " + response.code())
                    profile = response.body()?.data

                    setInit(profile)


                    //profile 등록 여부
                    status = response.body()?.data?.PROFILE_STTUS
                    Log.d("profile", "status: " + status)
                    //checkStatus(status)

                    //가입 승인 (사업자) 여부
                    businessApproval = response.body()?.data?.BSNNM_APRVL_NAME
                    Log.d("profile", "승인여부: $businessApproval")
                    businessApproval?.let { setStatusButton(it) }

                }
            }

            override fun onFailure(call: Call<BusinessProfile>, t: Throwable) {
                Log.d("profile", "error" + t.message)
            }
        })
    }

    private fun setInit(profileItems: BusinessProfileItems?) {
        val img = profileItems?.CMPNY_IM
        val cmpnyName = profileItems?.CMPNY_NM
        val cmpnyEmail = profileItems?.APPLCNT_EMAIL_ADRES
        if (img != "") {
            Glide.with(this@BusinessAccount_01).load(img).into(binding.myPageImageview)
        } else {
            Glide.with(this@BusinessAccount_01).load(R.drawable.ic_img_basic_24)
                .into(binding.myPageImageview)
        }
        binding.nameMyPage.text = cmpnyName
        binding.emailMyPage.text = cmpnyEmail
    }

    private fun checkStatus(status: Int?) {
        when (status) {
            0 -> {
                //프로필 없음
                binding.businessProfileButton.setOnClickListener {
                    val bundle = bundleOf(
                        "cmpny_nm" to profile?.CMPNY_NM,
                        "bizrno" to profile?.BIZRNO)
                    Navigation.findNavController(binding.root).navigate(R.id.businessAccount_01_01,bundle)
                }

            }
            1 -> {
                //프로필 있음
                binding.businessProfileButton.setOnClickListener {
                    val bundle = bundleOf(
                        "cmpny_nm" to profile?.CMPNY_NM,
                        "bizrno" to profile?.BIZRNO,
                        "ceo" to profile?.CEO,
                        "address" to profile?.ADRES,
                        "detailad" to profile?.DETAIL_ADRES,
                        "category" to profile?.INDUTY,
                        "empNum" to profile?.NMBR_WRKRS,
                        "webSite" to profile?.WEB_ADRES,
                        "cmpny_email" to profile?.CEO_EMAIL_ADRES,
                        "cmpny_img" to profile?.CMPNY_IM,
                        "approval" to profile?.BSNNM_APRVL_NAME
                    )
                    Log.d("bundle", "이미지주소!! : ${profile?.CMPNY_IM}")
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.businessProfileView, bundle)
                }

            }
            else -> {
                return
            }
        }
    }

    //승인여부 팝업
    private fun statusButtonClicked(title: String, message: String, color: String) {
        // 팝업 띄우기
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_enterprise_state)
        showDialog(title, message, color)
    }

    private fun showDialog(state: String, message: String, color: String) {
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val stateText: TextView = dialog.findViewById(R.id.state)
        stateText.text = state
        stateText.setTextColor((Color.parseColor(color)))
        val messageText: TextView = dialog.findViewById(R.id.message)
        messageText.text = message


        val deleteBtn: AppCompatButton = dialog.findViewById(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            dialog.dismiss()
        }

        val closeBtn: ImageView = dialog.findViewById(R.id.closeBtn)
        closeBtn.setOnClickListener {
            dialog.dismiss()
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun setStatusButton(businessApproval: String) {
        //10	승인 요청 필요
        //20	승인 대기중
        //30	승인 완료
        when (businessApproval) {
            "승인 요청 필요" -> {
                //버튼 배경색, 텍스트 바꾸기
                binding.statusButton.text = "승인요청필요"
                binding.statusButton.setBackgroundResource(R.color.bg_pending_approval)
                binding.statusButton.setTextColor(Color.parseColor("#FFA31A"))
                binding.statusButton.setOnClickListener {
                    statusButtonClicked("승인요청필요", "승인 요청이 필요합니다.", "#FFA31A")
                }
            }

            "승인 대기" -> {
                //버튼 배경색, 텍스트 바꾸기
                binding.statusButton.text = "승인요청필요"
                binding.statusButton.setBackgroundResource(R.color.bg_pending_approval)
                binding.statusButton.setTextColor(Color.parseColor("#FFA31A"))
                binding.statusButton.setOnClickListener {
                    statusButtonClicked("승인 대기", "승인 대기중 입니다.", "#FFA31A")
                }
            }
            "승인 거절" -> {
                binding.statusButton.text = "승인 거절"
                binding.statusButton.setBackgroundResource(R.color.bg_non_approval)
                binding.statusButton.setTextColor(Color.parseColor("#FF1A43"))

                binding.statusButton.setOnClickListener {
                    statusButtonClicked("승인 거절", "승인이 거절되었습니다.", "#FF1A43")
                }
            }
            "승인 완료" -> {
                binding.statusButton.text = "승인 완료"
                binding.statusButton.setBackgroundResource(R.color.bg_approval)
                binding.statusButton.setTextColor(Color.parseColor("#1A75FF"))

                binding.statusButton.setOnClickListener {
                    statusButtonClicked("승인 완료", "승인이 완료되었습니다.", "#1A75FF")
                }
            }
            else -> {
                return
            }
        }
    }


}