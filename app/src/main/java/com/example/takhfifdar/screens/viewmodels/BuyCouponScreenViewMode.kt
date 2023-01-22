package com.example.takhfifdar.screens.viewmodels

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.DiscountBody
import com.example.takhfifdar.util.NumberUnicodeAdapter
import kotlinx.coroutines.launch

class BuyCouponScreenViewMode(application: Application) : AndroidViewModel(application) {

    val database = TakhfifdarDatabase.getDatabase(application).TokenDao()

    val fullName = TakhfifdareApplication.loggedInUser.value?.first_name?.replaceFirstChar(Char::titlecase) + " " + TakhfifdareApplication.loggedInUser.value?.last_name?.replaceFirstChar(Char::titlecase)

    val discountCode = mutableStateOf("")
    val discountCodeLoading = mutableStateOf(false)
    val discountPercentage = mutableStateOf(0)
    val discountStatus = mutableStateOf("")

    fun checkDiscountValidity() {
        viewModelScope.launch {
            discountCodeLoading.value = true
            val res = RetrofitInstance.api.checkDiscountCode("Bearer " + database.getToken().token, DiscountBody(discountCode.value))
            if (res.isSuccessful) {
                discountStatus.value = NumberUnicodeAdapter().convert(res.body()!!.percent.toString()) + " درصد تخفیف برای شما لحاظ شد"
                discountPercentage.value = res.body()!!.percent!!
                discountCodeLoading.value = false
            } else {
                if (res.code() == 401) {
                    Toast.makeText(getApplication(), "کد وارد شده معتبر نمیباشد", Toast.LENGTH_LONG).show()
                    discountStatus.value = ""
                    discountCodeLoading.value = false
                } else {
                 Toast.makeText(getApplication(), "مشکل ناشناخته ای رخ داد", Toast.LENGTH_LONG).show()
                    discountStatus.value = ""
                    discountCodeLoading.value = false
                }
            }
        }
    }

    fun proceedToGateway(price:) {
        val intent = Intent(Intent.ACTION_VIEW, "https://".toUri())
    }

}