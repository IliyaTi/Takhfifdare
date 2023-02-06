package com.example.takhfifdar.screens

import android.view.RoundedCorner
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
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

    var monthPickerExp by remember { mutableStateOf(false) }


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
                modifier = Modifier
                    .layoutId("form")
                    .verticalScroll(
                        state = scrollState,
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row (modifier = Modifier.weight(1f)){
                        Text(text = "تاریخ تولد")
                        Text(text = "*", color = Color.Red)
                    }

                    OutlinedTextField(
                        value = viewModel.dayPicker.value ?: "",
                        onValueChange = { viewModel.dayPicker.value = it },
                        label = { Text("روز") },
                        modifier = Modifier.weight(1f)
                    )
                    Box {
                        OutlinedButton(onClick = { monthPickerExp = !monthPickerExp }, Modifier.height(IntrinsicSize.Max)) {
                            Text(text = viewModel.monthPicker.value, color = Color.Blue)
                        }
                        DropdownMenu(
                            expanded = monthPickerExp,
                            onDismissRequest = { monthPickerExp = false }) {
                            viewModel.months.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    monthPickerExp = false
                                    viewModel.monthPicker.value = label
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }
                    OutlinedTextField(
                        value = viewModel.yearPicker.value ?: "",
                        onValueChange = { viewModel.yearPicker.value = it },
                        modifier = Modifier.weight(1f),
                        label = {
                            Text(text = "سال")
                        })

                }
                if (!viewModel.birthDateValid.value.first) Text(viewModel.birthDateValid.value.second, color = Color.Red)

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
                Text(text = "ثبت اطلاعات", fontSize = 16.sp, color = Color.White)
            }

            // Loading indicator
            if (viewModel.loadingState.value)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {  }
                        .background(Color(0x80000000)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }

        }
    }
}