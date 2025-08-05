package com.fit2081.shen33520089.data
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user entity in the database.
 */
@Entity(tableName = "patient")
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
    val phoneNumber: String = "",
    val name: String = "",
    val password: String = "",
    val gender: String = "",
    val heifaTotalScore: Float,
    val discretionaryScore: Float,
    val vegetablesScore: Float,
    val fruitScore: Float,
    val grainsScore: Float,
    val wholeGrainsScore: Float,
    val meatScore: Float,
    val dairyScore: Float,
    val sodiumScore: Float,
    val alcoholScore: Float,
    val waterScore:Float,
    val sugarScore: Float,
    val saturatedFatScore: Float,
    val unsaturatedFatScore: Float,
    val fruitServeSize: Float,
    val fruitVariationScore: Float
    )