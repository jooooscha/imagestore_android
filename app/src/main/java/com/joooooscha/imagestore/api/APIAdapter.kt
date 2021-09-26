package com.joooooscha.imagestore.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class APIAdapter {

//    private val baseUrl = "http://192.168.178.40:3000/api/" //TODO
    private val baseUrl = "http://10.109.150.7:3000/api/" //TODO
//    private val baseUrl = "http://duckduckgo.com"

    suspend fun fetch(): JSONArray {
//        val url = URL("${baseUrl}media/search/house")
        val url = URL("${baseUrl}media/all")
        val con = url.openConnection() as HttpURLConnection
        return withContext(Dispatchers.IO) {
            val ret: JSONArray
            con.apply {
                requestMethod = "GET"
                setRequestProperty("Accept", "application/json")
                con.doInput = true
                con.doOutput = false

                val responseCode = con.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val res = con.inputStream.bufferedReader().use { it.readText() }
                    withContext(Dispatchers.Main) {
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val pjson = gson.toJson(JsonParser.parseString(res))
                        ret = JSONArray(pjson)
                    }
                } else {
                    Log.d("nope", responseCode.toString())
                    ret = JSONArray("")
                }
            }
            ret
        }
    }

    suspend fun getImage(id: Int): Bitmap {
        val url = URL("${baseUrl}media/$id")
        return downloadHelper(url)
    }

    suspend fun getThumb(id: Int): Bitmap {
        val url = URL("${baseUrl}media/thumb_$id")
//        Thread.sleep(1000)
        return downloadHelper(url)
    }

    private fun downloadHelper(url: URL): Bitmap {
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        val ret: Bitmap

        val responseCode = con.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val res = con.inputStream
            ret = BitmapFactory.decodeStream(res)
        } else {
            ret = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            Log.d("nope", responseCode.toString())
        }
        return ret
    }
}