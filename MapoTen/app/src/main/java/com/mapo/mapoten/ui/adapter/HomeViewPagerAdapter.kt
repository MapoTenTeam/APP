package com.mapo.mapoten.ui.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapo.mapoten.R
import com.mapo.mapoten.data.BannerItem

/**
 * @author hj
 * @email syk01132@gmail.com
 * @created 2021-09-06
 * @desc
 */
class HomeViewPagerAdapter(private val bannerItemList:ArrayList<BannerItem>)
    : RecyclerView.Adapter<HomeViewPagerAdapter.BannerHolder>() {

    inner class BannerHolder(rowRoot: View) : RecyclerView.ViewHolder(rowRoot) {
        val bannerImage : ImageView = rowRoot.findViewById(R.id.iv_bannerImage)
        val bannerName : TextView = rowRoot.findViewById(R.id.tv_bannerName)
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
            bannerName.text = bannerData.homeName
            bannerContent.text = bannerData.homeContent
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}