package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.Login.DuplicateIdInfoItem
import com.mapo.mapoten.data.Login.EmailAuth
import com.mapo.mapoten.databinding.FragmentLogin0201Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login_02_01 : Fragment() {
    private var _binding: FragmentLogin0201Binding? = null
    private val binding get() = _binding!!

    lateinit var userService: UserService
    var code: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogin0201Binding.inflate(inflater, container, false)

        userService = RetrofitBuilder.getInstance().create(UserService::class.java)


        with(binding) {
            btnIdDoubleCheck.setOnClickListener {
                if (!idRequiredFieldChecker())
                    return@setOnClickListener
                val duplicateId = userService.isDuplicateUserId(idEditText.text.toString())

                duplicateId.enqueue(object : Callback<DuplicateIdInfoItem> {
                    override fun onResponse(
                        call: Call<DuplicateIdInfoItem>,
                        response: Response<DuplicateIdInfoItem>,
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
                            Toast.makeText(context,
                                "${response.body()?.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DuplicateIdInfoItem>, t: Throwable) {
                        Log.e("error", "통신 실패" + t.localizedMessage)
                    }

                })
            }

            btnEmailDoubleCheck.setOnClickListener {
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                val duplicateEmail = userService.isDuplicateUserEmail(emailEditText.text.toString())

                duplicateEmail.enqueue(object : Callback<DuplicateIdInfoItem> {
                    override fun onResponse(
                        call: Call<DuplicateIdInfoItem>,
                        response: Response<DuplicateIdInfoItem>,
                    ) {
                        if (response.isSuccessful) {
                            when (response.body()?.isDuplicate) {
                                false -> {
                                    emailTiL.helperText = "사용 가능한 이메일입니다"
                                    emailTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                }
                                true -> emailTiL.error = "이미 사용중인 이메일입니다"
                            }
                        } else {
                            Toast.makeText(context,
                                "${response.body()?.message}",
                                Toast.LENGTH_SHORT).show()
                            Log.d("TAG", "response : ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<DuplicateIdInfoItem>, t: Throwable) {
                        Log.e("error", "통신 실패" + t.localizedMessage)
                    }

                })
            }

            btnAuthenticationRequest.setOnClickListener {
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener

                val emailAuth = userService.emailAuth(emailEditText.text.toString())
                Log.d("TAG","${emailEditText.text.toString()}")

                emailAuth.enqueue(object : Callback<EmailAuth> {
                    override fun onResponse(call: Call<EmailAuth>, response: Response<EmailAuth>) {
                        if (response.isSuccessful) {
                            code = response.body()?.code!!

                        } else {

                        }
                    }

                    override fun onFailure(call: Call<EmailAuth>, t: Throwable) {
                        Log.e("error", "통신 실패" + t.localizedMessage)
                    }


                })
            }

            btnConfirm.setOnClickListener {
                if (!authenticationRequiredFieldChecker())
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
                findNavController().navigate(R.id.action_login_02_01_to_login_01)
            }
            tvBusinessSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_login_02_01_to_login_02_02)
            }

        }
        return binding.root

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

}


