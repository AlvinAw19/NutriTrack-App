package com.fit2081.shen33520089.data.repository

import com.fit2081.shen33520089.data.network.APIService
import com.fit2081.shen33520089.data.network.FruitResponse


class FruitsRepository() {
    private val apiService = APIService.create()

    suspend fun getFruitInfo(fruitName: String): FruitResponse? {
        return try {
            val response = apiService.getFruitInfo(fruitName.lowercase())
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
