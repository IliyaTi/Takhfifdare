package com.example.takhfifdar.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.takhfifdar.R

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel) {

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
                    Button(onClick = {
                        try {
                            viewModel.login()
                            navController.navigate("HomeScreen/${viewModel.loggedInUser.value!!.id}")
                        } catch (e: Exception) {
                            e.localizedMessage?.let { Log.e("fuck", it) }
                        }

                    }, shape = RoundedCornerShape(4.dp)) {
                        Text(text = "ورود", fontSize = 16.sp)
                    }
                }


            }
        }


    }
    if (viewModel.loadingState.value) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
        ){
            CircularProgressIndicator()
        }
    }


}