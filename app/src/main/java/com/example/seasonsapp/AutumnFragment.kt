package com.example.seasonsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class AutumnFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_autumn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = SeasonPagerAdapter(this, "autumn")
        //viewPager2의 방향을 수직으로 설정
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // 초기 상태를 투명하게 설정
        viewPager.alpha = 0f

        // ViewPager2 컨텐츠만 페이드인 애니메이션 적용
        viewPager.post {
            viewPager.animate().alpha(1f).duration = 400
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 1) { // 두 번째 탭에서 세 번째 탭으로 스크롤할 때
                    val seasonNoteFragment = childFragmentManager.findFragmentByTag("f2") as? SeasonNoteFragment
                    seasonNoteFragment?.setSeasonBackgroundAlpha(positionOffset)
                }
            }
        })
    }
}
