package com.rspk.internproject.compose.startPage

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rspk.internproject.R
import com.rspk.internproject.compose.ButtonState
import com.rspk.internproject.compose.customComposables.CustomButton
import com.rspk.internproject.compose.customComposables.CustomTextButtons
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.Login
import com.rspk.internproject.navigation.SignUpPageOne

@Composable
fun MainScreenBottomSheet(
    modifier: Modifier = Modifier,
    configuration: Configuration = LocalConfiguration.current,
    pagerState: PagerState,
    listOfImages: List<Int>,
    listOfColors: List<Int>,
    listOfTitles: List<Int>,
    listOfContents: List<Int>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(
                if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) RoundedCornerShape(
                    50.dp,
                    50.dp,
                    0.dp,
                    0.dp
                ) else RoundedCornerShape(0)
            )
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MainScreenBottomSheetContents(
            pagerState = pagerState,
            listOfImages = listOfImages,
            listOfColors = listOfColors,
            listOfTitles = listOfTitles,
            listOfContents = listOfContents
        )
    }
}


@Composable
fun MainScreenBottomSheetContents(
    pagerState: PagerState,
    listOfImages: List<Int>,
    listOfColors: List<Int>,
    listOfTitles: List<Int>,
    listOfContents: List<Int>,
) {
    val navController = LocalNavController.current

    AnimatedContent(
        targetState = pagerState.currentPage,
        label = "",
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = listOfTitles[page]),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp)
            )
            Text(
                text = stringResource(id = listOfContents[page]),
                textAlign = TextAlign.Center,
                fontSize = 13.5.sp,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
        }
    }

    PagerDots(pagerState = pagerState, listOfImages = listOfImages)

    CustomButton(
        color = listOfColors[pagerState.currentPage],
        state = ButtonState.ButtonWithInnerText(R.string.join_the_movement),
        onClick = {
            navController.navigate(SignUpPageOne)
        },
        widthOfButton = 0.6f
    )

    CustomTextButtons(
        onClick = {
            navController.navigate(Login)
        },
        text = R.string.login
    )
}


@Composable
fun PagerDots(
    pagerState: PagerState,
    listOfImages: List<Int>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.2f)
            .padding(vertical = 40.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        repeat(listOfImages.size) { index ->
            Box(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = EaseIn
                        )
                    )
                    .size(
                        width = if (index == pagerState.currentPage) 20.dp else 10.dp,
                        height = 10.dp
                    )
                    .clip(if (index == pagerState.currentPage) RoundedCornerShape(90.dp) else CircleShape)
                    .background(MaterialTheme.colorScheme.inverseSurface),
            )
        }
    }
}