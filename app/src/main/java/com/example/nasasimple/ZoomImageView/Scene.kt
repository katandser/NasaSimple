package com.example.nasasimple.ZoomImageView

import android.graphics.Bitmap
import android.graphics.Color
import android.util.DisplayMetrics

class Scene(val displayMetrics: DisplayMetrics) {
    var scale : Float = 1f
    var height : Int = 255
    var width : Int = 255
    var x : Float = 200f
    var y : Float = 200f
    fun centerX() : Float = x - (width / 2)
    fun centerY() : Float = y - (height / 2)
    var backgroundColor : Int = Color.BLUE
    var currentPreview : Bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888)

    fun setBitmap(bitmap: Bitmap) {

        var ratioBitmap = bitmap.height.toFloat() / bitmap.width.toFloat()
        var scaledBitmap = Bitmap.createScaledBitmap(bitmap, displayMetrics.widthPixels,
            (displayMetrics.widthPixels * ratioBitmap).toInt(), false)
        currentPreview = scaledBitmap
        height = scaledBitmap.height
        width = scaledBitmap.width
    }

    init {
        for (x in 0 until width) {
            for (y in 0 until height) {
                currentPreview.setPixel(x, y,
                   // Color.BLUE
                    (0xFFFF0000 + ((255 - x) * 0xFF) + 255 - y).toInt()
                )
            }
        }
    }


}