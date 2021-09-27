package com.mapo.mapoten.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mapo.mapoten.R
import com.mapo.mapoten.ui.data.EmploymentPostingContents

class EmploymentPostingAdapter(private val context: Context) : RecyclerView.Adapter<EmploymentPostingAdapter.ViewHolder>() {

    var data : MutableList<EmploymentPostingContents> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.employment_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title : TextView = view.findViewById(R.id.title)
        private val state : TextView = view.findViewById(R.id.state)
        private val category : TextView = view.findViewById(R.id.category)
        private val registrationDate : TextView = view.findViewById(R.id.registrationDate)

        fun bind(item: EmploymentPostingContents) {
            title.text = item.title
            state.text = item.state
            category.text = item.category
            registrationDate.text = item.registrationDate

            val stateBackground : GradientDrawable = state.background as GradientDrawable

            when(state.text){
                "승인" -> {
                    stateBackground.setColor(Color.parseColor("#E8F1FF"))
                    state.setTextColor(Color.parseColor("#1A75FF"))
                }
                "비승인" -> {
                    stateBackground.setColor(Color.parseColor("#FFE8EC"))
                    state.setTextColor(Color.parseColor("#FF1A43"))
                }
                else -> {
                    stateBackground.setColor(Color.parseColor("#FFF6E8"))
                    state.setTextColor(Color.parseColor("#FFA31A"))
                }
            }

            itemView.setOnClickListener {
                val bundle = bundleOf("state" to state.text)
                Navigation.findNavController(itemView).navigate(R.id.businessAccountEmploymentDetail_01, bundle)
            }

        }
    }
}