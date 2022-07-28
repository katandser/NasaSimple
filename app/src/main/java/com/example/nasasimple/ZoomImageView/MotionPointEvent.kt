package com.example.nasasimple.ZoomImageView

sealed class MotionPoint {
    data class MoveOnePointEvent(val dx : Float, val dy : Float) : MotionPoint()
    data class MoveTwoPointEvent(val distance: Float, val middleX : Float, val middleY : Float) : MotionPoint()
    object MoveNotingEvent : MotionPoint()
}