package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.PopupFindIdDialogBinding
import java.time.LocalDate

const val DIALOG_PARAM = "dialog_param"

class FindIdDialogFragment : DialogFragment() {

    private var _binding: PopupFindIdDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PopupFindIdDialogBinding.inflate(inflater, container, false)

        val code = arguments?.getString("code")
        val userId = arguments?.getString("userId")
        Log.d("TAG","다이얼 코드 : $code, 유저아이디 : $userId")
        with(binding) {

            when (code) {
                "200" -> tvId.text = userId
                "400" -> tvInformation.text = "가입된 회원정보가 없습니다."
                "null" -> tvInformation.text = "가입된 회원정보가 없습니다."
            }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            btnLogin.setOnClickListener {
                //로그인 화면 띄우기
                dismiss()
                findNavController().navigate(R.id.action_login_01_01_to_login_01)
            }
            tvCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}