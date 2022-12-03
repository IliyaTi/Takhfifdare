package com.example.takhfifdar

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import com.example.takhfifdar.data.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    val testUsers = mutableStateOf("")

    private val database = TakhfifdarDatabase.getDatabase(application.applicationContext)

    fun getAllUsers() {

    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            database.UserDao().addUser(user)
        }
    }

}