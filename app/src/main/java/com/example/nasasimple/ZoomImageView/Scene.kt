package com.example.nasasimple.ZoomImageView

import android.graphics.Bitmap
import android.graphics.Color
import android.util.DisplayMetrics
import java.lang.Math.sqrt
import kotlin.math.sqrt

class Scene(val displayMetrics: DisplayMetrics) {
    var scale : Float = 1f
    var height : Float = 255f
    var width : Float = 255f
    var diagonal : Float = 255f
    var firstDiagonal : Float = 255f
    var ratioBitmap : Float = 1f
    var scaleWidth : Float = 255f
    var scaleHeight : Float = 255f

    var x : Float = 0f
    var y : Float = 400f
    fun centerX() : Float = x
    fun centerY() : Float =  y
    var backgroundColor : Int = Color.BLUE
    var currentPreview : Bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
    var srcBitmap : Bitmap? = null

    fun setBitmap(bitmap: Bitmap) {
        srcBitmap = bitmap
        ratioBitmap = bitmap.height.toFloat() / bitmap.width.toFloat()
        var scaledBitmap = Bitmap.createScaledBitmap(bitmap, (displayMetrics.widthPixels * scale).toInt(),
            (displayMetrics.widthPixels * ratioBitmap * scale).toInt(), false)
        currentPreview = scaledBitmap
        height = scaledBitmap.height.toFloat()
        width = scaledBitmap.width.toFloat()
        diagonal = sqrt(width * width + height * height)
        firstDiagonal = diagonal
    }

    fun motion(dx : Float, dy : Float) {
        x -= dx
//        if (x < 0f) {
//            x = 0f
//        }
//        if (x + scaleWidth < width) {
//            x = scaleWidth - x
//        }
        y -= dy
//        if (y < 0f) {
//            y = 0f
//        }
//        if (y > 2000f) {
//            y = 2000f
//        }
    }

    fun changeScale(distanceDifference : Float) {
        diagonal -= distanceDifference
        scale = diagonal / firstDiagonal
        scaleHeight = height * scale
        scaleWidth = width * scale
        val newHeight = (height * scale).toInt()
        val newWidth = (diagonal * ratioBitmap).toInt()
        srcBitmap?.let {
            var scaledBitmap = Bitmap.createScaledBitmap(it, newWidth,
                (newWidth * ratioBitmap).toInt(), false)
            currentPreview = scaledBitmap
        }
    }
}