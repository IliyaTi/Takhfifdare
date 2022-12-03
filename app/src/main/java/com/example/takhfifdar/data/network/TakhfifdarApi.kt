package com.example.takhfifdar.data.network

import com.example.takhfifdar.data.network.objects.LoginBody
import com.example.takhfifdar.data.network.objects.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TakhfifdarApi {

    @POST("api/api/login")
    suspend fun login(@Body body: LoginBody): Response<LoginResponse>

}