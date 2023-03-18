package com.example.takhfifdar.screens.viewmodels

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.local.database.Token
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.ConfirmCodeBody
import com.example.takhfifdar.data.repositories.remote.network.objects.LoginBody
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import com.example.takhfifdar.util.OTPReceiveListener
import com.example.takhfifdar.util.SMSReceiver
import com.example.takhfifdar.util.runOnMain
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginScreenViewModel(val activity: Activity): ViewModel() {

    val database = TakhfifdarDatabase.getDatabase(activity.applicationContext)

    val loadingState = mutableStateOf(false)
    val pageState = mutableStateOf(1)

    val checked = mutableStateOf(false)

    val phoneNumber = mutableStateOf("")
    val otp = mutableStateOf("")
    val otpToken = mutableStateOf("")

    private var intentFilter: IntentFilter? = null
    private var smsReceiver: SMSReceiver? = null


    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingState.value = true
            try {
                val res = RetrofitInstance.api.login(LoginBody(phoneNumber.value))
                if (res.isSuccessful) {
                    otpToken.value = res.body()?.token ?: ""
                    pageState.value = 2
                    initSmsListener()
                    initBroadCast()
                } else {
                    loadingState.value = false
                    if (500 <= res.code())
                        runOnMain { Toast.makeText(activity.applicationContext, res.code().toString() + "مشکلی در سمت سرور های ما وجود دارد", Toast.LENGTH_LONG).show() }
                    else if (400 <= res.code())
                        runOnMain { Toast.makeText(activity.applicationContext, res.code().toString() + "کاربری با این شماره موبایل وجود ندارد", Toast.LENGTH_LONG).show() }
                    else
                        runOnMain { Toast.makeText(activity.applicationContext, res.code().toString() + "خطای ناشناسی رخ داد", Toast.LENGTH_LONG).show() }
                }
                loadingState.value = false
            } catch (e: SocketTimeoutException) {
                loadingState.value = false
                runOnMain { Toast.makeText(activity, e.message + "پاسخی دریافت نشد", Toast.LENGTH_LONG).show() }
            } catch (e: Exception) {
                e.printStackTrace()
                loadingState.value = false
                runOnMain { Toast.makeText(activity, e.message+ "دسترسی به اینترنت برقرار نمیباشد.", Toast.LENGTH_LONG).show() }
            }

        }
    }

    private fun initBroadCast() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsReceiver = SMSReceiver()
        activity.registerReceiver(smsReceiver, intentFilter)
        smsReceiver?.setOTPListener(object : OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                this@LoginScreenViewModel.otp.value = otp ?: ""
                confirmCode()
            }
        })
    }


    private fun initSmsListener() {
        val client = SmsRetriever.getClient(activity)
        client.startSmsRetriever()
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
                    activity.unregisterReceiver(smsReceiver)
                    smsReceiver = null
                    loadingState.value = false
                    Navigator.navigateTo(navTarget = NavTarget.HomeScreen)
                } else {
                    loadingState.value = false
                    if (500 <= res.code())
                        runOnMain { Toast.makeText(activity, res.code().toString() + "مشکلی در سمت سرور های ما وجود دارد", Toast.LENGTH_LONG).show() }
                    else if (400 <= res.code())
                        runOnMain { Toast.makeText(activity, res.code().toString() + "کد وارد شده صحیح نمیباشد و یا منقضی شده است", Toast.LENGTH_LONG).show() }
                    else
                        runOnMain { Toast.makeText(activity, res.code().toString() +"خطای ناشناسی رخ داد", Toast.LENGTH_LONG).show() }
                }
                loadingState.value = false
            } catch (e: SocketTimeoutException) {
                loadingState.value = false
//                runOnMain { Toast.makeText(getApplication(), "پاسخی دریافت نشد", Toast.LENGTH_LONG).show() }
                runOnMain { Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show() }
            } catch (e: SQLiteConstraintException) {
                loadingState.value = false
                e.printStackTrace()
                runOnMain { Toast.makeText(activity, "لطفا جهت استفاده از برنامه، اطلاعات کاربری خود را در سایت تکمیل نمایید", Toast.LENGTH_LONG).show() }
            }
            catch (e: Exception) {
                loadingState.value = false
                e.printStackTrace()
//                runOnMain { Toast.makeText(getApplication(), "دسترسی به اینترنت برقرار نمیباشد.", Toast.LENGTH_LONG).show() }
                runOnMain { Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show() }
            }
        }
    }




}

class LoginScreenViewModelFactory(val activity: Activity) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginScreenViewModel(activity) as T
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
//    }
}