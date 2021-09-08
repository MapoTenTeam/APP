package com.mapo.mapoten.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapo.mapoten.R
import com.mapo.mapoten.data.EmployItem

/**
 * @author hj
 * @email syk01132@gmail.com
 * @created 2021-09-08
 * @desc
 */
class EmploymentRecyclerViewAdapter(private val employmentItemList: MutableList<EmployItem>)
    : RecyclerView.Adapter<EmploymentRecyclerViewAdapter.EmployHolder>() {

    inner class EmployHolder(rowRoot: View) : RecyclerView.ViewHolder(rowRoot) {
        val employImage : ImageView = rowRoot.findViewById(R.id.employment_image)
        val employContent : TextView = rowRoot.findViewById(R.id.employment_content)
        val employTitle : TextView = rowRoot.findViewById(R.id.employment_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_employment_list, parent, false)
        return EmployHolder(view)
    }

    override fun onBindViewHolder(holder: EmployHolder, position: Int) {
        val employData = employmentItemList[position]
        with(holder) {
            employImage.setImageResource(employData.employImage)
            employContent.text = employData.employContent
            employTitle.text = employData.employTitle
        }
    }

    override fun getItemCount(): Int {
        return employmentItemList.size
    }
}