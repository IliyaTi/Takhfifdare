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
import kotlinx.coroutines.launch

class FillUserDataScreenViewModel(application: Application) : AndroidViewModel(application) {

    val database = TakhfifdarDatabase.getDatabase(getApplication())

    val firstName = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.first_name)
    val lastName = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.last_name)
    val username = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.name)
    val phoneNumber = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.phone)
    val email = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.email)
    val city = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.city)
    val birthDate = mutableStateOf(TakhfifdareApplication.loggedInUser.value?.birth_date)

    val firstNameValid = mutableStateOf(Pair(true, ""))
    val lastNameValid = mutableStateOf(Pair(true, ""))
    val usernameValid = mutableStateOf(Pair(true, ""))
    val phoneNumberValid = mutableStateOf(Pair(true, ""))
    val emailValid = mutableStateOf(Pair(true, ""))

    fun submit() {
        val isValid = formValidation()
        if (!isValid) return

        viewModelScope.launch {
            try {
                val res = RetrofitInstance.api.editProfile(
                    "Bearer " + database.TokenDao().getToken().token,
                    EditProfileBody(firstName.value ?: "", lastName.value ?: "", username.value ?: "", email.value ?: "", phoneNumber.value ?: "", city.value ?: "", birthDate.value ?: "")
                )
                if (res.isSuccessful) {
                    println(res.body())
                    val newUser = User(
                        id = 1,
                        first_name = firstName.value,
                        last_name = lastName.value,
                        name = username.value,
                        phone = phoneNumber.value,
                        birth_date = birthDate.value,
                        credit = TakhfifdareApplication.loggedInUser.value!!.credit,
                        image = TakhfifdareApplication.loggedInUser.value!!.image,
                        email = email.value,
                        city = city.value
                    )
                    TakhfifdareApplication.loggedInUser.value = newUser
                    database.UserDao().updateUser(newUser)
                } else {
                    Toast.makeText(getApplication(), "خطا: لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show()
                }

                println("the end")
            } catch (e: Exception) {
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


//        if (email.value.isNullOrBlank()) {
//            if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
//                emailValid.value = Pair(false, "ایمیل وارد شده صحیح نمیباشد")
//                return false
//            }
//        }

        return true
    }

}