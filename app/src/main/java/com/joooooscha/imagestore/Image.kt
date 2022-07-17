package com.joooooscha.imagestore

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.joooooscha.imagestore.db.ImageMeta

data class Image(val pos: Int) { // /*val dbImage: ImageDB, val bitmap: ImageBitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888).asImageBitmap()*/) {

    var dbImage: ImageMeta? = null
    private var bitmap: ImageBitmap = Bitmap.createBitmap(500, 300, Bitmap.Config.ARGB_8888).asImageBitmap()

    fun getName(): String {
        return if (this.dbImage == null) {
            "no loaded"
        } else {
            this.dbImage!!.name
        }
    }

    fun getBitmap(): ImageBitmap {
        return this.bitmap
    }

    fun updateMeta(dbImage: ImageMeta) {
        this.dbImage = dbImage
    }

    fun updateBitmap(bitmap: ImageBitmap) {
        this.bitmap = bitmap
    }
}