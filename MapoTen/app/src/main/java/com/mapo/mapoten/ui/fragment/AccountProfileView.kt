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
import com.mapo.mapoten.databinding.FragmentAccountProfileViewBinding
import com.mapo.mapoten.databinding.FragmentBusinessProfileViewBinding


class AccountProfileView : Fragment() {
    lateinit var binding: FragmentAccountProfileViewBinding

    lateinit var mber_nm: String
    lateinit var mber_id: String
    lateinit var mber_email: String
    lateinit var mobile: String
    lateinit var address : String
    lateinit var detailad : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountProfileViewBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        init()


        binding.updateButton.setOnClickListener {
            val bundle = bundleOf(
                "mber_nm" to mber_nm,
                "mber_id" to mber_id,
                "mber_email" to mber_email,
                "mobile" to mobile,
                "address" to address,
                "detailad" to detailad,
            )
            Navigation.findNavController(view).navigate(R.id.account_01_01, bundle)
        }




        return view
    }

    private fun init() {
        mber_nm = arguments?.getString("mber_nm").toString()
        mber_id = arguments?.getString("mber_id").toString()
        mber_email = arguments?.getString("mber_email").toString()
        mobile = arguments?.getString("mobile").toString()
        address = arguments?.getString("address").toString()
        detailad = arguments?.getString("detailad").toString()

        binding.businessNameValue.setText(mber_nm)
        binding.businessNumberValue.setText(mber_id)
        binding.ownerNameValue.setText(mber_email)
        binding.businessAddress1Value.setText(mobile)
        binding.businessCategortyValue.setText(address)
        binding.businessEmpNumValue.setText(detailad)

    }


}