package com.deniscosta.glub.ui.splash

import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.deniscosta.glub.R
import com.deniscosta.glub.ui.main.OfferListActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.android.ext.android.inject


class SplashScreen : AppCompatActivity() {

    val viewModel : SplashScreenViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x

        viewModel.state.observe(this, Observer {

            when(it){
                is AppInitiated -> {
                    initViews(width.toFloat())
                }
                is Waiting -> {
                    initAnimations(width.toFloat())
                }
                is RedirectToHome -> {
                    startActivity(OfferListActivity.newIntent(this))
                    finish()
                }
             }

        })

    }

    private fun initViews(width : Float){

        name.translationX = width
        role.translationX = width
        email.translationX = width
    }

    private fun initAnimations(width : Float){

        name.animate()
            .x(width/2 - getViewWidth(name)/2)
            .alpha(1f)
            .setDuration(600)
            .setInterpolator(DecelerateInterpolator())
            .setInterpolator(AnticipateOvershootInterpolator())
            .start()

        role.animate()
            .setStartDelay(100)
            .x(width/2 - getViewWidth(role)/2)
            .alpha(1f)
            .setDuration(600)
            .setInterpolator(DecelerateInterpolator())
            .setInterpolator(AnticipateOvershootInterpolator())
            .start()

        email.animate()
            .setStartDelay(200)
            .x(width/2 - getViewWidth(email)/2)
            .alpha(1f)
            .setDuration(600)
            .setInterpolator(DecelerateInterpolator())
            .setInterpolator(AnticipateOvershootInterpolator())
            .start()


    }

    private fun getViewWidth(view : View) : Int{
        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return view.measuredWidth
    }

}