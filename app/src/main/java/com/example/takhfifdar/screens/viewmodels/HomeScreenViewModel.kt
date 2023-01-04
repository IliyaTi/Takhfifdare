package com.example.takhfifdar.screens.viewmodels

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application, private val launcher: ManagedActivityResultLauncher<String, Boolean>, val activity: Context) : AndroidViewModel(application) {

    fun signOut() {
        viewModelScope.launch {
            TakhfifdarDatabase.getDatabase(getApplication()).UserDao().deleteUsers()
            Navigator.navigateTo(NavTarget.LoginScreen)
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

}

class HomeScreenViewModelFactory(private val application: Application, private val launcher: ManagedActivityResultLauncher<String, Boolean>, val activity: Activity) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = HomeScreenViewModel(application, launcher, activity) as T
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
//    }
}