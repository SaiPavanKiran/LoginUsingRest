package com.rspk.internproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rspk.internproject.compose.DummyPage
import com.rspk.internproject.compose.FeedPage
import com.rspk.internproject.compose.loginPage.ForgotPasswordPage
import com.rspk.internproject.compose.loginPage.LoginPage
import com.rspk.internproject.compose.loginPage.OtpPage
import com.rspk.internproject.compose.loginPage.ResetPasswordPage
import com.rspk.internproject.compose.signupPage.BusinessHours
import com.rspk.internproject.compose.signupPage.SignUpFarmInfo
import com.rspk.internproject.compose.signupPage.SignUpSuccess
import com.rspk.internproject.compose.signupPage.SignUpVerification
import com.rspk.internproject.compose.signupPage.SignUpWelcomePage
import com.rspk.internproject.compose.startPage.MainScreen
import com.rspk.internproject.viewmodels.FeedViewModel
import com.rspk.internproject.viewmodels.StartViewModel
import com.rspk.internproject.viewmodels.login.ForgotPasswordViewModel
import com.rspk.internproject.viewmodels.login.LoginPageViewModel
import com.rspk.internproject.viewmodels.login.OtpViewModel
import com.rspk.internproject.viewmodels.login.ResetPasswordViewModel
import com.rspk.internproject.viewmodels.signup.BusinessHoursViewModel
import com.rspk.internproject.viewmodels.signup.SignUpFarmInfoViewModel
import com.rspk.internproject.viewmodels.signup.SignUpSuccessViewModel
import com.rspk.internproject.viewmodels.signup.SignUpVerificationViewModel
import com.rspk.internproject.viewmodels.signup.SignUpWelcomePageViewModel

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    signUpWelcomePageViewModel: SignUpWelcomePageViewModel = viewModel(),
    signUpFarmInfoViewModel: SignUpFarmInfoViewModel = viewModel(),
    signUpVerificationViewModel: SignUpVerificationViewModel = viewModel(),
    businessHoursViewModel: BusinessHoursViewModel = viewModel(),
    signUpSuccessViewModel: SignUpSuccessViewModel = viewModel(),
    loginPageViewModel: LoginPageViewModel = viewModel(),
    startViewModel: StartViewModel = viewModel(),
    feedViewModel: FeedViewModel = viewModel(),
    forgotPasswordViewModel: ForgotPasswordViewModel = viewModel(),
    otpViewModel: OtpViewModel = viewModel(),
    resetPasswordViewModel: ResetPasswordViewModel = viewModel()
) {
    NavHost(
        navController = navHostController,
        startDestination = startViewModel.navGraphCondition()
    ) {

        composable<Home> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                MainScreen()
            }
        }

        composable<Login> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                LoginPage(
                    loginPageViewModel = loginPageViewModel
                )
            }
        }

        composable<ForgotPassword> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                ForgotPasswordPage(
                    forgotPasswordViewModel = forgotPasswordViewModel
                )
            }
        }

        composable<Otp> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                OtpPage(
                    otpViewModel = otpViewModel
                )
            }
        }

        composable<ResetPassword> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                ResetPasswordPage(
                    resetPasswordViewModel = resetPasswordViewModel
                )
            }
        }

        composable<SignUpPageOne> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                SignUpWelcomePage(
                    signUpWelcomePageViewModel = signUpWelcomePageViewModel
                )
            }
        }

        composable<SignUpPageTwo> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                SignUpFarmInfo(
                    signUpFarmInfoViewModel = signUpFarmInfoViewModel
                )
            }
        }

        composable<SignUpPageThree> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                SignUpVerification(
                    signUpVerificationViewModel = signUpVerificationViewModel
                )
            }
        }

        composable<SignUpPageFour> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                BusinessHours(
                    businessHoursViewModel = businessHoursViewModel
                )
            }
        }

        composable<SignUpPageFive> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                SignUpSuccess(
                    signUpSuccessViewModel = signUpSuccessViewModel
                )
            }
        }

        composable<Profile> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                FeedPage(
                    feedViewModel = feedViewModel
                )
            }
        }
        composable<Dummy> {
            CompositionLocalProvider(
                value = LocalNavController provides navHostController
            ) {
                DummyPage()
            }
        }
    }
}