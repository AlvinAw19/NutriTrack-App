package com.fit2081.shen33520089.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodIntakeDao {
    // Inserts a new user into the database.
    @Insert
    suspend fun insert(foodIntake: FoodIntake)

    // Updates an existing user in the database.
    @Update
    suspend fun update(foodIntake: FoodIntake)

    // Deletes a specific user from the database.
    @Delete
    suspend fun delete(foodIntake: FoodIntake)

    // Deletes all users from the database.
    @Query("DELETE FROM food_intake")
    suspend fun deleteAllFoodIntake()

    // Retrieves all users from the database, ordered by their ID in ascending order.
    //The return type is a Flow, which is a data stream that can be observed for changes.
    @Query("SELECT * FROM food_intake")
    fun getAllFoodIntake(): Flow<List<FoodIntake>>

    @Query("SELECT * FROM food_intake WHERE userId = :userId ORDER BY timestamp DESC LIMIT 1")
    fun getFoodIntakeById(userId: String): Flow<FoodIntake?>
}

