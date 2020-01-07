package com.example.storymedia

import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View

class MoveViewTouchListener : View.OnTouchListener {

    private var mGestureDetector: GestureDetector? = null
    private var mView: View? = null
    private var actionTouch: ((Boolean) -> Unit)? = null


    constructor(view: View, listener: (Boolean) -> Unit) {
        mGestureDetector = GestureDetector(view.context, mGestureListener)
        mView = view
        actionTouch = listener
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_MOVE -> {
                actionTouch?.let { it(true) }
            }
            MotionEvent.ACTION_UP -> {
                actionTouch?.let { it(false) }
            }
        }
        return mGestureDetector!!.onTouchEvent(event);
    }

    private val mGestureListener: GestureDetector.OnGestureListener =
        object : SimpleOnGestureListener() {
            private var mMotionDownX = 0f
            private var mMotionDownY = 0f
            override fun onDown(e: MotionEvent): Boolean {
                mMotionDownX = e.rawX - mView!!.translationX
                mMotionDownY = e.rawY - mView!!.translationY
                Log.d(
                    "dz",
                    "RawXDown: ${e.rawX}, RawYDown: ${e.rawY}, TranslationX: ${mView!!.translationX} MotionDownX: $mMotionDownX, MotionDownY: $mMotionDownY\n"
                )
                return true
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                mView!!.translationX = e2.rawX - mMotionDownX
                mView!!.translationY = e2.rawY - mMotionDownY
                return true
            }
        }
}