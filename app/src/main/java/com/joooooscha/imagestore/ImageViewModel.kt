package com.joooooscha.imagestore

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.joooooscha.imagestore.db.ImageMeta

class ImageViewModel: ViewModel() {

    private val imageList = MutableList(1) { i -> Image(i) }

    val images: List<Image>
        get() = imageList

    fun updateMetaAt(i: Int, meta: ImageMeta) {
        while (i >= this.imageList.size) {
            this.imageList.add(Image(i))
        }
        this.imageList[i].updateMeta(meta)
    }

    fun updateBitmapAt(i: Int, bitmap: ImageBitmap) {
        while (i >= this.imageList.size) {
            this.imageList.add(Image(i))
        }
        this.imageList[i].updateBitmap(bitmap)
    }

}

/*
//    var lastChangedPos: MutableList<Int> = mutableListOf()
    var lastChangedPos: Queue<Int> = LinkedList()
    var images_list by mutableStateOf(0)

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
    }*/

//}