package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.DuplicateIdInfoItem
import com.mapo.mapoten.databinding.FragmentLogin0103Binding
import com.mapo.mapoten.databinding.FragmentLogin0201Binding
import com.mapo.mapoten.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login_02_01 : Fragment() {
    private var _binding: FragmentLogin0201Binding? = null
    private val binding get() = _binding!!

    lateinit var userService: UserService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogin0201Binding.inflate(inflater, container, false)

        userService = RetrofitBuilder.getInstance().create(UserService::class.java)


        binding.btnIdDoubleCheck.setOnClickListener {
            val duplicateId = userService.isDuplicateUserId(binding.idEditText.text.toString())

            duplicateId.enqueue(object : Callback<DuplicateIdInfoItem> {
                override fun onResponse(
                    call: Call<DuplicateIdInfoItem>,
                    response: Response<DuplicateIdInfoItem>
                ) {
                    if (response.isSuccessful) {

                        Toast.makeText(
                            context,
                            "200 ${response.body()?.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {

                        Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<DuplicateIdInfoItem>, t: Throwable) {

                    Log.e("error", "통신 실패" + t.localizedMessage)

                }

            })
        }




        return binding.root

    }
}