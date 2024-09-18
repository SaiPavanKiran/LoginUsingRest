package com.rspk.internproject.compose.startPage

import android.content.res.Configuration
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import com.rspk.internproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    configuration: Configuration = LocalConfiguration.current
) {

    val listOfImages = listOf(
        R.drawable.image3,
        R.drawable.image,
        R.drawable.image2
    )

    val listOfColors = listOf(
        R.color.green,
        R.color.red,
        R.color.yellow
    )

    val listOfTitles = listOf(
        R.string.quality_title,
        R.string.convenient_title,
        R.string.local_title
    )
    
    val listOfContents = listOf(
        R.string.quality_content,
        R.string.convenient_content,
        R.string.local_content
    )
    
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { listOfImages.size }
    )

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(3500)
            if (!pagerState.isScrollInProgress) {
                coroutineScope.launch(Dispatchers.Main.immediate) {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                        animationSpec = tween(
                            durationMillis = 700,
                            easing = EaseInOut
                        )
                    )
                }
            }
        }
    }

    val brightnessFilter = if (isSystemInDarkTheme()) {
        ColorMatrix().apply { setToScale(0.7f, 0.7f, 0.7f, 1f) } // Darken by 30%
    } else {
        null
    }
    val mainContentModifier =
        Modifier
            .fillMaxSize()
            .background(colorResource(id = listOfColors[pagerState.currentPage]))

    if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        Column(modifier= mainContentModifier){
            MainScreenTopPager(
                pagerState = pagerState,
                listOfImages = listOfImages,
                modifier = Modifier
                    .weight(1f)
            )
            MainScreenBottomSheet(
                modifier = Modifier
                    .weight(1f),
                listOfImages = listOfImages,
                listOfColors = listOfColors,
                listOfTitles = listOfTitles,
                listOfContents = listOfContents,
                pagerState = pagerState
            )
        }
    }else{
        Row(
            modifier = mainContentModifier
        ){
            MainScreenTopPager(
                pagerState = pagerState,
                listOfImages = listOfImages,
                modifier = Modifier
                    .weight(1f)
            )
            MainScreenBottomSheet(
                modifier = Modifier
                    .weight(1f),
                listOfImages = listOfImages,
                listOfColors = listOfColors,
                listOfTitles = listOfTitles,
                listOfContents = listOfContents,
                pagerState = pagerState
            )
        }
    }
}



