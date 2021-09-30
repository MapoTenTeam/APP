package com.mapo.mapoten.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.employment.EmploymentJobPostingItem
import com.mapo.mapoten.data.employment.EmploymentResponse
import com.mapo.mapoten.data.employment.GeneralEmpPostingDTO
import com.mapo.mapoten.service.EmploymentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GeneralEmploymentPostingAdapter(private val context: Context) :
    RecyclerView.Adapter<GeneralEmploymentPostingAdapter.ViewHolder>() {
    var data: MutableList<GeneralEmpPostingDTO> = ArrayList()
    lateinit var employmentService: EmploymentService

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

        private val title: TextView = view.findViewById(R.id.postingTitle)
        private val companyImage: ImageView = view.findViewById(R.id.companyImage)
        private val companyName: TextView = view.findViewById(R.id.CompanyName)
        private val jobType: TextView = view.findViewById(R.id.jobType)
        private val careerType: TextView = view.findViewById(R.id.careerType)
        private val place: TextView = view.findViewById(R.id.place)
        private val date: TextView = view.findViewById(R.id.date)

        @SuppressLint("SetTextI18n")
        fun bind(item: GeneralEmpPostingDTO) {
            title.text = item.title

            companyName.text = item.companyName
            jobType.text = item.jobType
            careerType.text = item.career
            place.text = item.address
            date.text = "모집기간: " + item.startReception + " - " + item.endReception

            if(item.companyImage != null ){
                Glide.with(context).load(item.companyImage).into(companyImage)
            }else {
                companyImage.setImageResource(R.drawable.banner_image1)
            }

            itemView.setOnClickListener {
                val bundle = bundleOf("type" to 1, "jobId" to item.jobId)
                Navigation.findNavController(itemView).navigate(R.id.employment_Detail_01, bundle)
            }
        }
    }

}