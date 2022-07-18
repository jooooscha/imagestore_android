package com.joooooscha.imagestore

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.joooooscha.imagestore.db.ImageMeta
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class Image(val pos: Int) { // /*val dbImage: ImageDB, val bitmap: ImageBitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888).asImageBitmap()*/) {

    private var dbImage: ImageMeta? by mutableStateOf(null)
    private var _bitmap by mutableStateOf(Bitmap.createBitmap(500, 300, Bitmap.Config.ARGB_8888).asImageBitmap())

    fun getName(): String {
        return if (this.dbImage == null) {
            "no loaded"
        } else {
            this.dbImage!!.name
        }
    }

    fun getBitmap(): ImageBitmap {
        return this._bitmap
    }

    fun updateMeta(dbImage: ImageMeta) {
        this.dbImage = dbImage
    }

    fun updateBitmap(bitmap: ImageBitmap) {
        this._bitmap = bitmap
    }
}