package com.example.takhfifdar

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.screens.*
import com.example.takhfifdar.screens.viewmodels.FeedbackScreenViewModel
import com.example.takhfifdar.screens.viewmodels.HomeScreenViewModel
import com.example.takhfifdar.screens.viewmodels.LoginScreenViewModel
import com.example.takhfifdar.ui.theme.TakhfifdarTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainActivityViewModel = viewModel()
            val database = TakhfifdarDatabase.getDatabase(this)
            TakhfifdarTheme {
                window.statusBarColor = Color(0xFF001E60).toArgb()
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
){
    LaunchedEffect("navigation") {
        Navigator.sharedFlow.onEach {
            navController.navigate(it)
        }.launchIn(this)
    }
    NavHost(navController = navController, startDestination = "SplashScreen") {

        composable("HomeScreen") {
            HomeScreen(activity, launcher, viewModel<HomeScreenViewModel>())
        }

        composable("QrScanner") {
            QrCodeScanner()
        }

        composable(
            "FeedbackScreen/{vendor}",
            arguments = listOf(navArgument("vendor") { type = NavType.StringType })
        ){
            FeedbackScreen(it.arguments!!.getString("vendor")!!, viewModel<FeedbackScreenViewModel>())
        }

        composable("LoginScreen") {
            LoginScreen(viewModel<LoginScreenViewModel>(), activity)
        }

        composable("SplashScreen") {
            SplashScreen(database)
        }

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