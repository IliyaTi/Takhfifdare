package com.example.takhfifdar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.takhfifdar.screens.viewmodels.FillUserDataScreenViewModel

@Composable()
fun FillUserDataScreen(viewModel: FillUserDataScreenViewModel) {

    // Loading indicator
    if (viewModel.loadingState.value)
    Column(modifier = Modifier.fillMaxSize().background(Color(0x80000000)), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "وارد کردن موارد ستاره دار الزامی میباشند",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = viewModel.firstName.value ?: "",
                onValueChange = { viewModel.firstName.value = it },
                label = {
                    Row {
                        Text(text = "نام")
                        Text(text = "*", color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (!viewModel.firstNameValid.value.first) Text(
                text = viewModel.firstNameValid.value.second,
                color = Color.Red
            )
            OutlinedTextField(
                value = viewModel.lastName.value ?: "",
                onValueChange = { viewModel.lastName.value = it },
                label = {
                    Row {
                        Text(text = "نام خانوادگی")
                        Text(text = "*", color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (!viewModel.lastNameValid.value.first) Text(
                text = viewModel.lastNameValid.value.second,
                color = Color.Red
            )
            OutlinedTextField(
                value = viewModel.username.value ?: "",
                onValueChange = { viewModel.username.value = it },
                label = {
                    Row {
                        Text(text = "نام کاربری")
                        Text(text = "*", color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (!viewModel.usernameValid.value.first) Text(
                text = viewModel.usernameValid.value.second,
                color = Color.Red
            )
            OutlinedTextField(
                value = viewModel.phoneNumber.value ?: "",
                onValueChange = { viewModel.phoneNumber.value = it },
                label = {
                    Row {
                        Text(text = "شماره تلفن")
                        Text(text = "*", color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (!viewModel.phoneNumberValid.value.first) Text(
                text = viewModel.phoneNumberValid.value.second,
                color = Color.Red
            )

            OutlinedTextField(
                value = viewModel.email.value ?: "",
                onValueChange = { viewModel.email.value = it },
                label = { Text(text = "ایمیل") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.city.value ?: "",
                onValueChange = { viewModel.city.value = it },
                label = {
                    Row {
                        Text(text = "شهر")
                        Text(text = "*", color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (!viewModel.cityValid.value.first) Text(
                text = viewModel.cityValid.value.second,
                color = Color.Red
            )

            OutlinedTextField(
                value = viewModel.birthDate.value ?: "",
                onValueChange = { viewModel.birthDate.value = it },
                label = {
                    Row {
                        Text(text = "تاریخ تولد")
                        Text(text = "*", color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (!viewModel.birthDateValid.value.first) Text(
                text = viewModel.birthDateValid.value.second,
                color = Color.Red
            )

            Button(onClick = {
                viewModel.submit()
            }) {
                Text(text = "ثبت")
            }
        }
    }
}