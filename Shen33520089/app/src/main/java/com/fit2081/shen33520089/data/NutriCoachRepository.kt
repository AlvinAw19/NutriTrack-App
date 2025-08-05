package com.fit2081.shen33520089.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class NutriCoachRepository {

    // Property to hold the UserDao instance.
    var nutriCoachDao: NutriCoachDao
    // Constructor to initialize the UserDao.
    constructor(context: Context) {
        // Get the UserDao instance from the AppDatabase.
        nutriCoachDao = AppDatabase.getDatabase(context).nutriCoachDao()
    }
    // Function to insert a user into the database.
    suspend fun insert(nutriCoach: NutriCoach) {
        // Call the insert function from the UserDao.
        nutriCoachDao.insert(nutriCoach)
    }
    // Function to delete a user from the database.
    suspend fun delete(nutriCoach: NutriCoach) {
        // Call the delete function from the UserDao.
        nutriCoachDao.delete(nutriCoach)
    }
    // Function to update a user in the database.
    suspend fun update(nutriCoach: NutriCoach) {
        // Call the update function from the UserDao.
        nutriCoachDao.update(nutriCoach)
    }
    // Function to delete all users from the database.
    suspend fun deleteAllMessages() {
        // Call the deleteAllUsers function from the UserDao.
        nutriCoachDao.deleteAllMessages()
    }
    // Function to get all users from the database as a Flow.
    fun getAllMessages():   Flow<List<NutriCoach>> = nutriCoachDao.getAllMessages()

    fun getMessagesByUserId(userId: String): Flow<List<NutriCoach>> {
        return nutriCoachDao.getMessagesByUserId(userId)
    }
}
