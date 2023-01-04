package com.example.takhfifdar.data.repositories.remote.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

//        private val client by lazy {
//            OkHttpClient.Builder().addInterceptor(Interceptor {
//                val newRequest = it.request().newBuilder()
//                    .addHeader("Authorization", "Bearer ",  token)
//                    .build()
//                return@Interceptor it.proceed(newRequest)
//            })
//        }


        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://takhfifdare.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(TakhfifdarApi::class.java)
        }

    }


}