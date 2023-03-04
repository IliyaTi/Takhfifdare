package com.example.takhfifdar.screens.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.local.database.Transaction
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.QrBody
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class QrCodeScannerViewModel(application: Application) : AndroidViewModel(application) {

    var code =
        mutableStateOf("")

    val loadingState =  mutableStateOf(false)

    var storeId = mutableStateOf(0)
    var storeName = mutableStateOf("")

    val database = TakhfifdarDatabase.getDatabase(application)

    fun useCredit(storeId: Int, vendorObj: JSONObject): Job {
        return viewModelScope.launch {
            try {
                val req = RetrofitInstance.api.spendCredit(
                    "Bearer " + database.TokenDao().getToken().token,
                    QrBody(storeId, TakhfifdareApplication.loggedInUser.value!!.id)
                )
                if (req.isSuccessful) {
                    TakhfifdareApplication.loggedInUser.value!!.credit = req.body()?.user?.credit ?: "0"
                    database.UserDao().updateUser(TakhfifdareApplication.loggedInUser.value!!)

                    // TODO: Transactions not right yet // just for testing purposes
                    database.TransactionDao().insert(
                        Transaction(
                            billSerial = Random.nextInt(from = 100000, until = 999999).toString(),
                            vendorPhone = "123456789",
                            vendorName = req.body()!!.storeName,
                            date = SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().time),
                            customerName = TakhfifdareApplication.loggedInUser.value!!.first_name + " " + TakhfifdareApplication.loggedInUser.value!!.last_name,
                            discountPercent = req.body()!!.discount
                        )
                    )

                    Navigator.navigateTo(
                        navTarget = NavTarget.FeedbackScreen,
                        args = vendorObj.toString() + "~" + req.body()?.storeImage?.replace("/", "*") + "~" + req.body()?.discount
                    )
                } else {
                    if (req.code() >= 500) {
                        Toast.makeText(getApplication(), "مشکلی در سرور های ما بوجود آمده", Toast.LENGTH_LONG).show()
                    }
                    else if (req.code() == 401) {
                        Navigator.navigateTo(NavTarget.HomeScreen)
                        Toast.makeText(getApplication(), "متاسفانه اعتبار شما به پایان رسیده", Toast.LENGTH_LONG).show()
                    } else if (req.code() == 405) {
                        Toast.makeText(getApplication(), "لطفا دوباره ورود نمایید", Toast.LENGTH_LONG).show()
                        Navigator.navigateTo(NavTarget.LoginScreen)
                    }
                }
            } catch (e: SocketTimeoutException) {
                Toast.makeText(getApplication(), "جوابی دریافت نشد", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(getApplication(), "لطفا دوباره ورود نمایید", Toast.LENGTH_LONG).show()
                Navigator.navigateTo(NavTarget.LoginScreen)
            }
        }
    }



}