package com.fit2081.shen33520089.data.network

/**
 * Represents the response model for fruit data from the API.
 */
data class FruitResponse(
    val name: String,
    val id: Int,
    val family: String,
    val order: String,
    val genus: String,
    val nutritions: Nutritions
)

/**
 * Represents the nutritional information of a fruit.
 */
data class Nutritions(
    val calories: Double,
    val fat: Double,
    val sugar: Double,
    val carbohydrates: Double,
    val protein: Double
)