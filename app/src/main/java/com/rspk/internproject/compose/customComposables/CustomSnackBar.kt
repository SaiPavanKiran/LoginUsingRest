package com.rspk.internproject.compose.customComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSnackBar(
    modifier: Modifier = Modifier,
    text:String,
    showSnackBar:Boolean,
    onSnackBarDismiss:()->Unit,
    closeIcon :Painter,
    iconDescription:String? = null,
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = SnackbarDefaults.contentColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
){
    if(showSnackBar){
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(55.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(color = containerColor)
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontSize = 15.sp,
                    textDecoration = TextDecoration.Underline,
                    color = contentColor
                )
                IconButton(onClick = {
                    onSnackBarDismiss()
                },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = dismissActionContentColor)
                ) {
                    Icon(
                        painter = closeIcon,
                        contentDescription = iconDescription,
                        modifier = Modifier
                            .size(20.dp),
                    )
                }
            }
        }
    }
}

