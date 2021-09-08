package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mapo.mapoten.R
import com.mapo.mapoten.data.EmployItem
import com.mapo.mapoten.databinding.FragmentEmployment0101Binding
import com.mapo.mapoten.ui.adapter.EmploymentRecyclerViewAdapter

class Employment_01_01 : Fragment() {
    private lateinit var binding: FragmentEmployment0101Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEmployment0101Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.employRecyclerView.apply {
            adapter = EmploymentRecyclerViewAdapter(employData())
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }

    private fun employData(): MutableList<EmployItem> {
        val list = mutableListOf<EmployItem>()
        return list.apply {
            add(EmployItem(R.drawable.mapo_logo,"영상컨텐츠","마포구청"))
            add(EmployItem(R.drawable.mapo_logo,"자바컨텐츠","마포구"))
            add(EmployItem(R.drawable.mapo_logo,"코틀린컨텐츠","마포구구"))
            add(EmployItem(R.drawable.mapo_logo,"리액트컨텐츠","마포"))
            add(EmployItem(R.drawable.mapo_logo,"디자인컨텐츠","마포텐"))
        }
    }
}