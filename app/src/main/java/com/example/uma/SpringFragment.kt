package com.example.seasonsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class SpringFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spring, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = SeasonPagerAdapter(this, "spring")
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // 초기 상태를 투명하게 설정
        viewPager.alpha = 0f

        // ViewPager2 컨텐츠만 페이드인 애니메이션 적용
        viewPager.post {
            viewPager.animate().alpha(1f).duration = 800
        }
    }
}
