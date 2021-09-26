package com.joooooscha.imagestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recView = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        val layoutManager = FlexboxLayoutManager(baseContext, FlexDirection.ROW, FlexWrap.WRAP)
        val adapter = GalleryViewAdapter(arrayListOf(), baseContext)

        recView.layoutManager = layoutManager
        recView.adapter = adapter

        val manager = Manager()

        // add observer
        ImageViewModel.getImages().observe(this@MainActivity, { images ->
            adapter.setList(images)
        })

        manager.fullUpdate(applicationContext)

        Log.d("nope", "startup finished")
    }
}