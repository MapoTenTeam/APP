package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.data.EmploymentJobPostingItem
import com.mapo.mapoten.data.SpinnerModel
import com.mapo.mapoten.databinding.FragmentEmployment0101Binding
import com.mapo.mapoten.ui.adapter.EmploymentPostingAdapter
import com.mapo.mapoten.ui.adapter.GeneralEmploymentPostingAdapter
import com.mapo.mapoten.ui.adapter.SpinnerAdapter
import com.mapo.mapoten.ui.data.EmploymentPostingContents

class Employment_01_01 : Fragment() {

    lateinit var binding: FragmentEmployment0101Binding
    private lateinit var adapter: GeneralEmploymentPostingAdapter
    private val testList = mutableListOf<EmploymentJobPostingItem>()
    private lateinit var spinnerAdapterCareer: SpinnerAdapter
    private val listOfCareer = ArrayList<SpinnerModel>()
    private lateinit var spinnerAdapterJob: SpinnerAdapter
    private val listOfJob = ArrayList<SpinnerModel>()
    private lateinit var spinnerAdapterPlace: SpinnerAdapter
    private val listOfPlace = ArrayList<SpinnerModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmployment0101Binding.inflate(inflater, container, false)
        val view = binding.root


        adapter = GeneralEmploymentPostingAdapter(this.requireContext())
        binding.jobPostingBoard.adapter = adapter
        getAllPosting()

        // spinner settings
        setupSpinnerCareer()
        setupSpinnerJob()
        setupSpinnerPlace()

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }


        return view
    }


    private fun setupSpinnerCareer() {
        val careers = resources.getStringArray(R.array.employ_array_career)

        for (i in careers.indices) {
            val career = SpinnerModel(careers[i])
            listOfCareer.add(career)
        }
        spinnerAdapterCareer = SpinnerAdapter(requireContext(), R.layout.item_spinner, listOfCareer)
        binding.employCareer.adapter = spinnerAdapterCareer
    }

    private fun setupSpinnerJob() {
        val jobs = resources.getStringArray(R.array.employ_array_job)

        for (i in jobs.indices) {
            val job = SpinnerModel(jobs[i])
            listOfJob.add(job)
        }
        spinnerAdapterJob = SpinnerAdapter(requireContext(), R.layout.item_spinner, listOfJob)
        binding.employJob.adapter = spinnerAdapterJob
    }

    private fun setupSpinnerPlace() {
        val places = resources.getStringArray(R.array.employ_array_country)

        for (i in places.indices) {
            val place = SpinnerModel(places[i])
            listOfPlace.add(place)
        }
        spinnerAdapterPlace = SpinnerAdapter(requireContext(), R.layout.item_spinner, listOfPlace)
        binding.placeOfWork.adapter = spinnerAdapterPlace
    }

    private fun getAllPosting() {
        testList.apply {
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            add(
                EmploymentJobPostingItem(
                    1,
                    "마포구청 구내식당 영양사 모집",
                    "마포구청",
                    "서울특별시 마포구 성산로 22",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12
                )
            )
            adapter.data = testList
            adapter.notifyDataSetChanged()
        }
    }
}