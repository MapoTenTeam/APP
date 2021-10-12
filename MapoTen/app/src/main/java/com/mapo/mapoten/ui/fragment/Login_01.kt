package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
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
import java.lang.Exception
import java.lang.StringBuilder
import java.security.DigestException
import java.security.MessageDigest
import java.math.BigInteger
import java.nio.charset.Charset


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
        var hash: ByteArray = msg.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        Log.d("TAG","msg: $msg ByteArray :$hash")
        try {
            for (i in 0..99999) {
            val temp: String = msg + salt
//            md.update(salt.toByteArray())
//            md.update(msg.toByteArray())
            md.update(temp.toByteArray())
            hash = md.digest()

            }

        } catch (e: CloneNotSupportedException) {
            throw DigestException("couldn't make digest of partial content");
        }
//        return bytesToHex(hash)
        return Base64.encodeToString(hash, Base64.DEFAULT)
    }


//    // 비밀번호 해싱
//    @Throws(Exception::class)
//    private fun hashing(password: ByteArray, salt: String): String {
//        var password: ByteArray = password
//        val md = MessageDigest.getInstance("SHA-256") // SHA-256 해시함수를 사용
//
//        // key-stretching
//        for (i in 0..99999) {
//            val temp: String =
//                Byte_to_String(password).toString() + salt // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
//            md.update(temp.toByteArray()) // temp 의 문자열을 해싱하여 md 에 저장해둔다
//            password = md.digest() // md 객체의 다이제스트를 얻어 password 를 갱신한다
//        }
//        return Base64.encodeToString(password, Base64.DEFAULT)
////        return Byte_to_String(password)
//    }

    // 바이트 값을 16진수로 변경해준다
    private fun Byte_to_String(temp: ByteArray): String {
        val sb = StringBuilder()
        for (a in temp) {
            sb.append(String.format("%02x", a))
        }
        return sb.toString()
    }

//    fun bytesToHex(byteArray: ByteArray): String {
//        val hexChars = CharArray(byteArray.size * 2)
//        for (i in byteArray.indices) {
//            val v = byteArray[i].toInt() and 0xff
//            hexChars[i * 2] = digits[v shr 4]
//            hexChars[i * 2 + 1] = digits[v and 0xf]
//        }
//        return String(hexChars)
//    }

    // 로그인
    private fun login() {

        with(binding) {

            var textId = idEditText.text.toString()
            var textPwd = pwdEditText.text.toString()
            Log.d("TAG", "클릭")
//            Log.d("TAG", "해시 ${hashing(textPwd.toByteArray(), salt)}")
            Log.d("TAG", "해시 ${hashSHA256(textPwd)}")

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
//                        Log.d("TAG", "비밀번호 ${hashing(textPwd.toByteArray(), salt)}")
//                        Log.d("TAG", "비밀번호 ${hashSHA256(textPwd)}")
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
//                        Log.d("TAG", "비밀번호 ${hashing(textPwd.toByteArray(), salt)}")
                        Log.d("TAG", "비밀번호 ${hashSHA256(textPwd)}")
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

