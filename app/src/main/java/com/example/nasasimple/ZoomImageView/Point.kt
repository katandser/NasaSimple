package com.example.nasasimple.ZoomImageView

data class Point(var x: Float, var y: Float, var pointId : Int, var hisX : Float = 1f, var hisY : Float = 1f) {

    fun saveHis() {
        hisX = x
        hisY = y
    }
}