package com.example.takhfifdar.data.userdata

import androidx.compose.runtime.mutableStateOf
import com.example.takhfifdar.data.repositories.local.database.User

object LoggedInUser {
    val user = mutableStateOf(User())
}