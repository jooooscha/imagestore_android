package com.joooooscha.imagestore

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.remember
import com.joooooscha.imagestore.ui.Ui.ImageList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ImageViewModel()

        setContent {
            ImageList(viewModel = viewModel)
        }

        val manager = Manager(viewModel, applicationContext)

        // load in default thread
        @Suppress("DeferredResultUnused")
        CoroutineScope(Dispatchers.Default).async {
            manager.loadImageRange(0, 10)
            manager.syncMeta()
            Log.d("main", "sync complete")
            manager.loadImageRange(0, 10)
            manager.loadImageThumbnailRange(0, 10)
            Log.d("main", "Loading complete")
        }

        Log.d("nope", "startup finished")
    }
}