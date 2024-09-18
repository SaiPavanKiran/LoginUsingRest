package com.rspk.internproject.compose.signupPage

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rspk.internproject.R
import com.rspk.internproject.compose.AppHeading
import com.rspk.internproject.compose.PageHeadingForSignUp
import com.rspk.internproject.compose.ShortInfoText

sealed class SignUpPageState {
    data class WithTitles(
        val pageNumber: Int,
        @StringRes val pageHeading: Int,
    ) : SignUpPageState()

    data object WithoutTitles : SignUpPageState()
}


@Composable
fun SignUpPagePortraitLayout(
    modifier: Modifier = Modifier,
    headlineState: SignUpPageState,
    signInButton:(@Composable () -> Unit)? = null,
    navigationButton: @Composable () -> Unit,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .padding(horizontal = 30.dp)
            .padding(top = 40.dp, bottom = 20.dp),
    ) {
        when (headlineState) {
            is SignUpPageState.WithTitles -> {
                AppHeading()
                ShortInfoText(
                    text = stringResource(
                        id = R.string.sign_up_number_info,
                        headlineState.pageNumber
                    )
                ) //page Info
                PageHeadingForSignUp(
                    text = headlineState.pageHeading,
                    modifier = Modifier.padding(bottom = 45.dp)
                )
            }

            is SignUpPageState.WithoutTitles -> {}
        }
        if (signInButton != null) {
            signInButton()
        }
        content(Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(0.01f))
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            navigationButton()
        }
    }
}


@Composable
fun SignUpPageLandScapeLayout(
    modifier: Modifier = Modifier,
    headlineState: SignUpPageState,
    signInButton:(@Composable () -> Unit)? = null,
    navigationButton: @Composable () -> Unit,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .padding(horizontal = 30.dp)
            .padding(top = 40.dp, bottom = 20.dp),
    ) {
        when (headlineState) {
            is SignUpPageState.WithTitles -> {
                Column(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .weight(1f)
                ) {

                    AppHeading()
                    ShortInfoText(
                        text = stringResource(
                            id = R.string.sign_up_number_info,
                            headlineState.pageNumber
                        )
                    ) //page Info
                    PageHeadingForSignUp(
                        text = headlineState.pageHeading,
                        modifier = Modifier.padding(bottom = 45.dp)
                    )
                    if (signInButton != null) {
                        signInButton()
                    }
                }
            }

            is SignUpPageState.WithoutTitles -> {}
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            content(Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(0.01f))
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                navigationButton()
            }
        }
    }
}