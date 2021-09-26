package com.joooooscha.imagestore.db

import android.content.Context
import androidx.room.Room

class DBHandler() {

    companion object {

        private var dao: ImageDao? = null

        fun getDAO(context: Context): ImageDao {
            if (dao == null) {
                val db = Room.databaseBuilder(
                    context,
                    ImageDatabase::class.java, "imageDB"
                ).build()
                dao = db.imageDao()
            }

            return dao as ImageDao
        }
    }

}