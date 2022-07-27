package com.example.nasasimple.ZoomImageView

import java.lang.StringBuilder

class TouchPoints {

    var currentTouchPoints = mutableListOf<Point>()
    private val START_ID = 0


    fun touchCount() : Int = currentTouchPoints.size

    fun add(point : Point) {
        currentTouchPoints.add(point)
    }

    fun changeCountPoints(savedPointId : MutableList<Int>) {
        currentTouchPoints.removeIf {
            it.pointId !in savedPointId
        }
    }

//    private fun findMaxId() : Int {
//        var id = START_ID
//        for (touchPoint in currentTouchPoints) {
//            if (id == touchPoint.pointId) {
//                id++
//            } else {
//                break
//            }
//        }
//        return id
//    }

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

    fun movePoint(point : MutableList<Point>) {
        for (touchPoint in currentTouchPoints) {
            point.find {
                it.pointId == touchPoint.pointId
            }.apply {
                this?.let {
                    touchPoint.x = this.x
                    touchPoint.y = this.y
                }
            }
        }
    }
}