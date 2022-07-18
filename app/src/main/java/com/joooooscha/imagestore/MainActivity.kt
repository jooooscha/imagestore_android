package com.joooooscha.imagestore

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.joooooscha.imagestore.ui.Ui.ImageList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import androidx.compose.runtime.getValue

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ImageViewModel()

        setContent {
            val model by remember { mutableStateOf(viewModel)}
            ImageList(viewModel = model)
        }

        val manager = Manager(viewModel, applicationContext)

        val from = 0
        val to = 1000

        // load in default thread
        @Suppress("DeferredResultUnused")
        CoroutineScope(Dispatchers.Default).async {
            manager.loadMeta()
            manager.loadImageThumbnailRange(from, to)
            manager.syncMeta()
            Log.d("main", "sync complete")
            manager.loadMeta()
            manager.loadImageThumbnailRange(from, to)
            Log.d("main", "Loading complete")
        }

        Log.d("nope", "startup finished")
    }
}