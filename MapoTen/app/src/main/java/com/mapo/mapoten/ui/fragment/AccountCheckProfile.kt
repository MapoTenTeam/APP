package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentAccountCheckProfileBinding


class AccountCheckProfile : Fragment() {
    lateinit var binding : FragmentAccountCheckProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentAccountCheckProfileBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.profileButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.accountNewProfile)
        }

        return view
    }


}