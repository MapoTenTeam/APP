package com.mapo.mapoten.data.home

import com.mapo.mapoten.R

data class BannerItem(
    val homeImage: Int,
    val homeTitle: String,
    val homeContent: String
)

fun ViewData() : ArrayList<BannerItem> {
    val list = arrayListOf<BannerItem>()
    return list.apply {
        add(BannerItem(
            R.drawable.banner_image2, "2021년 마포형 청년일자리 사업 참여자 모집", "마포구청"))
        add(BannerItem(R.drawable.banner_image1, "2021년 마포청년 모집", "마포청년나루"))
        add(BannerItem(R.drawable.banner_image3, "2021년 마포 기자단 모집", "마포구문화회관"))
        add(BannerItem(R.drawable.banner_image2, "32명 앱개발 모집중","일자리 사업장"))
        add(BannerItem(R.drawable.banner_image1, "2명 디자이너 모집중","일자리 사업장"))
    }
}
