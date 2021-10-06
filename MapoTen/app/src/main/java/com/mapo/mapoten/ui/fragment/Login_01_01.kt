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
import com.mapo.mapoten.data.Login.UserByIdFindInputDto
import com.mapo.mapoten.databinding.FragmentLogin0101Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Login_01_01 : Fragment() {
    private var _binding: FragmentLogin0101Binding? = null
    private val binding get() = _binding!!
    lateinit var userService: UserService
    private lateinit var dialog: Dialog

    var code: String = ""
    var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogin0101Binding.inflate(inflater, container, false)
        userService = RetrofitBuilder.getInstance().create(UserService::class.java)



        with(binding) {
            btnConfirm.setOnClickListener {
                if (!nameRequiredFieldChecker())
                    return@setOnClickListener
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                if (!emailPatternChecker())
                    return@setOnClickListener
                getUserByFindId()

            }
        }
        return binding.root
    }

    private fun getUserByFindId() {
        with(binding) {

            var textName = nameEditText.text.toString()
            var textEmail = emailEditText.text.toString()

            val loginService =
                userService.getUserByFindId(UserByIdFindInputDto(textName, textEmail))

            Log.d("TAG", "이름 : $textName , 아이디 : $textEmail")

            loginService.enqueue(object : Callback<GetUserByIdFindOutputDto> {
                override fun onResponse(
                    call: Call<GetUserByIdFindOutputDto>,
                    response: Response<GetUserByIdFindOutputDto>
                ) { //정상응답이 올경우
                    if (response.isSuccessful) {
                        code = response.body()?.statusCode.toString()
                        userId = response.body()?.userId.toString()
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                        Log.d("TAG", "아이디 : ${response.body()?.userId}")
                        Log.d("TAG", "code : $code userId : $userId")
                        showDialog(code, userId)
                    } else {
                        code = response.body()?.statusCode.toString()
                        userId = response.body()?.userId.toString()
                        Log.d(
                            "TAG",
                            "${response.body()?.statusCode} ~~~ ${response.body()?.message}"
                        )
                    }
                }

                override fun onFailure(call: Call<GetUserByIdFindOutputDto>, t: Throwable) {
                    //실패할 경우
                    Log.e("error", "통신 실패" + t.localizedMessage)
                }

            })

        }
    }

    private fun nameRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = nameTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                nameTiL.error = "이름를 입력하세요."
                false
            } else {
                nameTiL.error = null
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
    private fun showDialog(code: String, userId: String) {
        dialog = Dialog(binding.root.context)
        with(dialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.popup_find_id_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            val tvIdTitle: TextView = findViewById(R.id.tv_id_title)
            val tvId: TextView = findViewById(R.id.tv_id)
            val tvInform: TextView = findViewById(R.id.tv_information)
            val ivError: ImageView = findViewById(R.id.iv_error)
            Log.d("TAG", "code : $code")
            when (code) {
                "200" -> {
                    tvId.text = userId
                    ivError.visibility = View.GONE
                }
                "400" -> {
                    tvIdTitle.visibility = View.GONE
                    tvId.visibility = View.GONE
                    tvInform.text = "가입된 회원정보가 없습니다."
                }
                "null" -> {
                    tvIdTitle.visibility = View.GONE
                    tvId.visibility = View.GONE
                    tvInform.text = "가입된 회원정보가 없습니다."
                }
            }

            setCanceledOnTouchOutside(true)
            setCancelable(true)
            show()

            val btnLogin: AppCompatButton = dialog.findViewById(R.id.btn_login)
            btnLogin.setOnClickListener {
                //로그인 화면 띄우기
                dismiss()
                findNavController().navigate(R.id.action_login_01_01_to_login_01)

            }
            val btnCancel: TextView = dialog.findViewById(R.id.tv_cancel)
            btnCancel.setOnClickListener {
                dismiss()
            }
        }

    }


}