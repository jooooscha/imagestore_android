package com.joooooscha.imagestore.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException

class Storage {
    companion object {
        fun writeImage(imageId: Int, img: Bitmap, context: Context) {
            // write
            try {
                val fos = context.openFileOutput(imageId.toString(), Context.MODE_PRIVATE)
                img.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()

                Log.d("writeImage", "written")
            } catch (e: IOException) {
                Log.d("writeImage", "not written $imageId")
                e.printStackTrace()
            }
        }

        fun readImage(imageId: Int, context: Context): Bitmap? {

            return try {
                val fis = context.openFileInput(imageId.toString())
                val img = BitmapFactory.decodeStream(fis)
                fis.close()
                img
            } catch (e: IOException) {
                null
            }
        }

        fun exists(imageId: Int, context: Context): Boolean {
            return readImage(imageId, context) != null
        }
    }
}