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

        // add listener
       /* binding.password.addTextChangedListener(editTextListener)
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

        // test - 현재 비밀번호 1234
        // 모든 조건 만족시 return true
        //현재 비밀번호 일치하고,
        result = checkCurrentPw()
        if (!result){
            Toast.makeText(context, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(context, "현재 비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show()
        }
        // 새 비번 일치하고, 새비번이 비어있지 않으면 true
        //현재 비번 불일치 처리
        //새비번 불일치 처리
       /* return if (binding.password.text.toString() == "1234" && checkedPassword && binding.newPassword.text!!.isNotEmpty()) {
            true
        } else if (binding.password.text.toString() != "1234") {
            Toast.makeText(context, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            false
        } else if (!checkedPassword) {
            Toast.makeText(context, "새 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            binding.newPassword.setText("")
            binding.confirmNewPassword.setText("")
            false
        } else {
            Toast.makeText(context, "입력란을 확인해주세요!", Toast.LENGTH_SHORT).show()
            false
        }*/
    }

    // listener
   /* private val editTextListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("password", "입력전");
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            checkedPassword =
                binding.newPassword.text.toString() == binding.confirmNewPassword.text.toString()
        }

        override fun afterTextChanged(p0: Editable) {
            Log.i("password", "입력후");

        }

    }*/

    private fun checkCurrentPw() :Boolean {
        //var result = false
        val password = CheckCurrentPW(binding.password.text.toString())
      //  val password = binding.password.text.toString()
        Log.d("password", "현재비번 : $password")
        service.checkCurrentPw(password).enqueue(object : Callback<ImageResponse>{
            override fun onResponse(call: Call<ImageResponse>,response: Response<ImageResponse>) {

                if (response.isSuccessful){
                    val a = response.message()
                    Log.d("password", "현재 비번 확인 : $a")
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



}