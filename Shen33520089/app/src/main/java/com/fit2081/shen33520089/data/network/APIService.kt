package com.fit2081.shen33520089.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("api/fruit/{fruitName}")
    suspend fun getFruitInfo(@Path("fruitName") fruitName: String): Response<FruitResponse>


    companion object {
        var BASE_URL = "https://www.fruityvice.com"

        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(APIService::class.java)

        }
    }
}