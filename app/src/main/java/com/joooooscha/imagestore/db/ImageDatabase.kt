package com.joooooscha.imagestore.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageMeta::class], version = 1)
abstract class ImageDatabase: RoomDatabase() {
    abstract fun imageDao(): ImageDao
}