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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.takhfifdar.R
import com.example.takhfifdar.screens.viewmodels.LoginScreenViewModel

@Composable
fun LoginScreen(viewModel: LoginScreenViewModel, context: Activity) {

    BackHandler {
        context.finishAndRemoveTask()
    }

    Row(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color(0xff43C4D0))
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
                painter = painterResource(id = R.drawable.login_header),
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
            if (viewModel.pageState.value == 1) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        OutlinedTextField(
                            value = viewModel.phoneNumber.value.toString(),
                            onValueChange = { viewModel.phoneNumber.value = it },
                            label = { Text(text = "شماره تلفن") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { viewModel.login() },
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(text = "ارسال کد", fontSize = 16.sp)
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        OutlinedTextField(
                            value = viewModel.otp.value,
                            onValueChange = { viewModel.otp.value = it },
                            label = { Text(text = "کد ارسالی") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { viewModel.confirmCode() },
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(text = "ورود", fontSize = 16.sp)
                        }
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