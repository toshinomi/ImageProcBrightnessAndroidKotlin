package com.example.imageprocbrightnessandroidkotlin

import android.graphics.Bitmap

/**
 * 明るさ
 */
class Brightness {
    public class Brightness() {}

    /**
     * 明るさの実行
     * @args bitmap ビットマップ
     * @args alpha  明るさ　%
     * @return return 明るさ後のビットマップ
     */
    public fun goImageProcessing(bitmap: Bitmap, alphaValue: Int): Bitmap {
        var pixels = IntArray(bitmap.width * bitmap.height);
        var resultPixels = IntArray(bitmap.width * bitmap.height)

        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for ((count, argb) in pixels.withIndex()) {
            var red = android.graphics.Color.red(argb)
            var green = android.graphics.Color.green(argb)
            var blue = android.graphics.Color.blue(argb)
            var alpha = android.graphics.Color.alpha(argb) * alphaValue / 100
            resultPixels[count] = android.graphics.Color.argb(alpha, red, green, blue)
        }

        var mutableBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        mutableBitmap.setPixels(resultPixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        return mutableBitmap;
    }
}