package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.Login.*
import com.mapo.mapoten.databinding.FragmentLogin0201Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login_02_01 : Fragment() {
    private var _binding: FragmentLogin0201Binding? = null
    private val binding get() = _binding!!

    lateinit var userService: UserService
    var code: String = "1"
    var termAgreeck: Boolean = false
    var emailAuthck: Boolean = false
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentLogin0201Binding.inflate(inflater, container, false)

        userService = RetrofitBuilder.getInstance().create(UserService::class.java)


        with(binding) {
            textLengthChecker()
            btnIdDoubleCheck.setOnClickListener {
                if (!idRequiredFieldChecker())
                    return@setOnClickListener
                duplicateId()
            }

            btnEmailDoubleCheck.setOnClickListener {
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                duplicateEmail()
            }

            btnAuthenticationRequest.setOnClickListener {
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                sendEmailAuth()
            }

            allCheckBox.setOnClickListener {
                setAllCheck()
            }

            btnConfirm.setOnClickListener {
                if (!authenticationRequiredFieldChecker())
                    return@setOnClickListener
                authenticationNumberChecker()
            }

            btnSignup.setOnClickListener {
                if (!nameRequiredFieldChecker())
                    return@setOnClickListener
                if (!idRequiredFieldChecker())
                    return@setOnClickListener
                if (!pwdRequiredFieldChecker())
                    return@setOnClickListener
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                signUp()

            }

            tvLogin.setOnClickListener {
                findNavController().navigate(R.id.action_login_02_01_to_login_01)
            }
            tvBusinessSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_login_02_01_to_login_02_02)
            }

        }
        return binding.root

    }

    // 아이디 중복확인
    private fun duplicateId() {
        with(binding) {
            val duplicateId = userService.isDuplicateUserId(idEditText.text.toString())

            duplicateId.enqueue(object : Callback<DuplicateInfoItem> {
                override fun onResponse(
                    call: Call<DuplicateInfoItem>,
                    response: Response<DuplicateInfoItem>,
                ) {
                    if (response.isSuccessful) {
                        when (response.body()?.isDuplicate) {
                            false -> {
                                idTiL.helperText = "사용 가능한 아이디입니다"
                                idTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
                            }
                            true -> idTiL.error = "이미 사용중인 아이디입니다"
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "${response.body()?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DuplicateInfoItem>, t: Throwable) {
                    Log.e("error", "통신 실패" + t.localizedMessage)
                }

            })
        }
    }

    // 이메일 중복확인
    private fun duplicateEmail() {
        with(binding) {
            val duplicateEmail = userService.isDuplicateUserEmail(emailEditText.text.toString())

            duplicateEmail.enqueue(object : Callback<DuplicateInfoItem> {
                override fun onResponse(
                    call: Call<DuplicateInfoItem>,
                    response: Response<DuplicateInfoItem>,
                ) {
                    if (response.isSuccessful) {
                        when (response.body()?.isDuplicate) {
                            false -> {
                                emailTiL.helperText = "사용 가능한 이메일입니다"
                                emailTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                Log.d(
                                    "TAG",
                                    "${response.body()?.statusCode} : ${response.body()?.message}"
                                )
                            }
                            true -> emailTiL.error = "이미 사용중인 이메일입니다"
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "${response.body()?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DuplicateInfoItem>, t: Throwable) {
                    Log.e("error", "통신 실패" + t.localizedMessage)
                }
            })
        }
    }

    // 이메일 인증
    private fun sendEmailAuth() {
        with(binding) {
            val emailAuth = userService.emailAuth(emailEditText.text.toString())

            emailAuth.enqueue(object : Callback<GetUserByEmailAuthDto> {
                override fun onResponse(
                    call: Call<GetUserByEmailAuthDto>,
                    response: Response<GetUserByEmailAuthDto>
                ) {
                    if (response.isSuccessful) {
                        code = response.body()?.code.toString()!!
                        emailTiL.helperText = "인증번호가 전송되었습니다."
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                    } else {
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<GetUserByEmailAuthDto>, t: Throwable) {
                    Log.e("error", "통신 실패" + t.localizedMessage)
                }

            })
        }
    }

    // 회원가입
    private fun signUp() {
        with(binding) {
            Log.d("TAG", "회원가입")
            Log.d("TAG", "이메일인증 $emailAuthck 약관동의 $termAgreeck")
            val signUpService = userService.requestSignUp(
                SignUpRequest
                    (
                    nameEditText.text.toString(),
                    idEditText.text.toString(),
                    emailEditText.text.toString(),
                    pwdEditText.text.toString(),
                    emailAuthck,
                    termAgreeck
                )
            )

            signUpService.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>,
                ) { //정상응답이 올경우
                    if (response.isSuccessful) {
                        Log.d("TAG", "성공")
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                    } else {
                        Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) { //실패할 경우
                    Log.e("error", "통신 실패" + t.localizedMessage)
                }

            })
        }

    }

    // 비밀번호 8자 이상 체크
    private fun textLengthChecker() {
        with(binding) {
            pwdEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (pwdEditText.length() in 1..7) {
                        pwdTiL.error = "비밀번호를 8글자 이상 입력해주세요"
                    } else {
                        pwdTiL.error = null
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
            pwdConfirmEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    if (pwdEditText.text.isNullOrEmpty() == pwdConfirmEditText.text.isNullOrEmpty()) {
                        if (pwdEditText.text.toString() == pwdConfirmEditText.text.toString()) {
                            pwdConfirmTiL.helperText = "비밀번호가 일치합니다."
                        } else pwdConfirmTiL.error = "비밀번호가 일치하지 않습니다."
                    }

                }

            })
        }
    }

    // <------------------------------------ 필수 입력 값 체크 ------------------------------------>

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

    private fun idRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = idTiL.editText?.text.toString()
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

    private fun authenticationRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = authenticationNumberTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                authenticationNumberTiL.error = "인증번호를 입력하세요."
                false
            } else {
                authenticationNumberTiL.error = null
                true
            }
        }
    }

    private fun pwdRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = pwdTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                pwdTiL.error = "비밀번호를 입력하세요."
                false
            } else {
                pwdTiL.error = null
                true
            }
        }
    }
    // <-------------------------------------------------------------------------------->

    // 인증번호 확인
    private fun authenticationNumberChecker() {
        with(binding) {
            val text = authenticationNumberEditText.text.toString()
            if (text == code) {
                emailAuthck = true
                authenticationNumberTiL.helperText = "이메일 인증이 완료되었습니다."
                authenticationNumberTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
            } else {
                emailAuthck = false
                authenticationNumberTiL.error = "인증번호가 일치하지 않습니다."
            }
        }
    }

    // 약관 전체동의
    private fun setAllCheck() {
        with(binding) {
            if (allCheckBox.isChecked) {
                tvTos1.isChecked = true
                tvTos2.isChecked = true
                termAgreeck = true
            } else {
                tvTos1.isChecked = false
                tvTos2.isChecked = false
                termAgreeck = false
            }
        }
    }

    private fun getTerms() {
        with(binding) {

        }
    }




}


