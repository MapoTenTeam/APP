package com.mapo.mapoten.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.employment.EmploymentResponse
import com.mapo.mapoten.data.employment.SelectJobEnterpriseItem
import com.mapo.mapoten.data.employment.SelectJobEnterpriseOutputDto
import com.mapo.mapoten.databinding.FragmentBusinessAccount0104Binding
import com.mapo.mapoten.service.EmploymentService
import com.mapo.mapoten.ui.adapter.EmploymentPostingAdapter
import com.mapo.mapoten.ui.data.EmploymentPostingContents
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BusinessAccount_01_04 : Fragment() {

    lateinit var binding: FragmentBusinessAccount0104Binding
    private lateinit var adapter: EmploymentPostingAdapter
    private val dataList = mutableListOf<SelectJobEnterpriseItem>()
    private var resultDataList = mutableListOf<SelectJobEnterpriseItem>()
    lateinit var employmentService: EmploymentService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBusinessAccount0104Binding.inflate(inflater, container, false)
        val view = binding.root

        adapter = EmploymentPostingAdapter(this.requireContext())
        binding.employmentPostingBoard.adapter = adapter

        // test
        getAllPosting()
        binding.allStates.isChecked = true

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.filter.setOnCheckedChangeListener { _, position ->

            dataList.clear()
            when (position) {
                R.id.allStates -> {
                    getAllPosting()
                }
                R.id.request -> {
                    getPosting("승인요청")
                }
                R.id.approval -> {
                    getPosting("승인완료")
                }
                R.id.reject -> {
                    getPosting("승인거절")
                }
                else -> {
                    getAllPosting()
                }
            }

        }

        binding.refreshLayout.setOnRefreshListener {

            when (binding.filter.checkedRadioButtonId) {
                R.id.allStates -> {
                    getAllPosting()
                }
                R.id.request -> {
                    getPosting("승인요청")
                }
                R.id.approval -> {
                    getPosting("승인완료")
                }
                R.id.reject -> {
                    getPosting("승인거절")
                }
                else -> {
                    getAllPosting()
                }
            }
            binding.refreshLayout.isRefreshing = false
        }

        return view
    }

    private fun getAllPosting() {

        employmentService = RetrofitBuilder.getInstance().create(EmploymentService::class.java)
        val inquireGeneralDetailPosting = employmentService.getEnterpriseJobList()

        inquireGeneralDetailPosting.enqueue(object : Callback<SelectJobEnterpriseOutputDto> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<SelectJobEnterpriseOutputDto>,
                response: Response<SelectJobEnterpriseOutputDto>
            ) {

                Log.d("employmentDetail", "code : " + response.code())
                Log.d("employmentDetail", "message : " + response.message())

                if (response.isSuccessful) {

                    resultDataList = response.body()!!.data

                    Log.d("employmentDetail", "resultDataList : $resultDataList")

                    if (resultDataList.size > 0) {
                        binding.emptyBoardGuide.visibility = View.GONE
                        binding.refreshLayout.visibility = View.VISIBLE
                        adapter.data = resultDataList
                        adapter.notifyDataSetChanged()

                    } else {
                        binding.emptyBoardGuide.visibility = View.VISIBLE
                        binding.refreshLayout.visibility = View.GONE
                    }


                } else {
                    Log.d("employmentDetail", "code : " + response.code())
                    binding.emptyBoardGuide.text = "관리자에게 문의하여 주세요..."
                    binding.emptyBoardGuide.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<SelectJobEnterpriseOutputDto>, t: Throwable) {
                Log.e("employmentDetail", "통신 실패" + t.localizedMessage)
                binding.emptyBoardGuide.text = "통신실패 : 관리자에게 문의하여 주세요."
                binding.emptyBoardGuide.visibility = View.VISIBLE

            }

        })
    }

    private fun getPosting(jobStat: String) {

        for (i: Int in 0 until resultDataList.size) {
            if (resultDataList[i].jobStat == jobStat) {

                dataList.add(resultDataList[i])
            }
        }
        adapter.data = dataList
        adapter.notifyDataSetChanged()

    }

}