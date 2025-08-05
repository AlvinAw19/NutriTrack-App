package com.fit2081.shen33520089.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class FoodIntakesRepository {

    // Property to hold the UserDao instance.
    var foodIntakeDao: FoodIntakeDao
    // Constructor to initialize the UserDao.
    constructor(context: Context) {
        // Get the UserDao instance from the AppDatabase.
        foodIntakeDao = AppDatabase.getDatabase(context).foodIntakeDao()
    }
    // Function to insert a user into the database.
    suspend fun insert(foodIntake: FoodIntake) {
        // Call the insert function from the UserDao.
        foodIntakeDao.insert(foodIntake)
    }
    // Function to delete a user from the database.
    suspend fun delete(foodIntake: FoodIntake) {
        // Call the delete function from the UserDao.
        foodIntakeDao.delete(foodIntake)
    }
    // Function to update a user in the database.
    suspend fun update(foodIntake: FoodIntake) {
        // Call the update function from the UserDao.
        foodIntakeDao.update(foodIntake)
    }
    // Function to delete all users from the database.
    suspend fun deleteAllFoodIntakes() {
        // Call the deleteAllUsers function from the UserDao.
        foodIntakeDao.deleteAllFoodIntake()
    }
    // Function to get all users from the database as a Flow.
    fun getAllFoodIntakes():   Flow<List<FoodIntake>> = foodIntakeDao.getAllFoodIntake()

    // Function to get a specific user by ID from the database as a Flow.
    fun getFoodIntakeById(userId: String): Flow<FoodIntake?> {
        return foodIntakeDao.getFoodIntakeById(userId)
    }
}
