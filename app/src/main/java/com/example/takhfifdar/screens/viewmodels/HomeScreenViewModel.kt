package com.example.takhfifdar.screens.viewmodels

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.Store
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.QrResponse
import com.example.takhfifdar.data.repositories.remote.network.objects.SerialBody
import com.example.takhfifdar.data.repositories.remote.network.objects.SerialStoreBody
import com.example.takhfifdar.data.repositories.remote.network.objects.SerialStoreResponse
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class HomeScreenViewModel(application: Application, private val launcher: ManagedActivityResultLauncher<String, Boolean>, val activity: Context) : AndroidViewModel(application) {

    val storeSerial = mutableStateOf("")

    fun signOut() {
        viewModelScope.launch {
            TakhfifdarDatabase.getDatabase(getApplication()).UserDao().deleteUsers()
            TakhfifdareApplication.loggedInUser.value = null
            Navigator.navigateTo(NavTarget.HomeScreen)
        }
    }

    fun proceedToScan() {
        if (TakhfifdareApplication.loggedInUser.value == null) {
            Toast.makeText(activity, "لطفا اول وارد شوید", Toast.LENGTH_LONG).show()
            return
        }
        val camPermission =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        if (camPermission != PackageManager.PERMISSION_GRANTED) launcher.launch(
            Manifest.permission.CAMERA
        )
        else Navigator.navigateTo(navTarget = NavTarget.QrScanner)
    }

    fun onSerialButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val store = fetchStore()
            if (store.isSuccessful) {
                val response = spendCreditWithSerial(
                    storeId = store.body()!!.store.id,
                    userId = TakhfifdareApplication.loggedInUser.value!!.id,
                    serial = store.body()!!.store.serial
                )
                if (response.isSuccessful) {
                    TakhfifdareApplication.loggedInUser.value!!.credit = response.body()?.user?.credit ?: "0"
                    TakhfifdarDatabase.getDatabase(getApplication()).UserDao().updateUser(TakhfifdareApplication.loggedInUser.value!!)
                    val vendorObj = JSONObject("{\"id\":${store.body()!!.store.id},\"username\":\"${store.body()!!.store.storeName}\"}")
                    Navigator.navigateTo(
                        navTarget = NavTarget.FeedbackScreen,
                        args = vendorObj.toString() + "~" + response.body()?.storeImage?.replace("/", "*") + "~" + response.body()?.discount
                    )
                }
            }
        }
    }

    private suspend fun spendCreditWithSerial(storeId: Int, userId: Int, serial: String): Response<QrResponse> {
        return RetrofitInstance.api.serial(
            token = "Bearer " + TakhfifdarDatabase.getDatabase(getApplication()).TokenDao().getToken().token,
            body = SerialBody(storeId, userId, serial)
        )
    }

    private suspend fun fetchStore(): Response<SerialStoreResponse> {
        return RetrofitInstance.api.serialStore(
            token = "Bearer " + TakhfifdarDatabase.getDatabase(getApplication()).TokenDao().getToken().token,
            body = SerialStoreBody(storeSerial.value)
        )
    }

}

class HomeScreenViewModelFactory(private val application: Application, private val launcher: ManagedActivityResultLauncher<String, Boolean>, val activity: Activity) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = HomeScreenViewModel(application, launcher, activity) as T
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
//    }
}