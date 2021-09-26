package com.joooooscha.imagestore

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.joooooscha.imagestore.api.APIAdapter
import com.joooooscha.imagestore.db.DBHandler
import com.joooooscha.imagestore.db.ImageDB
import com.joooooscha.imagestore.db.ImageDao
import com.joooooscha.imagestore.storage.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.json.JSONObject

class Manager() {

    fun fullUpdate(context: Context) {
        CoroutineScope(Dispatchers.Default).async {
            Log.d("manager", "update started")
            val imageDao = DBHandler.getDAO(context)
            val api = APIAdapter()

            Log.d("manager", "init")
//            init()
            updateDB(context, api, imageDao)
//            loadFromDB(context, imageDao)

            Log.d("manager", "update finished")
        }
    }

    // Helper function

    private suspend fun loadImages(context: Context, imageDao: ImageDao, api: APIAdapter) {

        Log.d("manager", "loading from db")
        val dbData = imageDao.getAll()

        val imgList = arrayListOf<Image>()
        val preBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)

        // load pre images
        for (img in dbData) {
            val image = Image(img, preBitmap)
            imgList.add(image)
        }
        ImageViewModel.replaceList(imgList)

        // load actual images from server
        for (imageDB in dbData.reversed()) {

            if (!Storage.exists(imageDB.id, context)) {
                val bitmap = api.getThumb(imageDB.id) // load thumbnail from server
                Storage.writeImage(imageDB.id, bitmap, context)
            }
        }
    }

    private suspend fun updateDB(context: Context, api: APIAdapter, imageDao: ImageDao) {
        Log.d("manager", "loading from server")
        val ret = api.fetch()
        Log.d("manager", "fetched ${ret.length()} images")

        // fetch information from server
        for (i in 0 until ret.length()) {
            val obj = ret.getJSONObject(i)
            val imageId = obj.getInt("id")

            // test if image exists in DB
            if (imageDao.getImageById(imageId).isEmpty()) {
                Log.d("manager", "images does not exist")
                val imageDB = objToDB(obj) // write to db and create imageDB version
                imageDao.putImage(imageDB.id, imageDB.name, imageDB.height, imageDB.width, imageDB.date, imageDB.type, imageDB.coordx, imageDB.coordy)
            } else {
                Log.d("manager", "images skipped: exists")
            }
        }

        loadImages(context, imageDao, api)
    }

    // Helper helper functions

    private fun objToDB(obj: JSONObject): ImageDB {

        val imageId = obj.getInt("id")
        val imageName = obj.getString("name")
        val imageHeight = obj.getInt("height")
        val imageWidth = obj.getInt("width")
        val imageDate = obj.getString("date")
        val imageType = obj.getString("type")
        // val imageCoordx = obj.getDouble("coordx")
        // val imageCoordy = obj.getDouble("coordy")
        val imageCoordx = 0.0
        val imageCoordy = 0.0

//        imageDao.putImage(
//            imageId,
//            imageName,
//            imageHeight,
//            imageWidth,
//            imageDate,
//            imageType,
//            imageCoordx,
//            imageCoordy
//        )

        return ImageDB(imageId, imageName, imageHeight, imageWidth, imageDate, imageType, imageCoordx, imageCoordy)

    }


}