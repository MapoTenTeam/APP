package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.Login.GetUserByIdFindOutputDto
import com.mapo.mapoten.data.Login.GetUserByPasswordFindOutputDto
import com.mapo.mapoten.data.Login.UserByIdFindInputDto
import com.mapo.mapoten.data.Login.UserByPasswordFindInputDto
import com.mapo.mapoten.databinding.FragmentLogin0102Binding
import com.mapo.mapoten.databinding.FragmentLogin0103Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Login_01_03 : Fragment() {
    private var _binding: FragmentLogin0103Binding? = null
    private val binding get() = _binding!!

    lateinit var userService: UserService
    private lateinit var dialog: Dialog
    var code: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogin0103Binding.inflate(inflater,container,false)

        userService = RetrofitBuilder.getInstance().create(UserService::class.java)

        with(binding){

            btnSendPwd.setOnClickListener{
                if (!idRequiredFieldChecker())
                    return@setOnClickListener
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                if (!emailPatternChecker())
                    return@setOnClickListener
                getUserByFindPwd()
            }
        }

        return binding.root

    }

    // 비밀번호 찾기
    private fun getUserByFindPwd() {
        with(binding) {

            var textId = idEditText.text.toString()
            var textEmail = emailEditText.text.toString()

            val findPwdService =
                userService.getUserByFindPwd(UserByPasswordFindInputDto(textId, textEmail))

            Log.d("TAG", "아이디 : $textId , 이메일 : $textEmail")

            findPwdService.enqueue(object : Callback<GetUserByPasswordFindOutputDto> {
                override fun onResponse(
                    call: Call<GetUserByPasswordFindOutputDto>,
                    response: Response<GetUserByPasswordFindOutputDto>
                ) { //정상응답이 올경우
                    if (response.isSuccessful) {
                        code = response.body()?.statusCode.toString()
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                        showDialog(code)
                    } else {
                        Log.d(
                            "TAG",
                            "${response.code()}"
                        )
                        Log.d(
                            "TAG",
                            "${response.body()?.statusCode} ~~~ ${response.body()?.message}"
                        )
                    }
                }

                override fun onFailure(call: Call<GetUserByPasswordFindOutputDto>, t: Throwable) {
                    //실패할 경우
                    Log.e("error", "통신 실패" + t.localizedMessage)
                }

            })

        }
    }


    // <-------------------------- 필수 입력 체크 -------------------------->
    private fun idRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = idEditText.text.toString()
            return if (value.isEmpty()) {
                idTiL.error = "아이디를 입력하세요."
                false
            } else {
                idTiL.error = null
                true
            }
        }
    }

    private fun emailRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = emailTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                emailTiL.error = "이메일을 입력하세요."
                false
            } else {
                emailTiL.error = null
                true
            }
        }
    }

    // 이메일 형식 검사
    private fun emailPatternChecker(): Boolean{
        with(binding) {
            // 검사 정규식
            val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            val value: String = emailTiL.editText?.text.toString().trim() // 공백제거
            val p = Pattern.matches(emailValidation, value) // 패턴검사
            return if (p) { // 정상일 경우
                emailTiL.error = null
                true
            } else {
                emailTiL.error = "이메일 형식에 맞게 입력하세요."
                false
            }
        }
    }

    // dialog
    private fun showDialog(code: String) {
        dialog = Dialog(binding.root.context)
        with(dialog) {
            requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
            setContentView(com.mapo.mapoten.R.layout.popup_find_pw_dialog)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            window!!.setLayout(
                android.view.WindowManager.LayoutParams.WRAP_CONTENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT
            )

            val ivError: ImageView = findViewById(com.mapo.mapoten.R.id.iv_error)
            val tvInform: TextView = findViewById(R.id.tv_information)

            android.util.Log.d("TAG", "code : $code")
            when (code) {
                "400" -> {
                    ivError.setImageResource(R.drawable.ic_error_dialog)
                    tvInform.text = "가입된 회원정보가 없습니다. \n 회원가입 후 이용해주세요."
                }
                "null" -> {
                    ivError.setImageResource(R.drawable.ic_error_dialog)
                    tvInform.text = "가입된 회원정보가 없습니다. \n 회원가입 후 이용해주세요."
                }
            }
            show()

            val btnLogin: AppCompatButton = dialog.findViewById(com.mapo.mapoten.R.id.btn_login)
            btnLogin.setOnClickListener {
                //로그인 화면 띄우기
                dismiss()
                findNavController().navigate(com.mapo.mapoten.R.id.action_login_01_03_to_login_01)
            }
            val btnCancel: TextView = dialog.findViewById(com.mapo.mapoten.R.id.tv_cancel)
            btnCancel.setOnClickListener {
                dismiss()
            }
        }

    }
}