package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.PersonalProfile
import com.mapo.mapoten.databinding.FragmentAccount01Binding
import com.mapo.mapoten.databinding.FragmentMypage01Binding
import com.mapo.mapoten.service.AccountManageService
import com.mapo.mapoten.system.AppPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Mypage : Fragment() {

    lateinit var binding: FragmentMypage01Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMypage01Binding.inflate(inflater, container, false)

        val view = binding.root


        if (AppPrefs.getUserType(requireContext()).equals("GNR")) {
            findNavController().navigate(R.id.account_01)
        } else {
            findNavController().navigate(R.id.businessAccount_01)
        }

        return view
    }


}