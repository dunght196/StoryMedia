package com.example.storymedia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        KeyboardUtils.hideKeyBoardWhenClickOutSide(constrainLayout, this)
        iv_story.setImageResource(R.drawable.nhatan)


        tv_create.setOnClickListener {
            edt_content.visibility = View.VISIBLE
            edt_content.requestFocus()
            edt_content.isFocusableInTouchMode = true
            KeyboardUtils.showKeyboard(this)
        }

        tv_done.setOnClickListener {
            val textContent = TextView(this)
            textContent.text = edt_content.text.toString().trim()
            textContent.setTextColor(resources.getColor(R.color.colorWhile))
            textContent.setTextSize(20f)
            textContent.id = View.generateViewId()

            constrainLayout.addView(textContent)

            val constrainSet = ConstraintSet()
            constrainSet.clone(constrainLayout)
            constrainSet.connect(
                textContent.id,
                ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT
            )
            // bbbbbbb
            constrainSet.connect(
                textContent.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT
            )
            constrainSet.connect(
                textContent.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            constrainSet.connect(
                textContent.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constrainSet.applyTo(constrainLayout)

            edt_content.clearFocus()
            edt_content.setText("")
            edt_content.visibility = View.GONE

            textContent.setOnTouchListener(MoveViewTouchListener(
                textContent,
                iv_delete,
                listener = { isMove, isBoundView ->
                    iv_delete.isVisible = isMove
                    if(isBoundView) constrainLayout.removeView(textContent)
                }
            ))
        }


    }


}
