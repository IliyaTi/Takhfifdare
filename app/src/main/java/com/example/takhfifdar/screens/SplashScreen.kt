package com.example.takhfifdar.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.takhfifdar.R
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.util.Connection
import com.example.takhfifdar.util.Connection.connectivityState


@Composable
fun SplashScreen(database: TakhfifdarDatabase) {

    val state = connectivityState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
        Text(
            text = "تخفیف داره",
            fontFamily = FontFamily(Font(R.font.almarai_regular)),
            fontSize = 36.sp,
            color = Color(0xff001E60)
        )
    }
    if (state.value == Connection.ConnectionState.Unavailable) {
        NetworkUnavailable()

    }

    if (state.value == Connection.ConnectionState.Available) {
        LaunchedEffect(false) {

            if (database.UserDao().getUser() != null) {
                Navigator.navigateTo(navTarget = NavTarget.HomeScreen)
            } else {
                Navigator.navigateTo(navTarget = NavTarget.LoginScreen)
            }

        }
    }



}


@Composable
fun NetworkUnavailable() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = "", tint = Color(0xFFFFC107), modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "فکر کردی با باد کار میکنه؟")
        Text(text = "اینترنتتو روشن کن بابا")
    }
}