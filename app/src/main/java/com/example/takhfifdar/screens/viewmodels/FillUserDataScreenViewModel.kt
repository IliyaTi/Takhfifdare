package com.example.takhfifdar.screens.viewmodels

import android.app.Application
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takhfifdar.TakhfifdareApplication
import com.example.takhfifdar.data.repositories.local.database.TakhfifdarDatabase
import com.example.takhfifdar.data.repositories.local.database.User
import com.example.takhfifdar.data.repositories.remote.network.RetrofitInstance
import com.example.takhfifdar.data.repositories.remote.network.objects.EditProfileBody
import com.example.takhfifdar.navigation.NavTarget
import com.example.takhfifdar.navigation.Navigator
import kotlinx.coroutines.launch

class FillUserDataScreenViewModel(application: Application) : AndroidViewModel(application) {

    val database = TakhfifdarDatabase.getDatabase(getApplication())

    val loadingState = mutableStateOf(false)

    val months = listOf("ماه", "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند")

    val userBD = TakhfifdareApplication.loggedInUser.value!!.birth_date

    val dayPicker = mutableStateOf(userBD?.split("/")?.get(2))
    val monthPicker = mutableStateOf(months.get(userBD?.split("/")?.get(1)?.toInt() ?: 0))
    val yearPicker = mutableStateOf(userBD?.split("/")?.get(0))

    val firstName = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.first_name)
    val lastName = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.last_name)
    val username = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.name)
    val phoneNumber = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.phone)
    val email = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.email)
    val city = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.city)

    val firstNameValid = mutableStateOf(Pair(true, ""))
    val lastNameValid = mutableStateOf(Pair(true, ""))
    val usernameValid = mutableStateOf(Pair(true, ""))
    val phoneNumberValid = mutableStateOf(Pair(true, ""))
    val emailValid = mutableStateOf(Pair(true, ""))
    val cityValid = mutableStateOf(Pair(true, ""))
    val birthDateValid = mutableStateOf(Pair(true, ""))

    fun submit() {
        val isValid = formValidation()
        if (!isValid) return

        viewModelScope.launch {
            loadingState.value = true
            if (dayPicker.value!!.length == 1) dayPicker.value = '0' + dayPicker.value!!
            val date = yearPicker.value + "/" + months.indexOf(monthPicker.value) + "/" + dayPicker.value
            println(date)
            try {
                val res = RetrofitInstance.api.editProfile(
                    "Bearer " + database.TokenDao().getToken().token,
                    EditProfileBody(
                        firstName.value ?: "",
                        lastName.value ?: "",
                        username.value ?: "",
                        email.value ?: "",
                        phoneNumber.value ?: "",
                        city.value ?: "",
                        date
                    )
                )
                if (res.isSuccessful) {
                    println(res.body())
                    val newUser = User(
                        id = TakhfifdareApplication.loggedInUser.value!!.id,
                        first_name = firstName.value,
                        last_name = lastName.value,
                        name = username.value,
                        phone = phoneNumber.value,
                        birth_date = date,
                        credit = TakhfifdareApplication.loggedInUser.value!!.credit,
                        image = TakhfifdareApplication.loggedInUser.value!!.image,
                        email = email.value,
                        city = city.value,
                        invite_code = TakhfifdareApplication.loggedInUser.value!!.invite_code
                    )
                    TakhfifdareApplication.loggedInUser.value = newUser
                    database.UserDao().deleteUsers()
                    database.UserDao().addUser(newUser)
                    loadingState.value = false
                    Toast.makeText(getApplication(), "تغییرات با موفقیت ذخیره شد", Toast.LENGTH_LONG).show()
                    Navigator.navigateTo(NavTarget.HomeScreen)
                } else if (res.code() == 401) {
                    loadingState.value = false
                    Toast.makeText(getApplication(), "زمان این نشست به پایان رسیده، لطفا دوباره وارد شوید.", Toast.LENGTH_LONG).show()
                    Navigator.navigateTo(NavTarget.LoginScreen)
                } else {
                    loadingState.value = false
                    Toast.makeText(getApplication(), "خطا: لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                loadingState.value = false
                Toast.makeText(getApplication(), "مشکل ناشناخته ای رخ داد", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }


        }

    }


    private fun formValidation(): Boolean {
        if (firstName.value.isNullOrBlank()) {
            firstNameValid.value = Pair(false, "الزامی است")
            return false
        } else {
            firstNameValid.value = Pair(true, "")
        }
        if (lastName.value.isNullOrBlank()) {
            lastNameValid.value = Pair(false, "الزامی است")
            return false
        } else {
            lastNameValid.value = Pair(true, "")
        }
        if (username.value.isNullOrBlank()) {
            usernameValid.value = Pair(false, "الزامی است")
            return false
        } else {
            usernameValid.value = Pair(true, "")
        }
        if (phoneNumber.value.isNullOrBlank()) {
            phoneNumberValid.value = Pair(false, "الزامی است")
            return false
        } else {
            phoneNumberValid.value = Pair(true, "")
        }

        if (phoneNumber.value.isNullOrBlank()) {
            phoneNumberValid.value = Pair(false, "الزامی است")
            return false
        } else {
            phoneNumberValid.value = Pair(true, "")
        }
        if (city.value.isNullOrBlank()) {
            cityValid.value = Pair(false, "الزامی است")
            return false
        } else {
            cityValid.value = Pair(true, "")
        }
        if (dayPicker.value!!.isBlank() || yearPicker.value!!.isBlank()) {
            birthDateValid.value = Pair(false, "الزامی است")
            return false
        } else if (monthPicker.value == months[0]) {
            birthDateValid.value = Pair(false, "ماه را وارد کنید")
            return false
        } else if (yearPicker.value!!.length != 4) {
            birthDateValid.value = Pair(false, "سال را کامل وارد کنید")
            return false
        } else {
            birthDateValid.value = Pair(true, "")
        }

//        if (email.value.isNullOrBlank()) {
//            if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
//                emailValid.value = Pair(false, "ایمیل وارد شده صحیح نمیباشد")
//                return false
//            }
//        }

        return true
    }

}