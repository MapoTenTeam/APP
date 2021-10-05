package com.mapo.mapoten.ui.adapter

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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.employment.EmploymentJobPostingItem
import com.mapo.mapoten.data.employment.EmploymentResponse
import com.mapo.mapoten.data.employment.GeneralEmpPostingDTO
import com.mapo.mapoten.service.EmploymentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PublicEmploymentPostingAdapter(private val context: Context) :
    RecyclerView.Adapter<PublicEmploymentPostingAdapter.ViewHolder>() {

    var data: MutableList<GeneralEmpPostingDTO> = ArrayList()
    lateinit var employmentService: EmploymentService

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.public_employment_posting_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.postingTitle)
        private val name: TextView = view.findViewById(R.id.CompanyName)
        private val place: TextView = view.findViewById(R.id.place)
        private val date: TextView = view.findViewById(R.id.date)
        private val companyImage: ImageView = view.findViewById(R.id.companyImage)
        private val dDay: TextView = view.findViewById(R.id.dDay)

        fun bind(item: GeneralEmpPostingDTO) {
            title.text = item.title
            name.text = item.companyName
            place.text = item.address
            date.text = "${item.endReception.substring(0, 4)}년 ${item.endReception.substring(5, 7)}월 ${item.endReception.substring(8, 10)}일 모집마감!"
            dDay.text = getDDay(item.endReception)
            if (item.companyImage != null) {
                Glide.with(context).load(item.companyImage).transform(
                    CenterCrop(), GranularRoundedCorners(
                        32F,
                        32F, 0F,
                        0F
                    )
                ).into(companyImage)

            } else {
                companyImage.setImageResource(R.drawable.banner_image1)
            }

            itemView.setOnClickListener {

                val bundle = bundleOf("type" to 0, "jobId" to item.jobId)
                Navigation.findNavController(itemView).navigate(R.id.employment_Detail_01, bundle)
            }
        }

        fun setDateFormat(startDate: String, endDate: String): String {
            Log.d("time", "startDate : ${startDate.substring(0, 10)}")
            Log.d("time", "endDate : ${endDate.substring(0, 10)}")
            return "${startDate.substring(0, 10)} ~ ${endDate.substring(0, 10)}"
        }

        private fun getDDay(endDay: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val today = Calendar.getInstance()
            val endDate = dateFormat.parse(endDay.substring(0, 10))

            val day = (endDate.time - today.time.time) / (24 * 60 * 60 * 1000)

            return if (day.toString() == "0") {
                "D-day"
            } else {
                "D-${day}"

            }
        }
    }


}