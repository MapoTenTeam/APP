package com.mapo.mapoten.ui.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mapo.mapoten.databinding.FragmentBussinessAccountEmploymentDetail01Binding
import com.mapo.mapoten.databinding.FragmentEmploymentDetail01Binding

class BussinessAccountEmploymentDetail_01 : Fragment() {
    lateinit var binding : FragmentBussinessAccountEmploymentDetail01Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBussinessAccountEmploymentDetail01Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.state.text = arguments?.getString("state")

        changeStateBackground()


        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }


    private fun changeStateBackground(){
        val stateBackground : GradientDrawable = binding.state.background as GradientDrawable

        when(binding.state.text){
            "승인" -> {
                stateBackground.setColor(Color.parseColor("#E8F1FF"))
                binding.state.setTextColor(Color.parseColor("#1A75FF"))
            }
            "비승인" -> {
                stateBackground.setColor(Color.parseColor("#FFE8EC"))
                binding.state.setTextColor(Color.parseColor("#FF1A43"))
            }
            else -> {
                stateBackground.setColor(Color.parseColor("#FFF6E8"))
                binding.state.setTextColor(Color.parseColor("#FFA31A"))
            }
        }
    }

}