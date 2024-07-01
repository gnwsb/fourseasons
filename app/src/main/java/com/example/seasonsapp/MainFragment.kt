package com.example.seasonsapp

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var backgroundImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundImageView = view.findViewById(R.id.main_background)

        view.findViewById<ImageButton>(R.id.buttonSpring).setOnClickListener {
            animateButton(it, R.drawable.sprb, R.anim.scale_up_center_spring, R.id.action_mainFragment_to_springFragment)
        }

        view.findViewById<ImageButton>(R.id.buttonSummer).setOnClickListener {
            animateButton(it, R.drawable.sumb, R.anim.scale_up_center_summer, R.id.action_mainFragment_to_summerFragment)
        }

        view.findViewById<ImageButton>(R.id.buttonAutumn).setOnClickListener {
            animateButton(it, R.drawable.autb, R.anim.scale_up_center_autumn, R.id.action_mainFragment_to_autumnFragment)
        }

        view.findViewById<ImageButton>(R.id.buttonWinter).setOnClickListener {
            animateButton(it, R.drawable.winb, R.anim.scale_up_center_winter, R.id.action_mainFragment_to_winterFragment)
        }
    }

    private fun animateButton(button: View, imageResId: Int, animResId: Int, actionId: Int) {
        // Scale and translate animation
        val scaleUp = android.view.animation.AnimationUtils.loadAnimation(requireContext(), animResId)
        button.startAnimation(scaleUp)

        // Background image animation
        fadeInBackgroundImage(imageResId)

        // Fade out other icons
        fadeOutOtherIcons(button)

        // Start fade out animation after scale up animation ends
        scaleUp.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation?) {}

            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                val fadeOut = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
                button.startAnimation(fadeOut)
                fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                    override fun onAnimationStart(animation: android.view.animation.Animation?) {}

                    override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                        // Navigate to the respective fragment after fade out
                        findNavController().navigate(actionId)
                    }

                    override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
                })
            }

            override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
        })
    }

    private fun fadeInBackgroundImage(imageResId: Int) {
        backgroundImageView.setImageResource(imageResId)
        backgroundImageView.visibility = View.VISIBLE

        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Float
            backgroundImageView.alpha = alpha
        }
        animator.duration = 1000 // 1 second
        animator.start()
    }

    private fun fadeOutOtherIcons(except: View) {
        val fadeOut = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
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
