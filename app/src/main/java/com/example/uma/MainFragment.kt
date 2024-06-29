package com.example.seasonsapp

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.buttonSpring).setOnClickListener {
            animateButton(it, "#FFE1FE", R.anim.scale_up_center_spring, R.id.action_mainFragment_to_springFragment)
        }

        view.findViewById<ImageButton>(R.id.buttonSummer).setOnClickListener {
            animateButton(it, "#FFF7E1", R.anim.scale_up_center_summer, R.id.action_mainFragment_to_summerFragment)
        }

        view.findViewById<ImageButton>(R.id.buttonAutumn).setOnClickListener {
            animateButton(it, "#FFDCB9", R.anim.scale_up_center_autumn, R.id.action_mainFragment_to_autumnFragment)
        }

        view.findViewById<ImageButton>(R.id.buttonWinter).setOnClickListener {
            animateButton(it, "#D5F5FF", R.anim.scale_up_center_winter, R.id.action_mainFragment_to_winterFragment)
        }
    }

    private fun animateButton(button: View, color: String, animResId: Int, actionId: Int) {
        // Scale and translate animation
        val scaleUp = AnimationUtils.loadAnimation(requireContext(), animResId)
        button.startAnimation(scaleUp)

        // Background color animation
        val colorFrom = ContextCompat.getColor(requireContext(), android.R.color.white)
        val colorTo = Color.parseColor(color)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 1000 // Duration for color transition
        colorAnimation.addUpdateListener { animator ->
            view?.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        // Fade out other icons
        fadeOutOtherIcons(button)

        // Start fade out animation after scale up animation ends
        scaleUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                val fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
                button.startAnimation(fadeOut)
                fadeOut.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}

                    override fun onAnimationEnd(animation: Animation?) {
                        // Navigate to the respective fragment after fade out
                        findNavController().navigate(actionId)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun fadeOutOtherIcons(except: View) {
        val fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        val parent = except.parent as ViewGroup
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child != except) {
                child.startAnimation(fadeOut)
                child.visibility = View.INVISIBLE
            }
        }
    }
}
