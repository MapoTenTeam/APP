package com.mapo.mapoten.ui.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mapo.mapoten.R
import com.mapo.mapoten.data.BannerItem

class HomeViewPagerAdapter(private val bannerItemList:ArrayList<BannerItem>)
    : RecyclerView.Adapter<HomeViewPagerAdapter.BannerHolder>() {

    inner class BannerHolder(rowRoot: View) : RecyclerView.ViewHolder(rowRoot) {
        val bannerImage : ImageView = rowRoot.findViewById(R.id.iv_bannerImage)
        val bannerTitle : TextView = rowRoot.findViewById(R.id.tv_bannerTitle)
        val bannerContent : TextView = rowRoot.findViewById(R.id.tv_bannerContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_home_banner,parent,false)
        return BannerHolder(view)
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        val bannerData = bannerItemList[position%5]
        with(holder) {
            bannerImage.setImageResource(bannerData.homeImage)
            bannerTitle.text = bannerData.homeTitle
            bannerContent.text = bannerData.homeContent

            itemView.setOnClickListener {
                val bundle = bundleOf(
                    "title" to bannerData.homeTitle,
                    "content" to bannerData.homeContent)
                Navigation.findNavController(it).navigate(R.id.action_home_01_to_employment_Detail_01, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}