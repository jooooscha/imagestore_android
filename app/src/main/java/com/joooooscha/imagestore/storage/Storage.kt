package com.joooooscha.imagestore.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException

class Storage(private val context: Context) {

    fun writeImage(imageId: Int, img: Bitmap) {
        // write
        try {
            val fos = this.context.openFileOutput(imageId.toString(), Context.MODE_PRIVATE)
            img.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()

            Log.d("writeImage", "written")
        } catch (e: IOException) {
            Log.d("writeImage", "not written $imageId")
            e.printStackTrace()
        }
    }

    fun readImage(imageId: Int): Bitmap? {

        return try {
            val fis = this.context.openFileInput(imageId.toString())
            val img = BitmapFactory.decodeStream(fis)
            fis.close()
            img
        } catch (e: IOException) {
            null
        }
    }

    fun exists(imageId: Int): Boolean {
        return readImage(imageId) != null
    }
}