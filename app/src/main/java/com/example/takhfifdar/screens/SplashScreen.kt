package com.example.takhfifdar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.takhfifdar.R
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.util.Connection
import com.example.takhfifdar.util.Connection.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SplashScreen(database: TakhfifdarDatabase) {

    val state = connectivityState()

    Image(painter = painterResource(id = R.drawable.splash), contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)


    if (state.value == Connection.ConnectionState.Available) {
        LaunchedEffect(false) {

            val user = database.UserDao().getUser()

            if (user != null) {
                TakhfifdareApplication.loggedInUser.value = user
                Navigator.navigateTo(navTarget = NavTarget.HomeScreen)
            } else {
                Navigator.navigateTo(navTarget = NavTarget.LoginScreen)
            }

        }
    }



}


@Composable
fun NetworkUnavailable() {
    Image(
        painter = painterResource(id = R.drawable.asset_3),
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