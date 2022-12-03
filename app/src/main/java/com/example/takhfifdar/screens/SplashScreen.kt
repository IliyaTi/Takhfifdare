package com.example.takhfifdar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.takhfifdar.R
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController, database: TakhfifdarDatabase) {
    LaunchedEffect(key1 = false) {
        CoroutineScope(Dispatchers.Main).launch {
            if (database.UserDao().getUser() != null) {
                navController.navigate("HomeScreen/${database.UserDao().getUser().id}")
            } else {
                navController.navigate("LoginScreen")
            }

        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
        Text(text = "تخفیف داره", fontFamily = FontFamily(Font(R.font.almarai_regular)), fontSize = 36.sp, color = Color(0xff001E60))
    }
}