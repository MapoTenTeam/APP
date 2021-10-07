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
import com.mapo.mapoten.data.employment.*
import com.mapo.mapoten.databinding.FragmentEmployment0102Binding
import com.mapo.mapoten.service.EmploymentService
import com.mapo.mapoten.ui.adapter.GeneralEmploymentPostingAdapter
import com.mapo.mapoten.ui.adapter.SpinnerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class Employment_01_02 : Fragment() {
    lateinit var binding: FragmentEmployment0102Binding
    private lateinit var adapter: GeneralEmploymentPostingAdapter
    private var resultDataList = mutableListOf<GeneralEmpPostingDTO>()
    private lateinit var spinnerAdapterCareer: SpinnerAdapter
    private val listOfCareer = ArrayList<SpinnerModel>()
    private lateinit var spinnerAdapterJob: SpinnerAdapter
    private val listOfJob = ArrayList<SpinnerModel>()
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

        binding = FragmentEmployment0102Binding.inflate(inflater, container, false)
        val view = binding.root


        adapter = GeneralEmploymentPostingAdapter(this.requireContext())
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
        getAllPosting(1,"")

        listOfCareer.clear()
        listOfJob.clear()
        listOfPlace.clear()
//        setupSpinnerCareer()
//        setupSpinnerJob()
//        setupSpinnerPlace()
    }

//    private fun setupSpinnerCareer() {
//        val careers = resources.getStringArray(R.array.employ_array_career)
//
//        for (i in careers.indices) {
//            val career = SpinnerModel(careers[i])
//            listOfCareer.add(career)
//        }
//        spinnerAdapterCareer = SpinnerAdapter(requireContext(), R.layout.item_spinner, listOfCareer)
//        binding.employCareer.adapter = spinnerAdapterCareer
//    }
//
//    private fun setupSpinnerJob() {
//        val jobs = resources.getStringArray(R.array.employ_array_job)
//
//        for (i in jobs.indices) {
//            val job = SpinnerModel(jobs[i])
//            listOfJob.add(job)
//        }
//        spinnerAdapterJob = SpinnerAdapter(requireContext(), R.layout.item_spinner, listOfJob)
//        binding.employJob.adapter = spinnerAdapterJob
//    }
//
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

        employmentService = RetrofitBuilder.getInstance().create(EmploymentService::class.java)
        val generalJobList = employmentService.getGeneralJobList(page, searchTerm)

        generalJobList.enqueue(object : Callback<GeneralJobPostingResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GeneralJobPostingResponse>,
                response: Response<GeneralJobPostingResponse>
            ) {
                Log.d("employmentGeneral", "code : " + response.code())
                Log.d("employmentGeneral", "message : " + response.message())

                if (response.isSuccessful) {
                    if (page == 1) {
                        resultDataList = response.body()!!.data
                    } else {
                        for (item in response.body()!!.data) {
                            resultDataList.add(item)
                        }
                    }
                    endPostingCount = response.body()!!.count
                    Log.d("employmentGeneral", "resultDataList' size : " + resultDataList.size)
                    Log.d("employmentDetail", "resultDataList : $resultDataList")

                    if (resultDataList.size > 0) {

                        thread(start = true) {
                            Thread.sleep(300)

                            requireActivity().runOnUiThread {
                                loading(false)
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

