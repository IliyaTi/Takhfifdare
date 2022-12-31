package com.example.takhfifdar.screens.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.FeedbackBody
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import kotlinx.coroutines.async

class FeedbackScreenViewModel(application: Application) : AndroidViewModel(application) {

    val loadingState = mutableStateOf(false)

    val openDialog = mutableStateOf(false)

    val selectedTab = mutableStateOf(1)

    val selectedRate = mutableStateOf(0)

    val comment = mutableStateOf("")

    var posFeedBack = mutableStateListOf(
        Pair("رفتار محترمانه", false),
        Pair("ظاهر آراسته", false),
        Pair("حفظ حریم شخصی", false),
        Pair("محیط شیک و تمیز", false),
        Pair("رعایت اصول بهداشتی", false),
        Pair("رضایت از میزان تخفیف", false),
        Pair("سرعت رسیدگی", false),
        Pair("مشاوره و راهنمایی مناسب", false),
    )

    var negFeedBack = mutableStateListOf(
        Pair("رفتار نامناسب", false),
        Pair("ظاهر نامناسب", false),
        Pair("عدم حفظ حریم شخصی", false),
        Pair("شرایط نامناسب فروشگاه", false),
        Pair("عدم رعایت اصول بهداشتی", false),
        Pair("عدم رضایت از میزان تخفیف", false),
    )

    suspend fun sendFeedback(storeId: Int, name: String) {
        try {
            loadingState.value = true
            val userId = viewModelScope.async {
                TakhfifdarDatabase.getDatabase(getApplication<Application>().applicationContext)
                    .UserDao().getUser()
            }
            val positive = posFeedBack.count { it.second }
            val negative = negFeedBack.count { it.second }
            val req = viewModelScope.async {
                RetrofitInstance.api.sendFeedback(
                    FeedbackBody(
                        storeId = storeId,
                        userId = userId.await().id,
                        positive = positive,
                        negative = negative,
                        reaction = selectedRate.value,
                        comment = comment.value
                    ),
                    "Bearer " + TakhfifdarDatabase.getDatabase(getApplication<Application>().applicationContext).TokenDao().getToken().token
                )
            }
            val res = req.await()
            if (res.isSuccessful) {
                loadingState.value = false
                println("NETWORK RESPONSE RECEIVED SUCCESSFULLY!")
                Toast.makeText(getApplication(), "بازخورد شما با موفقیت ثبت شد", Toast.LENGTH_LONG)
                    .show()
                Navigator.navigateTo(NavTarget.HomeScreen, "")
            } else {
                loadingState.value = false
                println("NETWORK RESPONSE NOT RECEIVED!!!")
                if (res.code() >= 500) {
                    Toast.makeText(
                        getApplication(),
                        "مشکلی در سرور به وجود آمده، لطفا بعدا سعی کنید",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (res.code() >= 400) {
                    Toast.makeText(getApplication(), "اشکال در سمت شما", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            loadingState.value = false
            Log.e("fuck", e.message.toString())
            e.printStackTrace()
            Toast.makeText(getApplication(), "مشکل ناشناسی رخ داده", Toast.LENGTH_LONG).show()
        }


    }


}