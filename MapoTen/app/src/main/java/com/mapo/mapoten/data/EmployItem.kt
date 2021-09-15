package com.mapo.mapoten.data

import com.mapo.mapoten.R

/**
 * @author hj
 * @email syk01132@gmail.com
 * @created 2021-09-08
 * @desc
 */
data class EmployData(
    val employImage: Int,
    val employTitle: String,
    val employContent: String,
    val employCountry: String,
    val employCareer: String,
    val employJob: String
)

val EmployDataList = arrayListOf<EmployData>()

fun EmployData() : ArrayList<EmployData> {
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포구청","안드로이드 앱개발 모집","성산1동","신입","개발"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포청년나루","청년 창업지원 교육생 모집","연남동","신입","경영지원"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"합정동주민센터","마케팅 정규직 채용","합정동","경력","마케팅"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포장애인복지관","사회복지사 채용","아현동","경력무관","고객서비스"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포복지센터","마포복지센터 사회복지사 채용","합정동","경력무관","고객서비스"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"성산1동주민센터","계약직 사무보조 모집","성산1동","경력","경영지원"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포문화회관","문화회관 관리자 모집","도화동","경력","공간운영"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포구일자리지원과","일자리 지원 컨설팅","공덕동","신입","컨텐츠"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포구회관","함께할 마포구민 모집","연남동","경력무관","기획"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포구민지원센터","마포지원센터 모집","서강동","신입","개발"))
    EmployDataList.add(EmployData(
        R.drawable.mapo_logo,"마포안전과","안전과 모집중","대흥동","신입","기획"))
    return EmployDataList
}

