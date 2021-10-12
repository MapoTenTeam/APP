package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.mapo.mapoten.databinding.FragmentBusinessAccount0103Binding
import com.mapo.mapoten.service.AccountManageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BusinessAccount_01_03 : Fragment() {

    lateinit var binding: FragmentBusinessAccount0103Binding
    private var checkedPassword: Boolean = false
    lateinit var service : AccountManageService
    private var result :Boolean =false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessAccount0103Binding.inflate(inflater, container, false)
        val view = binding.root

        service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)

        binding.userNickName.setText(arguments?.getString("cmpny_nm"))

      /*  // add listener
        binding.password.addTextChangedListener(editTextListener)
        binding.newPassword.addTextChangedListener(editTextListener)
        binding.confirmNewPassword.addTextChangedListener(editTextListener)*/

        // back button
        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        // submit button
        binding.submitButton.setOnClickListener {
            //현재비번체크
            validatePassword()
          /*  if (validatePassword()) {
                Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.login_01)
            } else {
                // 전송 실패 처리
            }*/
        }

        return view


    }

    private fun validatePassword() {
        var checkedNewPw: Boolean = false
        result = checkCurrentPw()
        Log.d("password", "현재비번체크 result: $result")
        val newPw = binding.newPassword.text.toString()
        val newPw2 = binding.confirmNewPassword.text.toString()

        if (!result) {
            binding.passwordLayout.error = "현재 비밀번호가 일치하지 않습니다."
            Toast.makeText(context, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, "현재 비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show()
        }

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

        if (result && checkedNewPw) {
            updatePassword(newPw2)
        }

    }




    private fun checkCurrentPw() :Boolean {
        val password = binding.password.text.toString()
        Log.d("password", "현재비번 : $password")

        service.checkCurrentPw(password).enqueue(object : Callback<ImageResponse>{
            override fun onResponse(call: Call<ImageResponse>,response: Response<ImageResponse>) {

                if (response.isSuccessful){
                    Log.d("password", "code : ${response.code()}")
                    Log.d("password", "현재 비번 확인 message : ${response.body()!!.message}")

                    result = true
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.d("password", "서버통신실패 ")
            }
        })

        return result
    }

    private fun updatePassword(newPw2: String) {
        service.updateBusinessPassword(newPw2).enqueue(object : Callback<Void> {
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



}