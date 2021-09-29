package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.data.employment.EmploymentJobPostingItem
import com.mapo.mapoten.data.SpinnerModel
import com.mapo.mapoten.databinding.FragmentEmployment0101Binding
import com.mapo.mapoten.ui.adapter.PublicEmploymentPostingAdapter
import com.mapo.mapoten.ui.adapter.SpinnerAdapter

class Employment_01_01 : Fragment() {

    lateinit var binding: FragmentEmployment0101Binding
    private lateinit var adapter: PublicEmploymentPostingAdapter
    private val testList = mutableListOf<EmploymentJobPostingItem>()
    private lateinit var spinnerAdapterPlace: SpinnerAdapter
    private val listOfPlace = ArrayList<SpinnerModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmployment0101Binding.inflate(inflater, container, false)
        val view = binding.root


        adapter = PublicEmploymentPostingAdapter(this.requireContext())
        binding.jobPostingBoard.adapter = adapter
        getAllPosting()

        // init
        initialize()


        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    private fun initialize() {
        listOfPlace.clear()

        setupSpinnerPlace()

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
                    "기획 업무",
                    "기획나라",
                    "성산동",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12,
                    "기획"
                )
            )
            add(
                EmploymentJobPostingItem(
                    2,
                    "마케팅 팀 매니저 구인",
                    "우리나라 마케팅",
                    "공덕동",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12,
                    "마케팅"
                )
            )
            add(
                EmploymentJobPostingItem(
                    3,
                    "프론트 개발자 모집",
                    "마포구청(일자리 사업)",
                    "합정동",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12, "개발"
                )
            )
            add(
                EmploymentJobPostingItem(
                    4,
                    "백엔드 개발자 모집",
                    "마포구청(일자리 사업)",
                    "합정동",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12, "개발"
                )
            )
            add(
                EmploymentJobPostingItem(
                    5,
                    "PM구인",
                    "마포구청(일자리 사업)",
                    "합정동",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12, "개발"
                )
            )
            add(
                EmploymentJobPostingItem(
                    6,
                    "기획 매니저 구인",
                    "기획나라",
                    "상암동",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12,
                    "기획"
                )
            )
            add(
                EmploymentJobPostingItem(
                    7,
                    "디자이너 10명 모집중",
                    "마포구청(일자리 사업)",
                    "아현동",
                    "2021년 12월 12일",
                    "2021년 12월 30일",
                    "",
                    12, "개발"
                )
            )


            adapter.data = testList
            adapter.notifyDataSetChanged()
        }
    }
}