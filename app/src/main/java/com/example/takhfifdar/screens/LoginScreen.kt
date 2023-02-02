package com.example.takhfifdar.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.takhfifdar.R
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.screens.viewmodels.LoginScreenViewModel

@Composable
fun LoginScreen(viewModel: LoginScreenViewModel, context: Activity) {
    

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

    BackHandler {
        Navigator.navigateTo(NavTarget.HomeScreen)
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
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Checkbox(checked = viewModel.checked.value,
                                onCheckedChange = { viewModel.checked.value = it },
                                modifier = Modifier.size(20.dp),
                                colors = CheckboxDefaults.colors(checkedColor = Color(0xff0ea960)))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "شرایط استفاده از خدمات",
                                style = TextStyle(color = Color(0xff0ea960)),
                                modifier = Modifier.clickable { context.startActivity(Intent(Intent.ACTION_VIEW, "https://takhfifdare.com/policy".toUri())) })
                            Text(text = " و ")
                            Text(
                                text = "حریم خصوصی",
                                style = TextStyle(color = Color(0xff0ea960)),
                                modifier = Modifier.clickable { context.startActivity(Intent(Intent.ACTION_VIEW, "https://takhfifdare.com/privacy".toUri())) }
                            )
                            Text(text = " را می پذیرم.")
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { viewModel.login() },
                            shape = RoundedCornerShape(4.dp),
                            enabled = viewModel.checked.value
                        ) {
                            Text(text = "ارسال کد", fontSize = 16.sp, color = Color.White)
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
                            label = { Text(text = "کد ارسالی", color = Color.Black) },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { viewModel.confirmCode() },
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(text = "ورود", fontSize = 16.sp, color = Color.White)
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