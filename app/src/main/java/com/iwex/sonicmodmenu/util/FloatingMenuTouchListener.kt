package com.iwex.sonicmodmenu.util

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import kotlin.math.absoluteValue

class FloatingMenuTouchListener(
    private val layoutParams: WindowManager.LayoutParams,
    private val onViewMoveListener: (View, WindowManager.LayoutParams) -> Unit
) : View.OnTouchListener {
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var initialX = 0
    private var initialY = 0

    private val minAlpha = 0.5f
    private val maxAlpha = 1.0f
    private val slopDistance = MenuDesign.slopDistance

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> handleDown(view, motionEvent)
            MotionEvent.ACTION_UP -> handleUp(view, motionEvent)
            MotionEvent.ACTION_MOVE -> handleMove(view, motionEvent)
            MotionEvent.ACTION_CANCEL -> handleCancel(view)
        }
        return true
    }

    private fun handleDown(view:View, motionEvent: MotionEvent) {
        view.animate().alpha(minAlpha)
        initialX = layoutParams.x
        initialY = layoutParams.y
        initialTouchX = motionEvent.rawX
        initialTouchY = motionEvent.rawY
    }

    private fun handleUp(view:View, motionEvent: MotionEvent) {
        view.animate().alpha(maxAlpha)
        val deltaX = (motionEvent.rawX - initialTouchX).absoluteValue
        val deltaY = (motionEvent.rawY - initialTouchY).absoluteValue
        // Check whether it's a swipe or click event
        if (deltaX < slopDistance && deltaY < slopDistance) {
            view.performClick()
        }
    }

    private fun handleMove(view:View, motionEvent: MotionEvent) {
        val deltaX = motionEvent.rawX - initialTouchX
        val deltaY = motionEvent.rawY - initialTouchY
        layoutParams.apply {
            x = (initialX + deltaX).toInt()
            y = (initialY + deltaY).toInt()
        }
        onViewMoveListener(view, layoutParams)
    }

    private fun handleCancel(view:View) {
        view.animate().alpha(maxAlpha)
    }

}