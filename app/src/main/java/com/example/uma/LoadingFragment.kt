package com.example.seasonsapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class LoadingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 로딩 화면 페이드 인
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        view.startAnimation(fadeIn)

        // 일정 시간 후 페이드 아웃 시작
        Handler(Looper.getMainLooper()).postDelayed({
            val fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    // 페이드 아웃이 끝난 후 메인 화면으로 전환
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.loadingFragment, true) // 백 스택을 비우도록 설정
                        .setEnterAnim(R.anim.fade_in)
                        .setPopExitAnim(R.anim.fade_out)
                        .build()
                    findNavController().navigate(R.id.action_loadingFragment_to_mainFragment, null, navOptions)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            view.startAnimation(fadeOut)
        }, 2000) // 2초 후 페이드 아웃 시작
    }
}
