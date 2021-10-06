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

class Account_01_02 : Fragment() {

    lateinit var binding : FragmentAccount0102Binding
    private var result :Boolean = false
    lateinit var service : AccountManageService

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
        var checkedNewPw :Boolean = false
        result = checkCurrentPw()
        Log.d("password", "???: $result")
        if (!result){
            Toast.makeText(context, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(context, "현재 비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show()
        }

        // 새 비번 일치하고, 새비번이 비어있지 않으면 true
        val newPw = binding.newPassword.text.toString()
        val newPw2 = binding.confirmNewPassword.text.toString()
        if(newPw == newPw2){
            checkedNewPw = true
        }
        //현재 비번 불일치 처리
        //새비번 불일치 처리

        if (result && checkedNewPw){
            updatePassword(newPw2)
        }
    }

    private fun checkCurrentPw() :Boolean {
        //var result = false
        val password = CheckCurrentPW(binding.password.text.toString())
        //  val password = binding.password.text.toString()
        Log.d("password", "현재비번 : $password")
        service.checkCurrentPw(password).enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {

                if (response.isSuccessful){

                    Log.d("password", "현재 비번 확인 : $response.code()")
                    //    Toast.makeText(requireContext(), "현재 비밀번호 확인 완료", Toast.LENGTH_SHORT).show()
                    result = true
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.d("password", "서버통신실패 ")
            }
        })

        return result
    }

    private fun updatePassword(newPw2:String){
        val password = CheckCurrentPW(newPw2)
        service.updatePassword(password).enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
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