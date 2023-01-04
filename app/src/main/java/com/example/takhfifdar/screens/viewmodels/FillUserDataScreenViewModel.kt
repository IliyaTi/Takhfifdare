package com.example.takhfifdar.screens.viewmodels

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FillUserDataScreenViewModel: ViewModel() {

    private val firstName = mutableStateOf("")
    private val lastName = mutableStateOf("")
    private val username = mutableStateOf("")
    private val phoneNumber = mutableStateOf("")
    private val email = mutableStateOf("")

    private val firstNameValid = mutableStateOf(Pair(true, ""))
    private val lastNameValid = mutableStateOf(Pair(true, ""))
    private val usernameValid = mutableStateOf(Pair(true, ""))
    private val phoneNumberValid = mutableStateOf(Pair(true, ""))
    private val emailValid = mutableStateOf(Pair(true, ""))

    fun submit() {
        val isValid = formValidation()
        if (!isValid) return


    }


    private fun formValidation(): Boolean {
        if (firstName.value.isEmpty()) {
            firstNameValid.value = Pair(false, "الزامی است")
            return false
        }
        if (lastName.value.isEmpty()) {
            lastNameValid.value = Pair(false, "الزامی است")
            return false
        }
        if (username.value.isEmpty()) {
            usernameValid.value = Pair(false, "الزامی است")
            return false
        }
        if (phoneNumber.value.isEmpty()) {
            phoneNumberValid.value = Pair(false, "الزامی است")
            return false
        }

        if (phoneNumber.value.length != 11) {
            phoneNumberValid.value = Pair(false, "شماره وارد شده صحیح نمیباشد")
            return false
        }


        if (email.value.isNotEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                emailValid.value = Pair(false, "ایمیل وارد شده صحیح نمیباشد")
                return false
            }
        }

        return true
    }

}