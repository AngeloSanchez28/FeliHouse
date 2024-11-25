package com.upao.felihouse.ui.Connection

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiService {

    private const val BASE_URL = "https://1ccb-132-184-131-183.ngrok-free.app" // Cambia por tu IP o URL de ngrok

    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()) // Para manejar texto plano
            .addConverterFactory(GsonConverterFactory.create())    // Para manejar JSON
            .client(client)
            .build()
    }

    val apiEndpoint: ApiEndpoint by lazy {
        retrofit.create(ApiEndpoint::class.java)
    }
}
