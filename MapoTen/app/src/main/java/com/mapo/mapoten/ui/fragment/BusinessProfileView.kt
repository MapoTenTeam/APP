package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentBusinessProfileViewBinding


class BusinessProfileView : Fragment() {
    lateinit var binding: FragmentBusinessProfileViewBinding

    lateinit var cmpny_nm: String
    lateinit var bizrno: String
    lateinit var ceo: String
    lateinit var address: String
    lateinit var detailAdd : String
    lateinit var category: String
    lateinit var empNum: String
    lateinit var webSite: String
    lateinit var cmpny_email: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBusinessProfileViewBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        init()


        binding.updateButton.setOnClickListener {
            val bundle = bundleOf(
                "cmpny_nm" to cmpny_nm,
                "bizrno" to bizrno,
                "ceo" to ceo,
                "address" to address,
                "detailad" to detailAdd,
                "category" to category,
                "empNum" to empNum,
                "webSite" to webSite,
                "cmpny_email" to cmpny_email
            )
            Navigation.findNavController(view).navigate(R.id.businessProfileEdit, bundle)
        }




        return view
    }

    private fun init() {
        cmpny_nm = arguments?.getString("cmpny_nm").toString()
        bizrno = arguments?.getString("bizrno").toString()
        ceo = arguments?.getString("ceo").toString()
        address = arguments?.getString("address").toString()
        detailAdd = arguments?.getString("detailad").toString()
        category = arguments?.getString("category").toString()
        empNum = arguments?.getString("empNum").toString()
        webSite = arguments?.getString("webSite").toString()
        cmpny_email = arguments?.getString("cmpny_email").toString()
        val img = arguments?.getString("cmpny_img")
        Log.d("bundle", "이미지주소 : $img")

        binding.businessNameValue.setText(cmpny_nm)
        binding.businessNumberValue.setText(bizrno)
        binding.ownerNameValue.setText(ceo)
        binding.businessAddress1Value.setText(address)
        binding.businessCategortyValue.setText(category)
        binding.businessEmpNumValue.setText(empNum)
        binding.businessWebValue.setText(webSite)
        binding.businessEmailValue.setText(cmpny_email)
        Glide.with(binding.imgBusinessLogoValue).load(img).into(binding.imgBusinessLogoValue)

        val approvalStatus = arguments?.getString("approval")
        when (approvalStatus) {

            "승인 요청 필요" -> {
                binding.applyButton.visibility = View.VISIBLE
            }
            "승인 대기중" -> {
                binding.applyButton.visibility = View.VISIBLE
            }

            "승인 완료" -> {
                binding.applyButton.visibility = View.INVISIBLE

            }
            else -> {
            }
        }
    }


}