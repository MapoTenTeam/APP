package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.Login.LoginRequest
import com.mapo.mapoten.data.Login.LoginResponse
import com.mapo.mapoten.databinding.FragmentLogin01Binding
import com.mapo.mapoten.service.UserService
import com.mapo.mapoten.system.AppPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.DigestException
import java.security.MessageDigest

class Login_01 : Fragment() {
    private var _binding: FragmentLogin01Binding? = null
    private val binding get() = _binding!!

    var code: String = ""
    lateinit var userService: UserService
    private lateinit var dialog: Dialog
    private val digits = "0123456789ABCDEF"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentLogin01Binding.inflate(inflater, container, false)

        userService = RetrofitBuilder.getInstance().create(UserService::class.java)

        // Inflate the layout for this fragment
        with(binding) {
            btnLogin.setOnClickListener {
                if (!idRequiredFieldChecker()) {
                    return@setOnClickListener
                }
                if (!pwdRequiredFieldChecker()) {
                    return@setOnClickListener
                }
                login()

            } //로그인 성공시 홈화면으로 이동
            tvFindIdPersonal.setOnClickListener {
                findNavController().navigate(R.id.login_01_01)
            } //개인 아이디찾기 화면으로 이동
            tvFindIdBusiness.setOnClickListener {
                findNavController().navigate(R.id.login_01_02)
            } //기업 아이디찾기 화면으로 이동
            tvFindPwd.setOnClickListener {
                findNavController().navigate(R.id.login_01_03)
            } //비밀번호 찾기 화면으로 이동
            btnSignUpPersonal.setOnClickListener {
                findNavController().navigate(R.id.login_02_01)
            } //개인 회원가입 화면으로 이동
            btnSignUpBusiness.setOnClickListener {
                findNavController().navigate(R.id.login_02_02)
            } //기업 회원가입 화면으로 이동
//            autoLoginCheckBox.setOnClickListener {
//                Log.d("TAG","클릭")
//                val log = AppPrefs.getToken(requireActivity())
//                Log.d("TAG","$log")
//            }

        }
        return binding.root


    }

    // 암호화
    fun hashSHA256(msg: String): String {
        val hash: ByteArray
        try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(msg.toByteArray())
            md.update(salt.toByteArray())
            hash = md.digest()
        } catch (e: CloneNotSupportedException) {
            throw DigestException("couldn't make digest of partial content");
        }
        return bytesToHex(hash)
    }

    fun bytesToHex(byteArray: ByteArray): String {
        val hexChars = CharArray(byteArray.size * 2)
        for (i in byteArray.indices) {
            val v = byteArray[i].toInt() and 0xff
            hexChars[i * 2] = digits[v shr 4]
            hexChars[i * 2 + 1] = digits[v and 0xf]
        }
        return String(hexChars)
    }

    // 로그인
    private fun login() {

        with(binding) {

            var textId = idEditText.text.toString()
            var textPwd = pwdEditText.text.toString()
            Log.d("TAG", "클릭")
            Log.d("TAG", "${hashSHA256(textPwd)}")

            val loginService = userService.requestLogin(LoginRequest(textId,hashSHA256(textPwd)))
            loginService.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>,
                ) { //정상응답이 올경우
                    if (response.isSuccessful) {
                        code = response.body()?.statusCode.toString()
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                            Log.d("TAG", "토큰 : ${response.body()?.accessToken}")
                        val token = response.body()?.accessToken
                            Log.d("TAG", "로그인 유저정보 : ${response.body()?.user_se}")
                        if (token != null) {
                            AppPrefs.saveToken(requireActivity(), token)
                            AppPrefs.saveUserType(requireActivity(), response.body()!!.user_se)
                        }
                        findNavController().navigate(R.id.home_01)
                    }
                    else {
                        showDialog()
                        Toast.makeText(context,
                                "로그인 실패",
                                Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "${hashSHA256(textPwd)}")
                        Log.d("TAG", "로그인 실패 ${response.code()} , ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) { //실패할 경우
                    Log.e("error", "통신 실패" + t.localizedMessage)
                }
            })

        }
    }

    // <-------------------------- 필수 입력 체크 -------------------------->
    private fun idRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = idEditTextInputLayout.editText?.text.toString()
            return if (value.isEmpty()) {
                idEditTextInputLayout.error = "아이디를 입력하세요."
                false
            } else {
                idEditTextInputLayout.error = null
                true
            }
        }
    }

    private fun pwdRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = pwdEditTextInputLayout.editText?.text.toString()
            return if (value.isEmpty()) {
                pwdEditTextInputLayout.error = "비밀번호를 입력하세요."
                false
            } else {
                pwdEditTextInputLayout.error = null
                true
            }
        }
    }

    // dialog
    private fun showDialog() {
        dialog = Dialog(binding.root.context)
        with(dialog) {
            requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
            setContentView(com.mapo.mapoten.R.layout.popup_error_dialog)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            window!!.setLayout(
                android.view.WindowManager.LayoutParams.WRAP_CONTENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT
            )
            show()

            val btnConfirm: AppCompatButton = dialog.findViewById(com.mapo.mapoten.R.id.btn_confirm)
            btnConfirm.setOnClickListener {
                dismiss()
            }
        }
    }

}

