package com.example.takhfifdar.data.repositories.remote.network

import com.example.takhfifdar.data.repositories.local.database.Store
import com.example.takhfifdar.data.repositories.local.database.User
import com.example.takhfifdar.data.repositories.remote.network.objects.*
import retrofit2.Response
import retrofit2.http.*

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

    @Headers("Accept: application/json")
    @POST("api/api/userApi")
    suspend fun getUser(@Header("Authorization") token: String, @Body body: GetUserBody): Response<User>

    @Headers("Accept: application/json")
    @POST("api/api/serialStore")
    suspend fun serialStore(@Header("Authorization") token: String, @Body body: SerialStoreBody): Response<SerialStoreResponse>

    @Headers("Accept: application/json")
    @POST("api/api/serial")
    suspend fun serial(@Header("Authorization") token: String, @Body body: SerialBody): Response<QrResponse>

    @Headers("Accept: application/json")
    @GET("api/api/version")
    suspend fun version(): Response<VersionResponse>

    @Headers("Accept: application/json")
    @POST("api/api/invite-code")
    suspend fun inviteCode(@Header("Authorization") token: String, @Body body: InviteCodeBody): Response<InviteCodeResponse>

    @Headers("Accept: application/json")
    @POST("api/api/Score")
    suspend fun buyByScore(body: BuyByScoreBody): Response<BuyByScoreResponse>


}