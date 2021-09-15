package com.mapo.mapoten.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
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
class EmploymentRecyclerViewAdapter(private val employList: ArrayList<EmployItem>)
    : RecyclerView.Adapter<EmploymentRecyclerViewAdapter.EmployHolder>(), Filterable {

    private val unFilteredList = employList
    private var filteredList = employList

    inner class EmployHolder(rowRoot: View) : RecyclerView.ViewHolder(rowRoot) {
        val mImage : ImageView = rowRoot.findViewById(R.id.emp_item_image)
        val mTitle : TextView = rowRoot.findViewById(R.id.emp_item_title)
        val mContent : TextView = rowRoot.findViewById(R.id.emp_item_content)
        val mCountry : TextView = rowRoot.findViewById(R.id.emp_item_country)
        val mCareer : TextView = rowRoot.findViewById(R.id.emp_item_career)
        val mJob : TextView = rowRoot.findViewById(R.id.emp_item_job)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmploymentRecyclerViewAdapter.EmployHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_employment_list,parent,false)
        return EmployHolder(view)
    }

    override fun onBindViewHolder(holder: EmploymentRecyclerViewAdapter.EmployHolder, position: Int) {
        val employData = filteredList[position]
        with(holder) {
            mImage.setImageResource(employData.employImage)
            mTitle.text = employData.employTitle
            mContent.text = employData.employContent
            mCountry.text = employData.employCountry
            mCareer.text = employData.employCareer
            mJob.text = employData.employJob
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteringList = ArrayList<EmployItem>()
                val charString = constraint.toString()

                filteredList = if (charString.isEmpty()) {
                    unFilteredList
                } else {
                    for (item in unFilteredList) {
                        filteringList.add(item)
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<EmployItem>
                notifyDataSetChanged()
            }
        }
    }

}