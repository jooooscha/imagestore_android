package com.joooooscha.imagestore

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import com.joooooscha.imagestore.api.APIAdapter
import com.joooooscha.imagestore.db.DBHandler
import com.joooooscha.imagestore.db.ImageMeta
import com.joooooscha.imagestore.db.ImageDao
import com.joooooscha.imagestore.storage.Storage
import org.json.JSONObject

class Manager(private val viewModel: ImageViewModel, context: Context) {

    private val imageDao = DBHandler.getDAO(context)
    private val storage = Storage(context)

    // download metadata and put into database
    suspend fun syncMeta() {
        Log.d("manager", "loading meta from server")
        val ret = APIAdapter.fetchMeta()
        Log.d("manager", "fetched ${ret.length()} images")

        // fetch information from server
        for (i in 0 until ret.length()) {
            val obj = ret.getJSONObject(i)
            val imageId = obj.getInt("id")

            // test if image exists in DB
            if (this.imageDao.getImageById(imageId).isEmpty()) {
                Log.d("manager", "images does not exist")
                val imageDB = objToDB(obj) // write to db and create imageDB version
                this.imageDao.putMeta(imageDB.id, imageDB.name, imageDB.height, imageDB.width, imageDB.date, imageDB.type, imageDB.coordx, imageDB.coordy)
            } else {
                Log.d("manager", "images skipped: exists")
            }
        }
    }


    // try reading images from storage, else download form server
    suspend fun loadImageThumbnailRange(from: Int, to: Int) {
        val ids = this.imageDao.getIdRange(from, to)
        ids.forEachIndexed { i, id ->

            this.storage.readImage(id)?.let { bitmap -> // bitmap known, read from storage
                viewModel.updateBitmapAt(i, bitmap.asImageBitmap())
            } ?: run { // bitmap unknown, load from server
                val bitmap = APIAdapter.getThumb(id)
                viewModel.updateBitmapAt(i, bitmap.asImageBitmap())
                this.storage.writeImage(id, bitmap)
            }

        }
    }

    // load image meta data from range
    suspend fun loadImageRange(from: Int, to: Int) {
        val imageListMeta = this.imageDao.getRange(from, to)
        // val imageListMeta = this.imageDao.getAll()
        Log.d("manager", "got meta from db")
        imageListMeta.forEachIndexed { i, image ->
            viewModel.updateMetaAt(i, image)
        }
    }

    // load images from database and download missing images
    /*private suspend fun loadImages(context: Context, imageDao: ImageDao) {

        Log.d("manager", "loading from db")
        val dbData = imageDao.getAll()

        val imgList = arrayListOf<Image>()
        val preBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)

        // load pre images
        for (img in dbData) {
            //val image = Image(img, preBitmap.asImageBitmap())
            //imgList.add(image)
        }

        //ImageViewModel.replaceList(imgList)

        // load actual images from server
        for (imageDB in dbData.reversed()) {

            // if image is not downloaded, do now
            if (!Storage.exists(imageDB.id, context)) {
                val bitmap = APIAdapter.getThumb(imageDB.id) // TODO: load images not thumbnail from server
                Storage.writeImage(imageDB.id, bitmap, context)
            }
        }
    }*/

    // Helper functions

    // json object to database element
    private fun objToDB(obj: JSONObject): ImageMeta {

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


        return ImageMeta(imageId, imageName, imageHeight, imageWidth, imageDate, imageType, imageCoordx, imageCoordy)

    }


}