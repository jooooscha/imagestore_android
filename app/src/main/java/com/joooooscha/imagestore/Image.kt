package com.joooooscha.imagestore

import android.graphics.Bitmap
import com.joooooscha.imagestore.db.ImageDB

data class Image(val dbImage: ImageDB, val bitmap: Bitmap) {
}