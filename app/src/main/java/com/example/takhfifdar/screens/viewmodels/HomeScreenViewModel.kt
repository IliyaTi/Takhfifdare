package com.example.takhfifdar.screens.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import com.example.takhfifdar.data.database.User
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {

    val user = mutableStateOf(User())

    init {
        viewModelScope.launch { user.value = TakhfifdarDatabase.getDatabase(application).UserDao().getUser() }
    }

}