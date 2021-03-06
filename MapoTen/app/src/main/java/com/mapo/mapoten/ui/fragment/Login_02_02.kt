package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.Login.*
import com.mapo.mapoten.databinding.FragmentLogin0202Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.DigestException
import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util.*
import java.util.regex.Pattern
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class Login_02_02 : Fragment() {
    private var _binding: FragmentLogin0202Binding? = null
    private val binding get() = _binding!!

    lateinit var userService: UserService
    var code: String = ""
    private var termAgreeck: Int = 0
    private var emailAuthck: Int = 0
    private var bizrnock: Int = 0
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogin0202Binding.inflate(inflater, container, false)

        userService = RetrofitBuilder.getInstance().create(UserService::class.java)

        with(binding) {
            idLengthChecker()
            pwdLengthChecker()

            btnIdDoubleCheck.setOnClickListener {
                if (!idRequiredFieldChecker())
                    return@setOnClickListener
                duplicateId()
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
                if (!authenticationRequiredFieldChecker())
                    return@setOnClickListener
                authenticationNumberChecker()
            }
            btnCompanyNumberDoubleCheck.setOnClickListener {
                if (!bizrnoRequiredFieldChecker())
                    return@setOnClickListener
                duplicateBizrno()

            }
            allCheckBox.setOnClickListener {
                setAllCheck()
            }

            btnSignup.setOnClickListener {
                if (!nameRequiredFieldChecker())
                    return@setOnClickListener
                if (!idRequiredFieldChecker())
                    return@setOnClickListener
                if (!bizrnoRequiredFieldChecker())
                    return@setOnClickListener
                if (!emailRequiredFieldChecker())
                    return@setOnClickListener
                if (!authenticationRequiredFieldChecker())
                    return@setOnClickListener
                if (!pwdRequiredFieldChecker())
                    return@setOnClickListener
                if (!pwdCnfRequiredFieldChecker())
                    return@setOnClickListener
                if (!companyRequiredFieldChecker())
                    return@setOnClickListener
                if (!termCheck())
                    return@setOnClickListener
                signUp()
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

    // ?????????
    fun hashSHA256(password: String): String {
        val spec: KeySpec = PBEKeySpec(
            password.toCharArray(), salt.toByteArray(),
            10000, 512
        )
        val f: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = f.generateSecret(spec).getEncoded()
        return Base64.getEncoder().encodeToString(hash)
    }

    // ????????????
    private fun signUp() {
        with(binding) {
            var textPwd = pwdEditText.text.toString()

            Log.d("TAG", "???????????? ??????????????? $emailAuthck ???????????? $termAgreeck")
            val signUpService = userService.requestEnterpriseSignUp(
                AuthCredentialsEnterpriseDto(
                companyNameEditText.text.toString(),
                idEditText.text.toString(),
                emailEditText.text.toString(),
                hashSHA256(textPwd),
                nameEditText.text.toString(),
                companyNumberEditText.text.toString(),
                emailAuthck,
                termAgreeck,
                bizrnock)
            )

            signUpService.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>,
                ) { //??????????????? ?????????

                    if (response.isSuccessful) {
                        code = response.body()?.statusCode.toString()
                        Log.d("TAG", "${response.code()} + ${response.message()}")
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")

                        showDialog(code)
                    } else {
                        Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) { //????????? ??????
                    Log.e("error", "?????? ??????" + t.localizedMessage)
                }

            })
        }

    }

    // ????????? ????????????
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
                                idTiL.helperText = "?????? ????????? ??????????????????"
                                idTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
                            }
                            true -> idTiL.error = "?????? ???????????? ??????????????????"
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
                    Log.e("error", "?????? ??????" + t.localizedMessage)
                }

            })
        }
    }

    // ????????? ?????? ??????
    private fun emailPatternChecker(): Boolean {
        with(binding) {
            // ?????? ?????????
            val emailValidation =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            val value: String = emailTiL.editText?.text.toString().trim() // ????????????
            val p = Pattern.matches(emailValidation, value) // ????????????
            return if (p) { // ????????? ??????
                emailTiL.error = null
                true
            } else {
                emailTiL.error = "????????? ????????? ?????? ???????????????."
                false
            }
        }
    }

    // ????????? ????????????
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
                                emailTiL.helperText = "?????? ????????? ??????????????????"
                                emailTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                Log.d(
                                    "TAG",
                                    "${response.body()?.statusCode} : ${response.body()?.message}"
                                )
                            }
                            true -> emailTiL.error = "?????? ???????????? ??????????????????"
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
                    Log.e("error", "?????? ??????" + t.localizedMessage)
                }
            })
        }
    }

    // ????????? ??????
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
                        emailTiL.helperText = "??????????????? ?????????????????????."
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                    } else {
                        Log.d("TAG", "${response.body()?.statusCode} : ${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<GetUserByEmailAuthDto>, t: Throwable) {
                    Log.e("error", "?????? ??????" + t.localizedMessage)
                }

            })
        }
    }
    // ???????????? ??????
    private fun authenticationNumberChecker() {
        with(binding) {
            val text = authenticationNumberTiL.editText?.text.toString()
            if (text == code) {
                emailAuthck = 1
                authenticationNumberTiL.helperText = "????????? ????????? ?????????????????????."
                authenticationNumberTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
            } else {
                emailAuthck = 0
                authenticationNumberTiL.error = "??????????????? ???????????? ????????????."
            }
        }
    }

    // ????????????????????? ????????????
    private fun duplicateBizrno() {
        with(binding) {
            val duplicateBizrno =
                userService.isDuplicateBizrno(companyNumberEditText.text.toString())


            duplicateBizrno.enqueue(object : Callback<DuplicateInfoItem> {
                override fun onResponse(
                    call: Call<DuplicateInfoItem>,
                    response: Response<DuplicateInfoItem>,
                ) {
                    if (response.isSuccessful) {
                        Log.d("TAG", "???????????? ${response.body()?.isDuplicate}")
                        when (response.body()?.isDuplicate) {
                            false -> {
                                bizrnock = 1
                                companyNumberTiL.helperText = "?????? ????????? ????????????????????? ?????????."
                                companyNumberTiL.setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
                            }
                            true -> {
                                bizrnock = 0
                                companyNumberTiL.error = "?????? ????????? ????????????????????? ?????????."}
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
                    Log.e("error", "?????? ??????" + t.localizedMessage)
                }

            })
        }
    }

    // ?????? ????????????
    private fun setAllCheck(){
        with(binding) {
            if (allCheckBox.isChecked) {
                tvTos1.isChecked = true
                tvTos2.isChecked = true
            } else {
                tvTos1.isChecked = false
                tvTos2.isChecked = false
            }
        }
    }
    private fun termCheck() : Boolean{
        with(binding) {
            return if (allCheckBox.isChecked) {
                tosError1.visibility = View.GONE
                tosError2.visibility = View.GONE
                termAgreeck = 1
                true
            } else if(tvTos1.isChecked && tvTos2.isChecked){
                tosError1.visibility = View.GONE
                tosError2.visibility = View.GONE
                termAgreeck = 1
                true
            } else if (!tvTos1.isChecked && !tvTos2.isChecked){
                tosError1.visibility = View.VISIBLE
                tosError2.visibility = View.VISIBLE
                termAgreeck = 0
                false
            } else if (!tvTos1.isChecked){
                tosError1.visibility = View.VISIBLE
                tosError2.visibility = View.GONE
                termAgreeck = 0
                false
            } else {
                tosError1.visibility = View.GONE
                tosError2.visibility = View.VISIBLE
                termAgreeck = 0
                false
            }
        }
    }

    // <------------------------------------ ?????? ??? ?????? ------------------------------------>
    private fun pwdLengthChecker() {
        with(binding) {
            pwdEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (pwdEditText.length() in 1..7) {
                        pwdTiL.error = "??????????????? 8?????? ?????? ??????????????????"
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
                            pwdConfirmTiL.helperText = "??????????????? ???????????????."
                        } else pwdConfirmTiL.error = "??????????????? ???????????? ????????????."
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
                        idTiL.error = "???????????? 4?????? ?????? ??????????????????"
                    } else {
                        idTiL.error = null
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

        }
    }

// <------------------------------------ ?????? ?????? ??? ?????? ------------------------------------>
    private fun nameRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = nameTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                nameTiL.error = "????????? ???????????????."
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
                idTiL.error = "???????????? ???????????????."
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
                emailTiL.error = "???????????? ???????????????."
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
                authenticationNumberTiL.error = "??????????????? ???????????????."
                false
            } else {
                authenticationNumberTiL.error = null
                true
            }
        }
    }
    private fun pwdRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = pwdEditText.text.toString()
            return if (value.isEmpty()) {
                pwdTiL.error = "??????????????? ???????????????."
                false
            } else {
                pwdTiL.error = null
                true
            }
        }
    }
    private fun pwdCnfRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = pwdConfirmEditText.text.toString()
            return if (value.isEmpty()) {
                pwdConfirmTiL.error = "???????????? ????????? ???????????????."
                false
            } else {
                pwdConfirmTiL.error = null
                true
            }
        }
    }
    private fun bizrnoRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = companyNumberTiL.editText?.text.toString()
            return if (value.isEmpty()) {
                companyNumberTiL.error = "????????? ??????????????? ???????????????."
                false
            } else {
                companyNumberTiL.error = null
                true
            }
        }
    }
    private fun companyRequiredFieldChecker(): Boolean {
        with(binding) {
            val value: String = companyNameEditText.text.toString()
            return if (value.isEmpty()) {
                companyNameTiL.error = "??????????????? ???????????????."
                false
            } else {
                companyNameTiL.error = null
                true
            }
        }
    }

    // dialog
    private fun showDialog(code: String) {
        dialog = Dialog(binding.root.context)
        with(dialog) {
            requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
            setContentView(com.mapo.mapoten.R.layout.popup_signup_complete)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.setLayout(
                android.view.WindowManager.LayoutParams.WRAP_CONTENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT
            )

            val tvTitle: TextView = findViewById(R.id.tv_title)
            val tvInform: TextView = findViewById(R.id.tv_information)
            val iv: ImageView = findViewById(R.id.imageView)
            val btn: Button = findViewById(R.id.btn_confirm)

            when (code) {
                "201" -> {  // ?????? ??????
                    btn.text ="????????? / ???????????? ??????"
                }
                "406" -> {  // ????????? ?????? or ???????????? ?????? ??????
                    tvTitle.text = "?????? ?????????"
                    iv.setImageResource(R.drawable.ic_error_dialog)
                    tvInform.text = "??????????????? ?????????????????????. \n ????????? ?????? or ???????????? ????????? ??????????????????."
                }
                "409" -> {  // ?????? ID ??????
                    tvTitle.text = "?????? ?????????"
                    iv.setImageResource(R.drawable.ic_error_dialog)
                    tvInform.text = "????????? ???????????? ???????????????. \n"
                }
                "null" -> {
                    tvTitle.text = "?????? ?????????"
                    iv.setImageResource(R.drawable.ic_error_dialog)
                    tvInform.text = "??????????????? ?????????????????????. \n ????????? ???????????????."
                }
            }

            show()

            val btnConfirm: AppCompatButton = dialog.findViewById(com.mapo.mapoten.R.id.btn_confirm)
            btnConfirm.setOnClickListener {
                //????????? ?????? ?????????
                dismiss()
                findNavController().navigate(com.mapo.mapoten.R.id.action_login_02_02_to_login_01)
            }
            val btnCancel: TextView = dialog.findViewById(com.mapo.mapoten.R.id.tv_cancel)
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

}