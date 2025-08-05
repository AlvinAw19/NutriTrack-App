package com.fit2081.shen33520089.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// This interface defines the data access object (DAO) for the User entity.
@Dao
interface PatientDao {
    // Inserts a new user into the database.
    //suspend is a coroutine function that can be paused and resumed at a later time.
    //suspend is used to indicate that the function will be called from a coroutine.
    @Insert
    suspend fun insert(patient: Patient)

    // Updates an existing user in the database.
    @Update
    suspend fun update(patient: Patient)

    // Deletes a specific user from the database.
    @Delete
    suspend fun delete(patient: Patient)

    // Deletes all users from the database.
    @Query("DELETE FROM patient")
    suspend fun deleteAllPatients()

    // Retrieves all users from the database, ordered by their ID in ascending order.
    //The return type is a Flow, which is a data stream that can be observed for changes.
    @Query("SELECT * FROM patient")
    fun getAllPatients(): Flow<List<Patient>>

    // Retrieves a specific user by ID from the database as a Flow.
    @Query("SELECT * FROM patient WHERE userId = :userId")
    fun getPatientById(userId: String): Flow<Patient?>

    // Queries to get various statistics about patient scores
    @Query("SELECT AVG(heifaTotalScore) FROM patient WHERE gender = 'Male'")
    fun getAverageHeifaScoreMale(): Flow<Float?>

    @Query("SELECT AVG(heifaTotalScore) FROM patient WHERE gender = 'Female'")
    fun getAverageHeifaScoreFemale(): Flow<Float?>

    @Query("SELECT MAX(discretionaryScore) FROM patient")
    fun getMaxDiscretionaryScore(): Flow<Float?>
    @Query("SELECT MIN(discretionaryScore) FROM patient")
    fun getMinDiscretionaryScore(): Flow<Float?>
    @Query("SELECT AVG(discretionaryScore) FROM patient")
    fun getAvgDiscretionaryScore(): Flow<Float?>
    @Query("SELECT MAX(vegetablesScore) FROM patient")
    fun getMaxVegetablesScore(): Flow<Float?>
    @Query("SELECT MIN(vegetablesScore) FROM patient")
    fun getMinVegetablesScore(): Flow<Float?>
    @Query("SELECT AVG(vegetablesScore) FROM patient")
    fun getAvgVegetablesScore(): Flow<Float?>
    @Query("SELECT MAX(fruitScore) FROM patient")
    fun getMaxFruitScore(): Flow<Float?>
    @Query("SELECT MIN(fruitScore) FROM patient")
    fun getMinFruitScore(): Flow<Float?>
    @Query("SELECT AVG(fruitScore) FROM patient")
    fun getAvgFruitScore(): Flow<Float?>
    @Query("SELECT MAX(grainsScore) FROM patient")
    fun getMaxGrainsScore(): Flow<Float?>
    @Query("SELECT MIN(grainsScore) FROM patient")
    fun getMinGrainsScore(): Flow<Float?>
    @Query("SELECT AVG(grainsScore) FROM patient")
    fun getAvgGrainsScore(): Flow<Float?>
    @Query("SELECT MAX(wholeGrainsScore) FROM patient")
    fun getMaxWholeGrainsScore(): Flow<Float?>
    @Query("SELECT MIN(wholeGrainsScore) FROM patient")
    fun getMinWholeGrainsScore(): Flow<Float?>
    @Query("SELECT AVG(wholeGrainsScore) FROM patient")
    fun getAvgWholeGrainsScore(): Flow<Float?>
    @Query("SELECT MAX(meatScore) FROM patient")
    fun getMaxMeatScore(): Flow<Float?>
    @Query("SELECT MIN(meatScore) FROM patient")
    fun getMinMeatScore(): Flow<Float?>
    @Query("SELECT AVG(meatScore) FROM patient")
    fun getAvgMeatScore(): Flow<Float?>
    @Query("SELECT MAX(dairyScore) FROM patient")
    fun getMaxDairyScore(): Flow<Float?>
    @Query("SELECT MIN(dairyScore) FROM patient")
    fun getMinDairyScore(): Flow<Float?>
    @Query("SELECT AVG(dairyScore) FROM patient")
    fun getAvgDairyScore(): Flow<Float?>
    @Query("SELECT MAX(sodiumScore) FROM patient")
    fun getMaxSodiumScore(): Flow<Float?>
    @Query("SELECT MIN(sodiumScore) FROM patient")
    fun getMinSodiumScore(): Flow<Float?>
    @Query("SELECT AVG(sodiumScore) FROM patient")
    fun getAvgSodiumScore(): Flow<Float?>
    @Query("SELECT MAX(alcoholScore) FROM patient")
    fun getMaxAlcoholScore(): Flow<Float?>
    @Query("SELECT MIN(alcoholScore) FROM patient")
    fun getMinAlcoholScore(): Flow<Float?>
    @Query("SELECT AVG(alcoholScore) FROM patient")
    fun getAvgAlcoholScore(): Flow<Float?>
    @Query("SELECT MAX(waterScore) FROM patient")
    fun getMaxWaterScore(): Flow<Float?>
    @Query("SELECT MIN(waterScore) FROM patient")
    fun getMinWaterScore(): Flow<Float?>
    @Query("SELECT AVG(waterScore) FROM patient")
    fun getAvgWaterScore(): Flow<Float?>
    @Query("SELECT MAX(sugarScore) FROM patient")
    fun getMaxSugarScore(): Flow<Float?>
    @Query("SELECT MIN(sugarScore) FROM patient")
    fun getMinSugarScore(): Flow<Float?>
    @Query("SELECT AVG(sugarScore) FROM patient")
    fun getAvgSugarScore(): Flow<Float?>
    @Query("SELECT MAX(saturatedFatScore) FROM patient")
    fun getMaxSaturatedFatScore(): Flow<Float?>
    @Query("SELECT MIN(saturatedFatScore) FROM patient")
    fun getMinSaturatedFatScore(): Flow<Float?>
    @Query("SELECT AVG(saturatedFatScore) FROM patient")
    fun getAvgSaturatedFatScore(): Flow<Float?>
    @Query("SELECT MAX(unsaturatedFatScore) FROM patient")
    fun getMaxUnsaturatedFatScore(): Flow<Float?>
    @Query("SELECT MIN(unsaturatedFatScore) FROM patient")
    fun getMinUnsaturatedFatScore(): Flow<Float?>
    @Query("SELECT AVG(unsaturatedFatScore) FROM patient")
    fun getAvgUnsaturatedFatScore(): Flow<Float?>

}

