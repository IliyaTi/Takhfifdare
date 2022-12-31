package com.example.takhfifdar

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.example.takhfifdar.data.repositories.local.database.User

class TakhfifdareApplication : Application() {
    companion object {
        var loggedInUser = mutableStateOf<User?>(null)
    }



}