package com.fit2081.shen33520089.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NutriCoachDao {
    // Inserts a new user into the database.
    //suspend is a coroutine function that can be paused and resumed at a later time.
    //suspend is used to indicate that the function will be called from a coroutine.
    @Insert
    suspend fun insert(nutriCoach: NutriCoach)

    // Updates an existing user in the database.
    @Update
    suspend fun update(nutriCoach: NutriCoach)

    // Deletes a specific user from the database.
    @Delete
    suspend fun delete(nutriCoach: NutriCoach)

    // Deletes all users from the database.
    @Query("DELETE FROM NutriCoachTips")
    suspend fun deleteAllMessages()

    // Retrieves all users from the database, ordered by their ID in ascending order.
    //The return type is a Flow, which is a data stream that can be observed for changes.
    @Query("SELECT * FROM NutriCoachTips")
    fun getAllMessages(): Flow<List<NutriCoach>>

    @Query("SELECT * FROM NutriCoachTips WHERE userId = :userId")
    fun getMessagesByUserId(userId: String): Flow<List<NutriCoach>>
}

