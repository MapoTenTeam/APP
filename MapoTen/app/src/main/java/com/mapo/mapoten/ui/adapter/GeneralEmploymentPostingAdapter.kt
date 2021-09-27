package com.mapo.mapoten.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import com.mapo.mapoten.R
import com.mapo.mapoten.data.EmploymentJobPostingItem
import com.mapo.mapoten.ui.data.EmploymentPostingContents

class GeneralEmploymentPostingAdapter(private val context: Context) : RecyclerView.Adapter<GeneralEmploymentPostingAdapter.ViewHolder>() {
    var data : MutableList<EmploymentJobPostingItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.employment_posting_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title : TextView = view.findViewById(R.id.postingTitle)
        private val name : TextView = view.findViewById(R.id.CompanyName)
        private val registrationDate : TextView = view.findViewById(R.id.registrationDate)

        fun bind(item: EmploymentJobPostingItem) {
            title.text = item.title
            name.text = item.name
            registrationDate.text = item.startReception
        }
    }
}