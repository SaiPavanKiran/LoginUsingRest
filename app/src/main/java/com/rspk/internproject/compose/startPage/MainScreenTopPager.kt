package com.rspk.internproject.compose.startPage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenTopPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    listOfImages: List<Int>,
){
    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
    ) { page ->
        //decrease brightness of image while in dark mode
        val brightnessFilter = if(isSystemInDarkTheme()) {
            ColorMatrix().apply { setToScale(0.7f, 0.7f, 0.7f, 1f) }
        } else {
            null
        }
        Image(
            painter = painterResource(id = listOfImages[page] ),
            contentDescription = null,
            colorFilter = brightnessFilter?.let { ColorFilter.colorMatrix(it) },
            modifier = Modifier
                .fillMaxSize()
        )
    }

}