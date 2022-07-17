package com.joooooscha.imagestore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.joooooscha.imagestore.Image
import com.joooooscha.imagestore.ImageViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue

object Ui {
    @Composable
    fun ImageElement(
        image: Image,
    ) {
        Text(image.getName())
        Image(
            bitmap = image.getBitmap(),
            contentDescription = "",
            modifier = Modifier
                .padding(10.dp)
                .border(2.dp, Color.Black)
        )
    }

    @Composable
    fun ImageList(
        viewModel: ImageViewModel
    ) {
        val list = remember { viewModel.images }

        LazyColumn {
            for (image in list) {
                item {
                    ImageElement(image = image)
                }
            }
        }
    }
}