package com.example.nasasimple.ZoomImageView

import java.lang.StringBuilder
import kotlin.math.pow
import kotlin.math.sqrt

class TouchPoints {

    var currentTouchPoints = mutableListOf<Point>()
    private val START_ID = 0
    private var lastScale = 1f


    fun touchCount() : Int = currentTouchPoints.size

    fun add(point : Point) {
        currentTouchPoints.add(point)
    }

    fun changeCountPoints(savedPointId : MutableList<Int>) {
        currentTouchPoints.removeIf {
            it.pointId !in savedPointId
        }
    }

    fun text() : String {
        val finalString = StringBuilder()
        finalString.append(currentTouchPoints.size)
        finalString.appendLine()
        for (point in currentTouchPoints) {
            finalString.append(point.x, "-", point.y)
            finalString.appendLine()
        }
        return finalString.toString()
    }

    fun movePoint(point : MutableList<Point>): MotionPoint {
        val newPoints = mutableListOf<Point>()
        for (touchPoint in currentTouchPoints) {
            touchPoint.saveHis()
            point.find {
                it.pointId == touchPoint.pointId
            }?.let {
                touchPoint.x = it.x
                touchPoint.y = it.y
            }
        }
        if (currentTouchPoints.size == 1) {
            val dx = currentTouchPoints[0].x - currentTouchPoints[0].hisX
            val dy = currentTouchPoints[0].y - currentTouchPoints[0].hisY
            //currentTouchPoints = newPoints
            return if (dx < 10f && dy < 10f) {
                MotionPoint.MoveNotingEvent
            } else {
                MotionPoint.MoveOnePointEvent(dx, dy)
            }

        }
        if (currentTouchPoints.size >= 2) {
            val oldDistance = sqrt((currentTouchPoints[0].hisX - currentTouchPoints[1].hisX).pow(2) +
                    (currentTouchPoints[0].hisY - currentTouchPoints[1].hisY).pow(2))
            val newDistance = sqrt((currentTouchPoints[0].x - currentTouchPoints[1].x).pow(2) +
                    (currentTouchPoints[0].y - currentTouchPoints[1].y).pow(2))
            //currentTouchPoints = newPoints
            return MotionPoint.MoveTwoPointEvent(oldDistance - newDistance,
                (newPoints[0].x + newPoints[1].x) / 2,
                (newPoints[0].y + newPoints[1].y) / 2)
        }
        return MotionPoint.MoveNotingEvent

    }
}