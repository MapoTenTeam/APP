package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentMypage01Binding
import com.mapo.mapoten.ui.activity.App

class Mypage : Fragment() {

    lateinit var binding: FragmentMypage01Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMypage01Binding.inflate(inflater, container, false)

        val view = binding.root

        Log.d("mypage", "type : ${App.prefs.type}")

        if (App.prefs.type.equals("GNR")) {
            findNavController().navigate(R.id.action_mypage_01_to_account_01)
        } else if(App.prefs.type.equals("ENT")){
            findNavController().navigate(R.id.action_mypage_01_to_businessAccount_01)
        }else {
            Log.d("mypage", "null")
            Navigation.findNavController(view).navigate(R.id.action_mypage_01_to_login_01)
        }


//        if (AppPrefs.getUserType(requireContext()).equals("GNR")) {
//            findNavController().navigate(R.id.account_01)
//        } else {
//            findNavController().navigate(R.id.businessAccount_01)
//        }

        return view
    }


}