package com.example.seasonsapp

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object Utils {
    fun decodeSampledBitmapFromResource(
        res: Resources, resId: Int, reqWidth: Int, reqHeight: Int
    ): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(res, resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
    ): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}

data class SongInfo(val title: String, val artist: String)

val songInfos = mapOf(
    R.raw.spr1 to SongInfo("Spring Song 1", "Artist A"),
    R.raw.spr2 to SongInfo("Spring Song 2", "Artist B"),
    R.raw.spr3 to SongInfo("Spring Song 3", "Artist C"),
    R.raw.spr4 to SongInfo("Spring Song 4", "Artist D"),
    R.raw.spr5 to SongInfo("Spring Song 5", "Artist E"),
    R.raw.spr6 to SongInfo("Spring Song 6", "Artist F"),
    R.raw.sum1 to SongInfo("Summer Song 1", "Artist G"),
    R.raw.sum2 to SongInfo("Summer Song 2", "Artist G"),
    R.raw.sum3 to SongInfo("Summer Song 3", "Artist G"),
    R.raw.sum4 to SongInfo("Summer Song 4", "Artist G"),
    R.raw.sum5 to SongInfo("Summer Song 5", "Artist G"),
    R.raw.sum6 to SongInfo("Summer Song 6", "Artist G"),
    R.raw.aut1 to SongInfo("Autumn Song 1", "Artist H"),
    R.raw.aut2 to SongInfo("Autumn Song 2", "Artist H"),
    R.raw.aut3 to SongInfo("Autumn Song 3", "Artist H"),
    R.raw.aut4 to SongInfo("Autumn Song 4", "Artist H"),
    R.raw.aut5 to SongInfo("Autumn Song 5", "Artist H"),
    R.raw.aut6 to SongInfo("Autumn Song 6", "Artist H"),
    R.raw.win1 to SongInfo("Winter Song 1", "Artist I"),
    R.raw.win2 to SongInfo("Winter Song 2", "Artist I"),
    R.raw.win3 to SongInfo("Winter Song 3", "Artist I"),
    R.raw.win4 to SongInfo("Winter Song 4", "Artist I"),
    R.raw.win5 to SongInfo("Winter Song 5", "Artist I"),
    R.raw.win6 to SongInfo("Winter Song 6", "Artist I")
)