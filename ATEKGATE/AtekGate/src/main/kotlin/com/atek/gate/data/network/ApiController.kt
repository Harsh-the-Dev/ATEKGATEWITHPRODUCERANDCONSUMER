package com.atek.gate.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiController {

    private const val BASE_URL = "http://10.13.3.8:8080"

    private fun retrofitService(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val apiService: ApiService by lazy {
        retrofitService().create(ApiService::class.java)
    }

}