package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.CheckCurrentPW
import com.mapo.mapoten.data.ImageResponse
import com.mapo.mapoten.databinding.FragmentAccount0102Binding
import com.mapo.mapoten.service.AccountManageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class Account_01_02 : Fragment() {

    lateinit var binding: FragmentAccount0102Binding
    var result: Boolean = false
    lateinit var service: AccountManageService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAccount0102Binding.inflate(inflater, container, false)
        val view = binding.root

        service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.userNickName.setText(arguments?.getString("name"))

        binding.submitButton.setOnClickListener {
            //현재비번체크
            validatePassword()
        }
        return view
    }

    private fun validatePassword() {
        var checkedNewPw = false
        checkCurrentPw()
        Log.d("password", "현재비번체크 result: $result")
        val newPw = binding.newPassword.text.toString()
        val newPw2 = binding.confirmNewPassword.text.toString()

       /* if (!result) {
            binding.passwordLayout.error = "현재 비밀번호가 일치하지 않습니다."
            Toast.makeText(context, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, "현재 비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show()
        }*/

        // 새 비번 일치하고, 새비번이 비어있지 않으면 true
        if (newPw == newPw2) {
            checkedNewPw = true
        }else if(newPw.isNullOrEmpty()){
            checkedNewPw = false
            binding.newPasswordLayout.error = "필수 입력 사항입니다."
        }else if(newPw2.isNullOrEmpty()){
            checkedNewPw = false
            binding.confirmNewPasswordLayout.error = "필수 입력 사항입니다."
        }else {
            //새비번 불일치 처리
        }

        if (result && checkedNewPw && newPw.isNotEmpty() && newPw2.isNotEmpty()) {
            updatePassword(newPw2)
        }else {
            Toast.makeText(context, "비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()

        }

    }

    private fun checkCurrentPw() {
        val password = binding.password.text.toString()
        Log.d("password", "현재비번 : $password")

        service.checkCurrentPw(hashSHA256(password)).enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {

                if (response.isSuccessful) {

                    Log.d("password", "code : ${response.code()}")
                    Log.d("password", "현재 비번 확인 message : ${response.body()!!.message}")
                    result = true
                    binding.passwordLayout.helperText = "현재비밀번호가 일치합니다"
                    Log.d("password", "통신후 result : ${result}")
                }else{
                    binding.passwordLayout.helperText="현재비밀번호가 일치하지 않습니다."
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                result = false
                Log.d("password", "현재 비번 확인 : 서버통신실패 ")
            }
        })

    }

    private fun updatePassword(newPw2: String) {
        service.updatePassword(hashSHA256(newPw2)).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(binding.root).navigate(R.id.login_01)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("password", "비번번경 실패 ")
            }
        })
    }

    private fun hashSHA256(password: String): String {
        val spec: KeySpec = PBEKeySpec(
            password.toCharArray(), salt.toByteArray(),
            10000, 512
        )
        val f: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = f.generateSecret(spec).getEncoded()
        return Base64.getEncoder().encodeToString(hash)
    }


}