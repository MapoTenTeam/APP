package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mapo.mapoten.R
import com.mapo.mapoten.data.EmployData
import com.mapo.mapoten.data.EmployItem
import com.mapo.mapoten.databinding.FragmentEmployment0101Binding
import com.mapo.mapoten.ui.adapter.EmploymentRecyclerViewAdapter

class Employment_01_01 : Fragment() {
    private lateinit var binding: FragmentEmployment0101Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEmployment0101Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.empRecyclerView.adapter = EmploymentRecyclerViewAdapter(EmployData())
        binding.empRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}