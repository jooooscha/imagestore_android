package com.joooooscha.imagestore.ui

import android.icu.number.Scale
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.joooooscha.imagestore.Image
import com.joooooscha.imagestore.ImageViewModel

object Ui {
    @Composable
    fun ImageElement(
        image: Image
    ) {
        Text(image.getName())
        Image(
            bitmap = image.getBitmap(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .height(300.dp)
                .padding(10.dp)
                .background(Color.LightGray)
                .fillMaxWidth()
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ImageList(
        viewModel: ImageViewModel
    ) {
        LazyColumn {
            for (image in viewModel.images) {
                item {
                    ImageElement(image = image)
                }
            }
        }
    }
}