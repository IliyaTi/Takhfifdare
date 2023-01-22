package com.example.takhfifdar.data.repositories.remote.network

import com.example.takhfifdar.data.repositories.remote.network.objects.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TakhfifdarApi {

    @POST("api/api/login")
    suspend fun login(@Body body: LoginBody): Response<LoginResponse>

    @POST("api/api/reaction")
    suspend fun sendFeedback(@Body body: FeedbackBody, @Header("Authorization") header: String): Response<FeedbackResponse>

    @POST("api/api/qr")
    suspend fun spendCredit(@Header("Authorization") token: String, @Body body: QrBody): Response<QrResponse>

    @POST("api/api/confirm-code")
    suspend fun confirmCode(@Body body: ConfirmCodeBody): Response<ConfirmCodeResponse>

    @Headers("Accept: application/json")
    @POST("api/api/edit-profile")
    suspend fun editProfile(@Header("Authorization") token: String, @Body body: EditProfileBody): Response<EditProfileResponse>

//    @Headers("Accept: application/json")
//    @GET("api/api/test")
//    suspend fun test()

    @Headers("Accept: application/json")
    @POST("api/api/refahPayment")
    suspend fun refahPayment()

    @Headers("Accept: application/json")
    @POST("api/api/payment/discount")
    suspend fun checkDiscountCode(@Header("Authorization") token: String, @Body body: DiscountBody): Response<DiscountResponse>

}