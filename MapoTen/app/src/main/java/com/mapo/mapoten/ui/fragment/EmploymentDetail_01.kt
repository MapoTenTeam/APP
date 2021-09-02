package com.mapo.mapoten.ui.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mapo.mapoten.databinding.FragmentEmploymentDetail01Binding

class EmploymentDetail_01 : Fragment() {
    lateinit var binding : FragmentEmploymentDetail01Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmploymentDetail01Binding.inflate(inflater, container, false)
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
                stateBackground.setColor(Color.parseColor("#132646EC"))
                binding.state.setTextColor(Color.parseColor("#2646EC"))
            }
            "비승인" -> {
                stateBackground.setColor(Color.parseColor("#17E97575"))
                binding.state.setTextColor(Color.parseColor("#FF5858"))
            }
            else -> {
                stateBackground.setColor(Color.parseColor("#50C4C4C4"))
                binding.state.setTextColor(Color.parseColor("#5F5F5F"))
            }
        }
    }

}