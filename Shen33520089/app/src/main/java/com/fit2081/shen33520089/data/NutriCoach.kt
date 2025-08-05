package com.fit2081.shen33520089.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a NutriCoach tip entity in the database.
 * This entity is used to store tips provided by the NutriCoach feature.
 */
@Entity(tableName = "NutriCoachTips")
data class NutriCoach(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val message: String = ""
)