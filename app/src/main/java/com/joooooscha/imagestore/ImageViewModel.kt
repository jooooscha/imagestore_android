package com.joooooscha.imagestore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.joooooscha.imagestore.db.ImageMeta
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImageViewModel: ViewModel() {

    private val imageList = initList().toMutableStateList()

    val images: List<Image>
        get() = imageList

    fun updateMetaAt(i: Int, meta: ImageMeta) {
        this.imageList[i].updateMeta(meta)
    }

    fun updateBitmapAt(i: Int, bitmap: ImageBitmap) {
        this.imageList[i].updateBitmap(bitmap)
    }

    private fun initList() = List(10) { i -> Image(i) }
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