package com.mapo.mapoten.data

import com.mapo.mapoten.R

/**
 * @author hj
 * @email syk01132@gmail.com
 * @created 2021-09-06
 * @desc
 */
data class BannerItem(
    val homeImage: Int,
    val homeTitle: String,
    val homeContent: String
)

fun ViewData() : ArrayList<BannerItem> {
    val list = arrayListOf<BannerItem>()
    return list.apply {
        add(BannerItem(
            R.drawable.home_banner_01, "마포구청", "2021년 마포형 청년일자리 사업\n" +
                "참여자 모집"))
        add(BannerItem(R.drawable.home_banner_01, "마포청년나루", "2021년 마포청년 모집"))
        add(BannerItem(R.drawable.home_banner_01, "마포구문화회관", "2021년 마포 기자단 모집"))
        add(BannerItem(R.drawable.home_banner_01, "마포마포","모집중"))
        add(BannerItem(R.drawable.home_banner_01, "마포구","2명 모집중"))
    }
}
