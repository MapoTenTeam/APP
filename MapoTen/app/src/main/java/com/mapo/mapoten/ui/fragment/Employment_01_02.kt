package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mapo.mapoten.data.EmploymentJobPostingItem
import com.mapo.mapoten.databinding.FragmentEmployment0102Binding
import com.mapo.mapoten.ui.adapter.GeneralEmploymentPostingAdapter

class Employment_01_02 : Fragment() {
    lateinit var binding: FragmentEmployment0102Binding
    private lateinit var adapter: GeneralEmploymentPostingAdapter
    private val testList = mutableListOf<EmploymentJobPostingItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmployment0102Binding.inflate(inflater, container, false)
        val view = binding.root


        adapter = GeneralEmploymentPostingAdapter(this.requireContext())
        binding.jobPostingBoard.adapter = adapter
        getAllPosting()


        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }


        return view
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