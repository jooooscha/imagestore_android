package com.joooooscha.imagestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

object ImageViewModel: ViewModel() {

//    var lastChangedPos: MutableList<Int> = mutableListOf()
    var lastChangedPos: Queue<Int> = LinkedList()

    private val images: MutableLiveData<ArrayList<Image>> by lazy {
        MutableLiveData<ArrayList<Image>>()
    }

    fun getImages(): LiveData<ArrayList<Image>> {
        return images
    }

    fun replaceList(imgList: ArrayList<Image>) {
        this.lastChangedPos.add(-1)
        setImageList(imgList)
    }

    fun addImage(image: Image) {

        // if live data not initialized
        if (images.value == null) {
            replaceList(arrayListOf())
        }

        images.value!!.add(image)
        this.lastChangedPos.add(images.value!!.size-1)
        setImageList(images.value!!)
    }

    fun updateImage(image: Image) {
        if (images.value == null) {
            Log.d("ImageViewModel", "images.value == null")
            return
        }

        for ((i, img) in images.value!!.withIndex()) {
            if (img.dbImage == image.dbImage) {
                images.value!![i] = image
                this.lastChangedPos.add(i)
                break
            }
        }

        this.replaceList(images.value!!)
    }

    private fun setImageList(imgList: ArrayList<Image>) {
        Log.d("ImageViewModel", "setImageList $imgList")
        imgList.sortBy { image -> image.dbImage.name } // second key
        imgList.sortBy { image -> image.dbImage.date } // first key
        imgList.reverse()
        images.postValue(imgList)
    }

}