package com.joooooscha.imagestore

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.joooooscha.imagestore.api.APIAdapter
import com.joooooscha.imagestore.db.ImageDao
import com.joooooscha.imagestore.storage.Storage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.IOException

@SuppressLint("NotifyDataSetChanged")
class GalleryViewAdapter(
    images: ArrayList<Image>,
    private val context: Context,
): RecyclerView.Adapter<GalleryViewAdapter.ViewHolder>() {

    private var images: List<Image> = arrayListOf()

    init {
        this.images = images
    }

    fun setList(list: ArrayList<Image>) {
        images = list
        val size = ImageViewModel.lastChangedPos.size
        for (i in 0 until size) {
            val pos = ImageViewModel.lastChangedPos.element()
            if (pos == -1) {
                notifyDataSetChanged()
            } else {
                notifyItemChanged(pos)
            }
            ImageViewModel.lastChangedPos.remove()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_element, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.imageView.setImageBitmap(images[position].bitmap)
        val image = ImageViewModel.getImages().value!![position]

//        val bitmap = if (image.state == ImageState.PRE_IMG) {
////            image.bitmap
//        } else {
//            image.bitmap
//        }
        val bitmap = Storage.readImage(image.dbImage.id, context)

        viewHolder.imageView.setImageBitmap(bitmap)
    }

    override fun getItemCount() = images.size

}
