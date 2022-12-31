package com.example.takhfifdar.screens.viewmodels

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.local.database.Token
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.ConfirmCodeBody
import com.example.takhfifdar.data.repositories.remote.network.objects.LoginBody
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.util.runOnMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginScreenViewModel(application: Application): AndroidViewModel(application) {

    val database = TakhfifdarDatabase.getDatabase(application.applicationContext)

    val loadingState = mutableStateOf(false)
    val pageState = mutableStateOf(1)

    val phoneNumber = mutableStateOf("")
    val otp = mutableStateOf("")
    val otpToken = mutableStateOf("")

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingState.value = true
            try {
                val res = RetrofitInstance.api.login(LoginBody(phoneNumber.value))
                if (res.isSuccessful) {
                    otpToken.value = res.body()?.token ?: ""
                    pageState.value = 2
                } else {
                    loadingState.value = false
                    if (500 <= res.code())
                        runOnMain { Toast.makeText(getApplication(), res.code().toString() + "مشکلی در سمت سرور های ما وجود دارد", Toast.LENGTH_LONG).show() }
                    else if (400 <= res.code())
                        runOnMain { Toast.makeText(getApplication(), res.code().toString() + "کاربری با این شماره موبایل وجود ندارد", Toast.LENGTH_LONG).show() }
                    else
                        runOnMain { Toast.makeText(getApplication(), res.code().toString() + "خطای ناشناسی رخ داد", Toast.LENGTH_LONG).show() }
                }
                loadingState.value = false
            } catch (e: SocketTimeoutException) {
                loadingState.value = false
                runOnMain { Toast.makeText(getApplication(), e.message + "پاسخی دریافت نشد", Toast.LENGTH_LONG).show() }
            } catch (e: Exception) {
                e.printStackTrace()
                loadingState.value = false
                runOnMain { Toast.makeText(getApplication(), e.message+ "دسترسی به اینترنت برقرار نمیباشد.", Toast.LENGTH_LONG).show() }
            }

        }
    }

    fun confirmCode() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingState.value = true
            try {
                val res = RetrofitInstance.api.confirmCode(ConfirmCodeBody(otp.value, otpToken.value))
                if (res.isSuccessful) {
                    val user = res.body()!!.user
//                    LoggedInUser.user.value = user
                    TakhfifdareApplication.loggedInUser.value = user
                    database.UserDao().deleteUsers()
                    database.UserDao().addUser(user)
                    database.TokenDao().addToken(Token(1, res.body()!!.token))

                    loadingState.value = false
                    Navigator.navigateTo(navTarget = NavTarget.HomeScreen)
                } else {
                    loadingState.value = false
                    if (500 <= res.code())
                        runOnMain { Toast.makeText(getApplication(), res.code().toString() + "مشکلی در سمت سرور های ما وجود دارد", Toast.LENGTH_LONG).show() }
                    else if (400 <= res.code())
                        runOnMain { Toast.makeText(getApplication(), res.code().toString() + "کد وارد شده صحیح نمیباشد و یا منقضی شده است", Toast.LENGTH_LONG).show() }
                    else
                        runOnMain { Toast.makeText(getApplication(), res.code().toString() +"خطای ناشناسی رخ داد", Toast.LENGTH_LONG).show() }
                }
                loadingState.value = false
            } catch (e: SocketTimeoutException) {
                loadingState.value = false
//                runOnMain { Toast.makeText(getApplication(), "پاسخی دریافت نشد", Toast.LENGTH_LONG).show() }
                runOnMain { Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show() }
            } catch (e: SQLiteConstraintException) {
                loadingState.value = false
                e.printStackTrace()
                runOnMain { Toast.makeText(getApplication(), "لطفا جهت استفاده از برنامه، اطلاعات کاربری خود را در سایت تکمیل نمایید", Toast.LENGTH_LONG).show() }
            }
            catch (e: Exception) {
                loadingState.value = false
                e.printStackTrace()
//                runOnMain { Toast.makeText(getApplication(), "دسترسی به اینترنت برقرار نمیباشد.", Toast.LENGTH_LONG).show() }
                runOnMain { Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show() }
            }
        }
    }

}