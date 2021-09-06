package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.mapo.mapoten.R
import com.mapo.mapoten.data.BannerItem
import com.mapo.mapoten.databinding.FragmentHome01Binding
import com.mapo.mapoten.ui.adapter.HomeViewPagerAdapter

class Home_01 : Fragment() {
    private lateinit var binding: FragmentHome01Binding
    private var bannerPosition = Int.MAX_VALUE/2
    private var homeBannerHandler = HomeBannerHandler()
    // 3초 간격
    private val intervalTime = 3000L
    // 자동 스크롤 컨트롤
    inner class HomeBannerHandler: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                binding.homeViewPager2.setCurrentItem(++bannerPosition, true) // 다음 포지션으로 이동
                autoScrollStart(intervalTime)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHome01Binding.inflate(inflater,container,false)
        val view = binding.root

        // 뷰페이저2 연결
        binding.homeViewPager2.apply {
            adapter = HomeViewPagerAdapter(ViewData())
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setCurrentItem(bannerPosition, false) // 배너 시작 위치
        }

        initViewPager2()
        return view
    }

    private fun initViewPager2() {
        binding.homeViewPager2.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when(position) {
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                    }
                }
            })
        }
    }
    // 자동 스크롤 시작
    private fun autoScrollStart(intervalTime: Long) {
        homeBannerHandler.removeMessages(0)
        homeBannerHandler.sendEmptyMessageDelayed(0, intervalTime)
    }
    // 자동 스크롤 정지
    private fun autoScrollStop() {
        homeBannerHandler.removeMessages(0) // 핸들러 중지
    }

    private fun ViewData() : ArrayList<BannerItem> {
        val list = arrayListOf<BannerItem>()
        return list.apply {
            add(BannerItem(R.drawable.home_banner_01, "마포구청", "2021년 마포형 청년일자리 사업\n" +
                    "참여자 모집"))
            add(BannerItem(R.drawable.home_banner_01, "마포청년나루", "2021년 마포청년 모집"))
            add(BannerItem(R.drawable.home_banner_01, "마포구문화회관", "2021년 마포 기자단 모집"))
            add(BannerItem(R.drawable.home_banner_01, "마포마포","모집중"))
            add(BannerItem(R.drawable.home_banner_01, "마포구","2명 모집중"))
        }
    }
    // 홈 화면으로 돌아왔을때 자동스크롤 다시 시작
    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }
    // 다른 화면 전환시 자동스크롤 정지
    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }
}