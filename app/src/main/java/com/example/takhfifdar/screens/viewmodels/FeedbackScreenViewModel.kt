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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class FeedbackScreenViewModel(application: Application) : AndroidViewModel(application) {

    val loadingState = mutableStateOf(false)

    val openDialog = mutableStateOf(false)

    val selectedTab = mutableStateOf(1)

    val selectedRate = mutableStateOf(0)

    val comment = mutableStateOf("")

    var posFeedBack = mutableStateListOf(
        FeedbackItem("رفتار محترمانه", false, 25f),
        FeedbackItem("ظاهر آراسته", false, 25f),
        FeedbackItem("حفظ حریم شخصی", false, 25f),
        FeedbackItem("محیط شیک و تمیز", false, 25f),
        FeedbackItem("رعایت اصول بهداشتی", false, 25f),
        FeedbackItem("رضایت از میزان تخفیف", false, 25f),
        FeedbackItem("سرعت رسیدگی", false, 25f),
        FeedbackItem("مشاوره و راهنمایی مناسب", false, 25f),
        FeedbackItem("در معرض دید بود QR", false, 50f)
    )

    var negFeedBack = mutableStateListOf(
        FeedbackItem("رفتار نامناسب", false, 31f),
        FeedbackItem("ظاهر نامناسب", false, 31f),
        FeedbackItem("عدم حفظ حریم شخصی", false, 31f),
        FeedbackItem("شرایط نامناسب فروشگاه", false, 31f),
        FeedbackItem("عدم رعایت اصول بهداشتی", false, 31f),
        FeedbackItem("عدم رضایت از میزان تخفیف", false, 31f),
        FeedbackItem("در معرض دید نبود QR", false, 64f),
    )

    suspend fun sendFeedback(storeId: Int) {
        try {
            loadingState.value = true
            val userId = viewModelScope.async {
                TakhfifdarDatabase.getDatabase(getApplication<Application>().applicationContext)
                    .UserDao().getUser()
            }
//            val positive = posFeedBack.count { it.checked }
//            val negative = negFeedBack.count { it.checked }

            val scoreCalc = viewModelScope.async(Dispatchers.IO) {
                var score = 0f
                for (i in 0 until posFeedBack.size){
                    if (posFeedBack[i].checked) score += posFeedBack[i].score
                }
                for (i in 0 until negFeedBack.size){
                    if (negFeedBack[i].checked) score -= negFeedBack[i].score
                }
                return@async score
            }

            val req = viewModelScope.async {
                RetrofitInstance.api.sendFeedback(
                    FeedbackBody(
                        storeId = storeId,
                        userId = userId.await()!!.id,
                        score = scoreCalc.await(),
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

    data class FeedbackItem(
        val msg: String,
        var checked: Boolean,
        val score: Float
    )

}