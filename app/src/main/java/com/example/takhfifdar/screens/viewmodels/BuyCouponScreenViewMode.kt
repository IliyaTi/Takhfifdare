package com.example.takhfifdar.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.example.takhfifdar.TakhfifdareApplication

class BuyCouponScreenViewMode : ViewModel() {

    val fullName = TakhfifdareApplication.loggedInUser.value?.first_name?.replaceFirstChar(Char::titlecase) + " " + TakhfifdareApplication.loggedInUser.value?.last_name?.replaceFirstChar(Char::titlecase)

}