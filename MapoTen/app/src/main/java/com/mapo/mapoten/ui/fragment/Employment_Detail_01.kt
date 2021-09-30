package com.mapo.mapoten.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.employment.EmploymentResponse
import com.mapo.mapoten.data.employment.GeneralEmpPostingDTO
import com.mapo.mapoten.data.employment.GeneralEmpPostingDetailDTO
import com.mapo.mapoten.data.employment.GeneralJobPostingResponse
import com.mapo.mapoten.databinding.FragmentEmploymentDetail01Binding
import com.mapo.mapoten.service.EmploymentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class Employment_Detail_01 : Fragment() {
    lateinit var binding: FragmentEmploymentDetail01Binding
    private var resultDataList = mutableListOf<EmploymentResponse>()
    lateinit var employmentService: EmploymentService
    var type by Delegates.notNull<Int>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmploymentDetail01Binding.inflate(inflater, container, false)
        val view = binding.root

        type = arguments?.getInt("type")!!
        val id = arguments?.getInt("jobId")

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        when(type){
            0 -> {

            }
            1 -> {
                if (id != null) {
                    getGeneralJobPostingDetail(id)
                }
            }
            else -> {
                Log.d("detail", "no type!")
            }
        }

        return view
    }


    private fun getGeneralJobPostingDetail(id : Int) {
        employmentService = RetrofitBuilder.getInstance().create(EmploymentService::class.java)
        val generalJobList = employmentService.inquireGeneralDetailPosting(id)

        generalJobList.enqueue(object : Callback<EmploymentResponse> {
            override fun onResponse(
                call: Call<EmploymentResponse>,
                response: Response<EmploymentResponse>
            ) {

                Log.d("generalDetail", "code : " + response.code())
                Log.d("generalDetail", "message : " + response.message())

                if (response.isSuccessful) {

                    Log.d("generalDetail", "resultDataList : ${response.body()?.data}")
                    response.body()?.data?.let { setData(it) }

                } else {
                    Log.d("generalDetail", "code : " + response.code())
                }
            }

            override fun onFailure(call: Call<EmploymentResponse>, t: Throwable) {
                Log.e("generalDetail", "통신 실패" + t.localizedMessage)

            }

        })
    }

    private fun setData(result : GeneralEmpPostingDetailDTO) {
        binding.category.text = if(type === 1) "일반채용" else "공공채용"
        binding.title.text = result.title
        binding.date.text = "마감일: ${result.endReception}"

    }

}