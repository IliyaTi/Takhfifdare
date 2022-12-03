package com.example.takhfifdar.screens

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import com.example.takhfifdar.data.database.User
import com.example.takhfifdar.data.network.RetrofitInstance
import com.example.takhfifdar.data.network.objects.LoginBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginScreenViewModel(application: Application): AndroidViewModel(application) {

    val database = TakhfifdarDatabase.getDatabase(application.applicationContext)

    val loadingState = mutableStateOf(false)
    val loggedInUser = mutableStateOf<User?>(null)

    val email = mutableStateOf("")
    val password = mutableStateOf("")


    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingState.value = true
            val res = RetrofitInstance.api.login(LoginBody(email.value, password.value))
            if (res.isSuccessful) {
                val user = res.body()!!.user
                database.UserDao().deleteUsers()
                database.UserDao().addUser(user)
                loggedInUser.value = user

                loadingState.value = false
            } else {
                loadingState.value = false
                throw Exception("req was not successful")
            }
        }
    }

}