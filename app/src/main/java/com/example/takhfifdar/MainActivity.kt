package com.example.takhfifdar

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import com.example.takhfifdar.screens.*
import com.example.takhfifdar.ui.theme.TakhfifdarTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

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

                    var hasCameraPermission by remember {
                        mutableStateOf(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    }
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { granted ->
                            hasCameraPermission = granted
                        }
                    )

                    NavHost(navController = navController, startDestination = "SplashScreen") {

                        composable("HomeScreen/{userId}") {
                            HomeScreen(navController, this@MainActivity, launcher, it.arguments!!.getString("userId")!!)
                        }

                        composable("QrScanner") {
                            QrCodeScanner(navController)
                        }

                        composable("FeedbackScreen"){
                            FeedbackScreen(navController)
                        }

                        composable("LoginScreen") {
                            LoginScreen(navController, viewModel<LoginScreenViewModel>())
                        }

                        composable("SplashScreen") {
                            SplashScreen(navController, database)
                        }

                    }
                }
            }
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