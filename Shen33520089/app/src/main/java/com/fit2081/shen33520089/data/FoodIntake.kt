package com.fit2081.shen33520089.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user entity in the database.
 */
@Entity(tableName = "food_intake")
data class FoodIntake(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
    val fruitsChecked: Boolean,
    val vegetablesChecked: Boolean,
    val grainsChecked: Boolean,
    val redMeatChecked: Boolean,
    val seafoodChecked: Boolean,
    val poultryChecked: Boolean,
    val fishChecked: Boolean,
    val eggsChecked: Boolean,
    val nutsSeedsChecked: Boolean,
    val selectedPersona: String = "Select your persona",
    val mealTime: String = "00:00",
    val sleepTime: String = "00:00",
    val wakeTime: String = "00:00",
    val questionnaireCompleted: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)