package com.mapo.mapoten.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.SpinnerModel
import com.mapo.mapoten.data.employment.GeneralEmpPostingDTO
import com.mapo.mapoten.data.employment.GeneralJobPostingResponse
import com.mapo.mapoten.databinding.FragmentEmployment0101Binding
import com.mapo.mapoten.service.EmploymentService
import com.mapo.mapoten.ui.adapter.PublicEmploymentPostingAdapter
import com.mapo.mapoten.ui.adapter.SpinnerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class Employment_01_01 : Fragment() {

    lateinit var binding: FragmentEmployment0101Binding
    private lateinit var adapter: PublicEmploymentPostingAdapter
    private var resultDataList = mutableListOf<GeneralEmpPostingDTO>()
    private lateinit var spinnerAdapterPlace: SpinnerAdapter
    private val listOfPlace = ArrayList<SpinnerModel>()
    lateinit var employmentService: EmploymentService

    private var postingCount = 1
    private var endPostingCount = 0
    private var searchTerm = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmployment0101Binding.inflate(inflater, container, false)
        val view = binding.root


        adapter = PublicEmploymentPostingAdapter(this.requireContext())
        binding.jobPostingBoard.adapter = adapter


        // init
        initialize()

        binding.searchBtn.setOnClickListener {
            searchTerm = binding.searchText.text.toString()
            getAllPosting(1, searchTerm)
        }

        binding.refreshLayout.setOnRefreshListener {
            postingCount += 1
            if (endPostingCount > 0 && (endPostingCount / 12 + 1) >= postingCount) {
                binding.refreshLayout.isRefreshing = true
                getAllPosting(postingCount, searchTerm)
            }
            binding.refreshLayout.isRefreshing = false
        }


        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    private fun initialize() {

        loading(true)
        getAllPosting(1, "")

        listOfPlace.clear()
        //setupSpinnerPlace()

    }

//    private fun setupSpinnerPlace() {
//        val places = resources.getStringArray(R.array.employ_array_country)
//
//        for (i in places.indices) {
//            val place = SpinnerModel(places[i])
//            listOfPlace.add(place)
//        }
//        spinnerAdapterPlace = SpinnerAdapter(requireContext(), R.layout.item_spinner, listOfPlace)
//        binding.placeOfWork.adapter = spinnerAdapterPlace
//    }


    private fun loading(isLoading: Boolean) {
        if (isLoading) binding.loading.visibility = View.VISIBLE
        else binding.loading.visibility = View.GONE
    }

    private fun getAllPosting(page: Int, searchTerm: String) {
        Log.d("employmentGeneral", "resultDataList' size : " + resultDataList.size)
        employmentService = RetrofitBuilder.getInstance().create(EmploymentService::class.java)
        val generalJobList = employmentService.getPublicJobList(page, searchTerm)

        generalJobList.enqueue(object : Callback<GeneralJobPostingResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GeneralJobPostingResponse>,
                response: Response<GeneralJobPostingResponse>
            ) {

                Log.d("employmentGeneral", "code : " + response.code())
                Log.d("employmentGeneral", "message : " + response.message())

                if (response.isSuccessful) {
                    binding.loading.visibility = View.GONE
                    if (page == 1) {
                        resultDataList = response.body()!!.data
                    } else {
                        for (item in response.body()!!.data) {
                            resultDataList.add(item)
                        }
                    }
                    endPostingCount = response.body()!!.count
                    if (resultDataList.size > 0) {

                        thread(start = true) {
                            Thread.sleep(300)

                            requireActivity().runOnUiThread {
                                loading(false)
                                Log.d(
                                    "employmentGeneral",
                                    "all resultDataList's size : " + resultDataList.size
                                )
                                adapter.data = resultDataList
                                adapter.notifyDataSetChanged()
                            }
                        }

                    } else {
                    }

                } else {
                    Log.d("employmentGeneral", "code : " + response.code())
                }
            }

            override fun onFailure(call: Call<GeneralJobPostingResponse>, t: Throwable) {
                Log.e("employmentGeneral", "통신 실패" + t.localizedMessage)

            }

        })
    }
}