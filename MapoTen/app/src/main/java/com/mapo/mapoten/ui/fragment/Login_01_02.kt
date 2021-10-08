package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.Login.GetUserByIdFindOutputDto
import com.mapo.mapoten.data.Login.UserByIdFindInputDto
import com.mapo.mapoten.databinding.FragmentLogin0101Binding
import com.mapo.mapoten.databinding.FragmentLogin0102Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login_01_02 : Fragment() {
    private var _binding: FragmentLogin0102Binding? = null
    private val binding get() = _binding!!

    lateinit var userService: UserService
    private lateinit var dialog: Dialog

    var code: String = ""
    var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogin0102Binding.inflate(inflater,container,false)

        userService = RetrofitBuilder.getInstance().create(UserService::class.java)

        with(binding) {
            btnConfirm.setOnClickListener {
                if (!companyNameRequiredFieldChecker())
                    return@setOnClickListener
                if (!companyNumberRequiredFieldChecker())
                    return@setOnClickListener
                //getUserByFindId()

            }
        }

        return binding.root
    }

    // 아이디 찾기
//    private fun getUserByFindId() {
//        with(binding) {
//
//            var textName = companyNameTiL.editText!!.text.toString()
//            var textNumber = companyNumberTiL.editText!!.text.toString()
//
//            val loginService =
//                userService.getUserByFindId(UserByIdFindInputDto(textName, textEmail))
//
//            Log.d("TAG", "이름 : $textName , 아이디 : $textEmail")
//
//            loginService.enqueue(object : Callback<GetUserByIdFindOutputDto> {
//                override fun onResponse(
//                    call: Call<GetUserByIdFindOutputDto>,
//                    response: Response<GetUserByIdFindOutputDto>
//                ) { //정상응답이 올경우
//                    if (response.isSuccessful) {
//                        code = response.body()?.statusCode.toString()
//                        userId = response.body()?.userId.toString()
//                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
//                        Log.d("TAG", "아이디 : ${response.body()?.userId}")
//                        Log.d("TAG", "code : $code userId : $userId")
//                        //showDialog(code, userId)
//                    } else {
//                        code = response.body()?.statusCode.toString()
//                        userId = response.body()?.userId.toString()
//                        Log.d(
//                            "TAG",
//                            "${response.body()?.statusCode} ~~~ ${response.body()?.message}"
//                        )
//                    }
//                }
//
//                override fun onFailure(call: Call<GetUserByIdFindOutputDto>, t: Throwable) {
//                    //실패할 경우
//                    Log.e("error", "통신 실패" + t.localizedMessage)
//                }
//
//            })
//
//        }
//    }

    // <-------------------------- 필수 입력 체크 -------------------------->
    private fun companyNameRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = companyNameTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                companyNameTiL.error = "사업체명을 입력하세요."
                false
            } else {
                companyNameTiL.error = null
                true
            }
        }
    }

    private fun companyNumberRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = companyNumberTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                companyNumberTiL.error = "사업자등록번호를 입력하세요."
                false
            } else {
                companyNumberTiL.error = null
                true
            }
        }
    }

}