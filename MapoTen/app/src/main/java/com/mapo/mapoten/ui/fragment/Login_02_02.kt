package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.Login.DuplicateInfoItem
import com.mapo.mapoten.data.Login.GetUserByEmailAuthDto
import com.mapo.mapoten.databinding.FragmentLogin0202Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Login_02_02 : Fragment() {
    private var _binding: FragmentLogin0202Binding? = null
    private val binding get() = _binding!!

    lateinit var userService: UserService
    var code: String = ""
    private var termAgreeck: Int= 0
    private var emailAuthck: Int= 0
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogin0202Binding.inflate(inflater,container,false)

        userService = RetrofitBuilder.getInstance().create(UserService::class.java)

        with(binding) {
            idLengthChecker()
            pwdLengthChecker()

            btnIdDoubleCheck.setOnClickListener {
                if (!idRequiredFieldChecker())
                    return@setOnClickListener
                duplicateId()
            }

            btnCompanyNumberDoubleCheck.setOnClickListener {
                if (!bizrnoRequiredFieldChecker())
                    return@setOnClickListener
                duplicateBizrno()

            }

            btnEmailDoubleCheck.setOnClickListener {
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                if (!emailPatternChecker())
                    return@setOnClickListener
                duplicateEmail()

            }
            btnAuthenticationRequest.setOnClickListener {
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                sendEmailAuth()
            }
            btnConfirm.setOnClickListener {
                if(!authenticationRequiredFieldChecker())
                    return@setOnClickListener
            }

            btnCompanyNumberDoubleCheck.setOnClickListener {
                if (!bizRequiredFieldChecker())
                    return@setOnClickListener

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

            }


            tvLogin.setOnClickListener {
                findNavController().navigate(R.id.action_login_02_02_to_login_01)
            }
            tvPersonalSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_login_02_02_to_login_02_01)
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

    // 이메일 형식 체크
    private fun emailPatternChecker(): Boolean {
        with(binding) {
            // 검사 정규식
            val emailValidation =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
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

    // 사업자등록번호 중복확인
    private fun duplicateBizrno() {
        with(binding) {
            val duplicateBizrno = userService.isDuplicateBizrno(companyNumberTiL.editText!!.text.toString())

            duplicateBizrno.enqueue(object : Callback<DuplicateInfoItem> {
                override fun onResponse(
                    call: Call<DuplicateInfoItem>,
                    response: Response<DuplicateInfoItem>,
                ) {
                    if (response.isSuccessful) {
                        when (response.body()?.isDuplicate) {
                            false -> {
                                companyNumberTiL.helperText = "가입 가능한 사업자등록번호 입니다."
                                companyNumberTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
                            }
                            true -> companyNumberTiL.error = "이미 가입된 사업자등록번호 입니다."
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

// <------------------------------------ 글자 수 체크 ------------------------------------>
    private fun pwdLengthChecker() {
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

    private fun idLengthChecker() {
        with(binding) {
            idEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    return if (idEditText.length() in 1..3) {
                        idTiL.error = "아이디를 4글자 이상 입력해주세요"
                    } else {
                        idTiL.error = null
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
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

    private fun bizrnoRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = companyNumberTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                companyNumberTiL.error = "사업자 등록번호를 입력하세요."
                false
            } else {
                companyNumberTiL.error = null
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

    private fun bizRequiredFieldChecker(): Boolean {
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