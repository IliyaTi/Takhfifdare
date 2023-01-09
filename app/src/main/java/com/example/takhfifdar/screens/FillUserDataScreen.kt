package com.example.takhfifdar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.takhfifdar.R
import com.example.takhfifdar.screens.viewmodels.FillUserDataScreenViewModel

@Composable()
fun FillUserDataScreen(viewModel: FillUserDataScreenViewModel) {

    // Loading indicator
    if (viewModel.loadingState.value)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }

    val set = ConstraintSet {
        val header = createRefFor("header")
        val form = createRefFor("form")
        val submit = createRefFor("submit")

        constrain(header) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(submit) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(form) {
            top.linkTo(header.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(submit.top)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ConstraintLayout(set, modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = R.drawable.edit_profile_header),
                contentDescription = "",
                modifier = Modifier.layoutId("header"),
                contentScale = ContentScale.Fit
            )

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier.layoutId("form")
                    .scrollable(
                        state = scrollState,
                        orientation = Orientation.Vertical
                    )
                    .padding(horizontal = 20.dp)
            ) {

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
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
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
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
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

            }

            Button(
                onClick = {
                    viewModel.submit()
                },
                modifier = Modifier
                    .layoutId("submit")
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(text = "ثبت اطلاعات", fontSize = 16.sp)
            }
        }
    }
}