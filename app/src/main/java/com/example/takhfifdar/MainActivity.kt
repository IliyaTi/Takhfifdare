package com.example.takhfifdar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.screens.*
import com.example.takhfifdar.screens.viewmodels.*
import com.example.takhfifdar.ui.theme.TakhfifdarTheme
import com.example.takhfifdar.util.Connection
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val database = TakhfifdarDatabase.getDatabase(this)
            TakhfifdarTheme {
                window.statusBarColor = Color(0xFF2e3192).toArgb()
                val state = Connection.connectivityState()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

//                    var hasCameraPermission by remember {
//                        mutableStateOf(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
//                    }
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { result ->
                            if (result) Navigator.navigateTo(navTarget = NavTarget.QrScanner)
                        }
                    )

                    NavigationComponent(navController, this@MainActivity, launcher, database)

                    if (state.value == Connection.ConnectionState.Unavailable) {
                        NetworkUnavailable()

                    }

                }
            }
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun NavigationComponent(
    navController: NavHostController,
    activity: Activity,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    database: TakhfifdarDatabase
) {
    LaunchedEffect("navigation") {
        Navigator.sharedFlow.onEach {
            navController.navigate(it) {
                launchSingleTop = true
            }
        }.launchIn(this)
    }
    NavHost(navController = navController, startDestination = "PaymentResult") {

        composable("HomeScreen") {
            HomeScreen(
                activity,
                viewModel<HomeScreenViewModel>(
                    factory = HomeScreenViewModelFactory(
                        TakhfifdareApplication(),
                        launcher,
                        activity
                    )
                )
            )
        }

        composable("FillUserDataScreen") {
            FillUserDataScreen(viewModel = viewModel<FillUserDataScreenViewModel>())
        }

        composable("QrScanner") {
//            QrCodeScanner(viewModel<QrCodeScannerViewModel>())
            QrScanner(viewModel<QrCodeScannerViewModel>())
        }

        composable(
            "FeedbackScreen/{vendor}",
            arguments = listOf(navArgument("vendor") { type = NavType.StringType })
        ) {
            FeedbackScreen(
                it.arguments!!.getString("vendor")!!,
                viewModel<FeedbackScreenViewModel>()
            )
        }

        composable("LoginScreen") {
            LoginScreen(viewModel<LoginScreenViewModel>(), activity)
        }

        composable("SplashScreen") {
            SplashScreen(database)
        }

        composable("BuyCouponScreen") {
            BuyCouponScreen(viewModel<BuyCouponScreenViewMode>())
        }

        composable("dial") {
            UpdateDialog()
        }

        composable(
            route = "PaymentResult",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "commando://sh.com/{id}"
                    action = Intent.ACTION_VIEW
                }
            ), arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            PaymentResultScreen(it.arguments?.getInt("id") ?: 0)
        }

    }
}


@Composable
fun UpdateDialog() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "بروزرسانی")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "انصراف")
                }
            },
            text = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(imageVector = Icons.Filled.Update, contentDescription = "")
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "بروزرسانی", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "در سیستم ما تغییرات عمده ای صورت گرفته و ادامه فعالیت برنامه نیازمند بروزرسانی است. لطفا جهت استفاده از برنامه آن را بروزرسانی کنید.")
                }
            },

            )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TakhfifdarTheme {
        Greeting("Android")
    }
}