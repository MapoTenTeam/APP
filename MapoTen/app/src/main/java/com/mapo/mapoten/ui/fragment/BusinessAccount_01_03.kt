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
import com.mapo.mapoten.data.UpdatePasswordItem
import com.mapo.mapoten.databinding.FragmentBusinessAccount0103Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BusinessAccount_01_03 : Fragment() {

    lateinit var binding: FragmentBusinessAccount0103Binding
    private var checkedPassword: Boolean = false
    lateinit var userService: UserService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessAccount0103Binding.inflate(inflater, container, false)

        val view = binding.root


        // add listener
        binding.password.addTextChangedListener(editTextListener)
        binding.newPassword.addTextChangedListener(editTextListener)
        binding.confirmNewPassword.addTextChangedListener(editTextListener)

        // back button
        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        // submit button
        userService = RetrofitBuilder.getInstance().create(UserService::class.java)

        binding.submitButton.setOnClickListener {
            if (validatePassword()) {
                Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()

                val updatePassword = userService.updatePassword(binding.password.text.toString())

                updatePassword.enqueue(object : Callback<UpdatePasswordItem> {
                    override fun onResponse(
                        call: Call<UpdatePasswordItem>,
                        response: Response<UpdatePasswordItem>
                    ) {

                        Log.d("passwordUpdate", "code : "+ response.code() )
                        if (response.isSuccessful) {

                            Toast.makeText(
                                context,
                                "200 ${response.body()?.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {

                            Toast.makeText(context, "${response.message()}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<UpdatePasswordItem>, t: Throwable) {

                        Log.e("error", "통신 실패" + t.localizedMessage)

                    }

                })


                Navigation.findNavController(view).navigate(R.id.login_01)
            } else {
                // 전송 실패 처리
            }
        }

        return view


    }

    private fun validatePassword(): Boolean {

        // test - 현재 비밀번호 1234
        // 모든 조건 만족시 return true
        return if (binding.password.text.toString() == "1234" && checkedPassword && binding.newPassword.text!!.isNotEmpty()) {
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
        }
    }

    // listener
    private val editTextListener = object : TextWatcher {
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

    }


}