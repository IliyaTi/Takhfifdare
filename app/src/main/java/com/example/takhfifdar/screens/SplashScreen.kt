package com.example.takhfifdar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.takhfifdar.BuildConfig
import com.example.takhfifdar.R
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.UpdateDialog
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.util.Connection
import com.example.takhfifdar.util.Connection.connectivityState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SplashScreen(database: TakhfifdarDatabase, versionDialog: @Composable () -> Unit) {

    val state = connectivityState()
    val scope = rememberCoroutineScope()
    val showUpdateDialog = remember {
        mutableStateOf(false)
    }

    Image(painter = painterResource(id = R.drawable.splash), contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {

        }
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {

        }
        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment =  Alignment.Center) {
            CircularProgressIndicator(color = Color.White)
        }
    }

    if (state.value == Connection.ConnectionState.Available) {
        LaunchedEffect(showUpdateDialog.value) {

            scope.launch(Dispatchers.IO) {
                val ver = RetrofitInstance.api.version()
                if (ver.isSuccessful) {
                    if (BuildConfig.VERSION_CODE >= ver.body()!!.min) {
                        val user = database.UserDao().getUser()

                        if (user != null) {
                            TakhfifdareApplication.loggedInUser.value = user
                        }
                        Navigator.navigateTo(navTarget = NavTarget.HomeScreen)
                    } else {
                        showUpdateDialog.value = true
                    }
                }
            }


        }
    }

    if (showUpdateDialog.value)
        versionDialog()

}


@Composable
fun NetworkUnavailable() {
    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(Color(0xFF202020)),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(imageVector = Icons.Filled.Warning, contentDescription = "", tint = Color(0xFFFFC107), modifier = Modifier.size(50.dp))
            Icon(imageVector = Icons.Filled.WifiOff, contentDescription = "",tint = Color(0xFFDA0E0E), modifier = Modifier.size(50.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "ارتباط دستگاه با اینترنت برقرار نیست", color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "به محض برقراری مجدد ارتباط، برنامه به روز خواهد شد", color = Color.White)
    }
}