package com.mapo.mapoten.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.employment.EmploymentResponse
import com.mapo.mapoten.data.employment.SelectJobEnterpriseDetailOutputDto
import com.mapo.mapoten.data.employment.SelectJobEnterpriseItem
import com.mapo.mapoten.service.EmploymentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmploymentPostingAdapter(private val context: Context) :
    RecyclerView.Adapter<EmploymentPostingAdapter.ViewHolder>() {

    var data: MutableList<SelectJobEnterpriseItem> = ArrayList()

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

        private val title: TextView = view.findViewById(R.id.title)
        private val state: TextView = view.findViewById(R.id.state)
        private val comments: TextView = view.findViewById(R.id.comments)
        private val createAt: TextView = view.findViewById(R.id.createAt)
        private val requestDate: TextView = view.findViewById(R.id.requestDate)

        @SuppressLint("SetTextI18n")
        fun bind(item: SelectJobEnterpriseItem) {
            title.text = if (item.title.length > 14) {
                item.title.substring(0, 14) + "..."
            } else item.title
            state.text = item.jobStat
            comments.text = if (item.comments != null) "??????: " + item.comments else ""
            createAt.text = "?????????: " + item.createAt.substring(0, 19)
            requestDate.text = "?????? ?????????: " + item.createAt.substring(0, 19)

            val stateBackground: GradientDrawable = state.background as GradientDrawable

            when (state.text) {
                "????????????" -> {
                    stateBackground.setColor(Color.parseColor("#E8F1FF"))
                    state.setTextColor(Color.parseColor("#1A75FF"))
                }
                "????????????" -> {
                    stateBackground.setColor(Color.parseColor("#FFE8EC"))
                    state.setTextColor(Color.parseColor("#FF1A43"))
                }
                "????????????" -> {
                    stateBackground.setColor(Color.parseColor("#FFF6E8"))
                    state.setTextColor(Color.parseColor("#FFA31A"))
                }
                else -> {
                    stateBackground.setColor(Color.parseColor("#EDEDED"))
                    state.setTextColor(Color.parseColor("#979797"))
                }
            }

            itemView.setOnClickListener {
                val bundle = bundleOf("jobId" to item.jobId, "state" to item.jobStat, "comments" to item.comments)
                Log.d("employmentDetail", "item.jobId : " + item.jobId)

                Navigation.findNavController(itemView)
                    .navigate(R.id.businessAccountEmploymentDetail_01, bundle)
            }

        }
    }


}