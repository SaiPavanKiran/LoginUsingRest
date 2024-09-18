package com.rspk.internproject.compose

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rspk.internproject.R
import com.rspk.internproject.navigation.LocalNavController
import com.rspk.internproject.navigation.Login
import com.rspk.internproject.navigation.Profile
import com.rspk.internproject.viewmodels.FeedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FeedPage(
    context:Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    feedViewModel: FeedViewModel
) {
    var count by rememberSaveable { mutableIntStateOf(2) }

    LaunchedEffect(key1 = count < 2) {
        if(count < 2){
            coroutineScope.launch {
                delay(1500)
                count = 2
            }
        }
    }

    BackHandler {
        count--
        if(count == 1){
            Toast.makeText(context,"Press again to exit",Toast.LENGTH_SHORT).show()
        }
        if(count <= 0){
            (context as Activity).finish()
        }
    }

    FeedImage()
    ExitButton(feedViewModel = feedViewModel)


}


@Composable
fun FeedImage(){
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.farmer_woman_svgrepo_com__1_) ,
            contentDescription = "Feed Image" ,
            modifier = Modifier
                .size(200.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        ShortInfoText(
            text = stringResource(id = R.string.app_name),
            fontSize = 18.sp,
            textColor = Color.LightGray,
            textAlignment = TextAlign.Justify,
            modifier = Modifier.padding(top = 10.dp)
        )

    }
}

@Composable
fun ExitButton(
    context: Context = LocalContext.current,
    navController: NavController = LocalNavController.current,
    feedViewModel: FeedViewModel
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            ShortInfoText(
                text = "Exit",
                fontSize = 18.sp,
                textColor = Color.LightGray,
                textAlignment = TextAlign.Justify,
                modifier = Modifier.padding(top = 10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.baseline_exit_to_app_24) ,
                contentDescription = "Sign Out" ,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        feedViewModel.onExit(context)
                        navController.navigate(Login) {
                            popUpTo(Profile) {
                                inclusive = true
                            }
                        }
                    }
            )
        }
    }
}