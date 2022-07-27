package com.example.nasasimple.ZoomImageView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.opengl.ETC1.getWidth
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.example.nasasimple.R
import java.lang.StringBuilder

@SuppressLint("ClickableViewAccessibility")
class Zoom(ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(ctx, attrs, defStyleAttr) {


    var rect = RectF (100f, 200f, 300f, 400f)
    var paint = Paint()
    val scene = Scene(context.resources.displayMetrics)
    val touchPoints = TouchPoints()
    var textView: TextView? = null

    constructor(ctx : Context, attrs: AttributeSet) : this(ctx, attrs, 0)

    protected override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        //here u can control the width and height of the images........ this line is very important
        canvas.drawARGB(255, 255, 255, 255)
        //canvas.drawRect(rect, paint)
        canvas.drawBitmap(scene.currentPreview, scene.centerX(), scene.centerY(), null)
    }


    init {
        //image=context.getResources().getDrawable(R.drawable.icon);
        setFocusable(true)
        paint.color = Color.GREEN
        paint.strokeWidth = 10f
        scene.setBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.test_image))
        setOnTouchListener { _, motionEvent ->

            val pointerCount = motionEvent.pointerCount
            val actionMask = motionEvent.action and MotionEvent.ACTION_MASK
            when (actionMask) {
                MotionEvent.ACTION_DOWN -> {
                    touchPoints.add(Point(motionEvent.getX(0),
                        motionEvent.getX(0),
                        motionEvent.getPointerId(0)))
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    touchPoints.add(Point(motionEvent.getX(motionEvent.actionIndex),
                        motionEvent.getX(motionEvent.actionIndex),
                        motionEvent.getPointerId(motionEvent.actionIndex)))
                }
                MotionEvent.ACTION_MOVE -> {
                    val movedPoint = mutableListOf<Point>()
                    for (i in 0 until pointerCount) {
                       movedPoint.add(Point(motionEvent.getX(i),
                           motionEvent.getY(i),
                           motionEvent.getPointerId(i)))
                   }
                    touchPoints.movePoint(movedPoint)
                    scene.y = motionEvent.getY(0)
                    scene.x = motionEvent.getX(0)
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    val arrayIds = mutableListOf<Int>()
                    touchPoints.changeCountPoints(arrayIds)
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    val arrayIds = mutableListOf<Int>()
                    for (i in 0 until pointerCount) {
                        arrayIds.add(motionEvent.getPointerId(i))
                    }
                    arrayIds.remove(motionEvent.getPointerId(motionEvent.actionIndex))
                    touchPoints.changeCountPoints(arrayIds)
                }
                else -> {

                }
            }
            val finalString = StringBuilder()
            finalString.append(pointerCount)
            finalString.appendLine()
            for (i in 0 until pointerCount) {
                finalString.append(motionEvent.getPointerId(i), " -- ",  motionEvent.getX(i), "-", motionEvent.getX(i))
                finalString.appendLine()
            }
            textView?.text = "Pointer count: " + touchPoints.text()//finalString.toString()
            return@setOnTouchListener true
        }
    }
}