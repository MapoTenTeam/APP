package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentBusinessAccount0104Binding
import com.mapo.mapoten.ui.adapter.EmploymentPostingAdapter
import com.mapo.mapoten.ui.data.EmploymentPostingContents


class BusinessAccount_01_04 : Fragment() {

    lateinit var binding : FragmentBusinessAccount0104Binding
    private lateinit var adapter : EmploymentPostingAdapter
    private val testList = mutableListOf<EmploymentPostingContents>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBusinessAccount0104Binding.inflate(inflater, container, false)
        val view = binding.root

        adapter  = EmploymentPostingAdapter(this.requireContext())
        binding.employmentPostingBoard.adapter = adapter

        // test
        getAllPosting()
        binding.allStates.isChecked = true

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.filter.setOnCheckedChangeListener { radioGroup, position ->


            testList.clear()
            when(position){
                R.id.allStates -> {
                    getAllPosting()
                }
                R.id.judging -> {
                    getJudgingPosting()
                }
                R.id.approval -> {
                    getApprovalPosting()
                }
                R.id.reject -> {
                    getRejectPosting()
                }
                else -> {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()}
            }

        }
        return view
    }

    private fun getAllPosting() {
        testList.apply {
            add( EmploymentPostingContents("채용공고제목", "공공일자리","승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","비승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","대기", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "공공일자리","승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","대기", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","비승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","대기", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","대기", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "공공일자리","비승인", "등록일: 2021년 08월 31일 17시 34분"))
            adapter.data = testList
            adapter.notifyDataSetChanged()
        }
    }

    private fun getRejectPosting() {
        testList.apply {
            add( EmploymentPostingContents("채용공고제목", "일반일자리","비승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","비승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "공공일자리","비승인", "등록일: 2021년 08월 31일 17시 34분"))
            adapter.data = testList
            adapter.notifyDataSetChanged()
        }
    }

    private fun getApprovalPosting() {
        testList.apply {
            add( EmploymentPostingContents("채용공고제목", "공공일자리","승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","승인", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "공공일자리","승인", "등록일: 2021년 08월 31일 17시 34분"))
            adapter.data = testList
            adapter.notifyDataSetChanged()
        }
    }

    private fun getJudgingPosting() {
        testList.apply {
            add( EmploymentPostingContents("채용공고제목", "일반일자리","대기", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","대기", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "공공일자리","대기", "등록일: 2021년 08월 31일 17시 34분"))
            add( EmploymentPostingContents("채용공고제목", "일반일자리","대기", "등록일: 2021년 08월 31일 17시 34분"))
            adapter.data = testList
            adapter.notifyDataSetChanged()
        }
    }
}