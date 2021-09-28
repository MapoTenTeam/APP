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