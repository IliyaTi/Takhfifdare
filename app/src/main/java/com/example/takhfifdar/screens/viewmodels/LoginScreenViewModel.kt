package com.example.takhfifdar.screens.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.data.database.TakhfifdarDatabase
import com.example.takhfifdar.data.database.User
import com.example.takhfifdar.data.network.RetrofitInstance
import com.example.takhfifdar.data.network.objects.LoginBody
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.util.runOnMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginScreenViewModel(application: Application): AndroidViewModel(application) {

    val database = TakhfifdarDatabase.getDatabase(application.applicationContext)

    val loadingState = mutableStateOf(false)
    val loggedInUser = mutableStateOf<User?>(null)

    val email = mutableStateOf("")
    val password = mutableStateOf("")


    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingState.value = true
            try {
                val res = RetrofitInstance.api.login(LoginBody(email.value, password.value))
                if (res.isSuccessful) {
                    val user = res.body()!!.user
                    database.UserDao().deleteUsers()
                    database.UserDao().addUser(user)
                    loggedInUser.value = user

                    loadingState.value = false
                    Navigator.navigateTo(navTarget = NavTarget.HomeScreen)
                } else {
                    loadingState.value = false
                    if (500 <= res.code())
                        runOnMain { Toast.makeText(getApplication(), "مشکلی در سمت سرور های ما وجود دارد", Toast.LENGTH_LONG).show() }
                    else if (400 <= res.code())
                        runOnMain { Toast.makeText(getApplication(), "آدرس ایمیل یا رمز عبور وارد شده معتبر نمیباشد", Toast.LENGTH_LONG).show() }
                    else
                        runOnMain { Toast.makeText(getApplication(), "خطای ناشناسی رخ داد", Toast.LENGTH_LONG).show() }
                }
            } catch (e: SocketTimeoutException) {
                loadingState.value = false
                runOnMain { Toast.makeText(getApplication(), "پاسخی دریافت نشد", Toast.LENGTH_LONG).show() }
            } catch (e: Exception) {
                loadingState.value = false
                runOnMain { Toast.makeText(getApplication(), "دسترسی به اینترنت برقرار نمیباشد.", Toast.LENGTH_LONG).show() }
            }

        }
    }

}