package com.example.takhfifdar.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.takhfifdar.R
import com.example.takhfifdar.screens.viewmodels.LoginScreenViewModel
import kotlin.system.exitProcess

@Composable
fun LoginScreen(viewModel: LoginScreenViewModel, context: Activity) {

    BackHandler {
        context.finishAndRemoveTask()
    }

    Row(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color(0xffE51900))
                .weight(1f)
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .background(Color(0xffffffff))
                .weight(1f)
                .fillMaxSize()
        )
    }



    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(0.dp, 0.dp, 32.dp, 0.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.login),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .weight(2f)
                .clip(RoundedCornerShape(topStart = 32.dp))
                .background(Color(0xffffffff))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    OutlinedTextField(
                        value = viewModel.email.value,
                        onValueChange = { viewModel.email.value = it },
                        label = { Text(text = "ایمیل") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = viewModel.password.value,
                        onValueChange = { viewModel.password.value = it },
                        label = { Text(text = "رمز عبور") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = { viewModel.login() },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(text = "ورود", fontSize = 16.sp)
                    }
                }


            }
        }


    }
    if (viewModel.loadingState.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x6A000000)), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF183891))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "شکیبایی کنید", fontSize = 20.sp, color = Color.White)
        }
    }


}