package com.example.storymedia

import android.graphics.Rect
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MoveViewTouchListener : View.OnTouchListener {

    private var mGestureDetector: GestureDetector? = null
    private var mTextView: View? = null
    private var mImageView: View? = null
    private var actionTouch: ((Boolean, Boolean) -> Unit)? = null

    var outRect: Rect = Rect()
    var location = IntArray(2)


    constructor(textView: View, imageView: View, listener: (Boolean, Boolean) -> Unit) {
        mGestureDetector = GestureDetector(textView.context, mGestureListener)
        mTextView = textView
        mImageView = imageView
        actionTouch = listener
    }

    private fun isViewInBounds(view: View?, x: Int?, y: Int?): Boolean {
        view?.getDrawingRect(outRect)
        view?.getLocationOnScreen(location)
        outRect.offset(location[0], location[1])
        return outRect.contains(x!!, y!!)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val x = (event?.rawX)?.toInt()
        val y = (event?.rawY)?.toInt()
        when(event?.action) {
            MotionEvent.ACTION_MOVE -> {
                actionTouch?.let { it(true, false) }
            }
            MotionEvent.ACTION_UP -> {
                if(isViewInBounds(mImageView, x!!, y!!)) {
                    Log.d("action", "A view to B")
                    actionTouch?.let { it(false, true) }
                }else {
                    actionTouch?.let { it(false, false) }
                }
            }
        }
        return mGestureDetector!!.onTouchEvent(event);
    }

    private val mGestureListener: GestureDetector.OnGestureListener =
        object : SimpleOnGestureListener() {
            private var mMotionDownX = 0f
            private var mMotionDownY = 0f
            override fun onDown(e: MotionEvent): Boolean {
                mMotionDownX = e.rawX - mTextView!!.translationX
                mMotionDownY = e.rawY - mTextView!!.translationY
                Log.d(
                    "dz",
                    "RawXDown: ${e.rawX}, RawYDown: ${e.rawY}, TranslationX: ${mTextView!!.translationX} MotionDownX: $mMotionDownX, MotionDownY: $mMotionDownY\n"
                )
                return true
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                mTextView!!.translationX = e2.rawX - mMotionDownX
                mTextView!!.translationY = e2.rawY - mMotionDownY
                return true
            }
        }
}