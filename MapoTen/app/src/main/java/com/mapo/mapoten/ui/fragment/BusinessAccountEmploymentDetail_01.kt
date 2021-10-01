package com.mapo.mapoten.ui.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.employment.JobEnterpriseDetailOutputDto
import com.mapo.mapoten.data.employment.SelectJobEnterpriseDetailOutputDto
import com.mapo.mapoten.databinding.FragmentBusinessAccountEmploymentDetail01Binding
import com.mapo.mapoten.service.EmploymentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessAccountEmploymentDetail_01 : Fragment() {
    lateinit var binding: FragmentBusinessAccountEmploymentDetail01Binding
    lateinit var employmentService: EmploymentService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            FragmentBusinessAccountEmploymentDetail01Binding.inflate(inflater, container, false)
        val view = binding.root

        //arguments?.getString("jobId")?.let { getDetail(it.toInt()) }
        getDetail(6)
        binding.state.text = arguments?.getString("state")
        changeStateBackground()


        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }


    private fun changeStateBackground() {
        val stateBackground: GradientDrawable = binding.state.background as GradientDrawable

        when (binding.state.text) {
            "승인완료" -> {
                stateBackground.setColor(Color.parseColor("#E8F1FF"))
                binding.state.setTextColor(Color.parseColor("#1A75FF"))
            }
            "승인거절" -> {
                stateBackground.setColor(Color.parseColor("#FFE8EC"))
                binding.state.setTextColor(Color.parseColor("#FF1A43"))
            }
            "승인요청" -> {
                stateBackground.setColor(Color.parseColor("#FFF6E8"))
                binding.state.setTextColor(Color.parseColor("#FFA31A"))
            }
            else -> {
                stateBackground.setColor(Color.parseColor("#EDEDED"))
                binding.state.setTextColor(Color.parseColor("#979797"))
            }
        }
    }


    private fun getDetail(id: Int) {
        employmentService = RetrofitBuilder.getInstance().create(EmploymentService::class.java)
        val employmentManagementDetail = employmentService.getEnterPriseJobDetail(id)

        employmentManagementDetail.enqueue(object : Callback<SelectJobEnterpriseDetailOutputDto> {
            override fun onResponse(
                call: Call<SelectJobEnterpriseDetailOutputDto>,
                response: Response<SelectJobEnterpriseDetailOutputDto>
            ) {

                Log.d("employmentDetail", "code : " + response.code())
                Log.d("employmentDetail", "message : " + response.message())

                if (response.isSuccessful) {
                    Log.d("employmentDetail", "isSuccessful.. body -> : " + response.body())
                    response.body()?.let { setData(it.data) }
                } else {
                    Log.d("employmentDetail", "code : " + response.code())
                }
            }

            override fun onFailure(call: Call<SelectJobEnterpriseDetailOutputDto>, t: Throwable) {
                Log.e("employmentDetail", "통신 실패" + t.localizedMessage)
            }

        })
    }

    private fun setData(data : JobEnterpriseDetailOutputDto){
        binding.title.text = data.title
        binding.nameValue.text = data.name
        binding.bizrNoValue.text = data.bizrNo
        binding.ceoValue.text = data.ceo
        binding.addressValue.text = data.address
        binding.sectorValue.text = data.sector
        binding.quaternionValue.text = data.quaternion
    }
}