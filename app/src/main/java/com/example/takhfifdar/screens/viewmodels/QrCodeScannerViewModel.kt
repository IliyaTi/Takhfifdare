package com.example.takhfifdar.screens.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.QrBody
import com.example.takhfifdar.data.userdata.LoggedInUser
import com.example.takhfifdar.util.ServerNotRespondingException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class QrCodeScannerViewModel(application: Application) : AndroidViewModel(application) {

    var code =
        mutableStateOf("")

    val loadingState =  mutableStateOf(false)

    var storeId = mutableStateOf(0)
    var storeName = mutableStateOf("")

    val database = TakhfifdarDatabase.getDatabase(application)

    fun useCredit(storeId: Int): Job {
        return viewModelScope.launch {
            try {
                val req = RetrofitInstance.api.spendCredit(
                    "Bearer " + database.TokenDao().getToken().token,
                    QrBody(storeId, database.UserDao().getUser().id)
                )
                if (req.isSuccessful) {
                    val user = req.body()!!.user
                    LoggedInUser.user.value = user
                    database.UserDao().updateUser(user)
                } else {
                    if (req.code() >= 500) throw ServerNotRespondingException()
                    else if (req.code() >= 400) throw com.example.takhfifdar.util.AccessDeniedException()
                }
            } catch (e: SocketTimeoutException) {
                Toast.makeText(getApplication(), "جوابی دریافت نشد", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "مشکل ناشناخته ای رخ داد", Toast.LENGTH_LONG).show()
            }
        }
    }



}