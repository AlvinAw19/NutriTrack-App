package com.fit2081.shen33520089.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class PatientsRepository {

    // Property to hold the UserDao instance.
    var patientDao: PatientDao
    // Constructor to initialize the UserDao.
    constructor(context: Context) {
        // Get the UserDao instance from the AppDatabase.
        patientDao = AppDatabase.getDatabase(context).userDao()
    }
    // Function to insert a user into the database.
    suspend fun insert(patient: Patient) {
        // Call the insert function from the UserDao.
        patientDao.insert(patient)
    }
    // Function to delete a user from the database.
    suspend fun delete(patient: Patient) {
        // Call the delete function from the UserDao.
        patientDao.delete(patient)
    }
    // Function to update a user in the database.
    suspend fun update(patient: Patient) {
        // Call the update function from the UserDao.
        patientDao.update(patient)
    }
    // Function to delete all users from the database.
    suspend fun deleteAllPatients() {
        // Call the deleteAllUsers function from the UserDao.
        patientDao.deleteAllPatients()
    }
    // Function to get all users from the database as a Flow.
    fun getAllPatients():   Flow<List<Patient>> = patientDao.getAllPatients()

    // Function to get a specific user by ID from the database as a Flow.
    fun getPatientById(userId: String): Flow<Patient?> {
        return patientDao.getPatientById(userId)
    }

    // Function to get the statistics for all patients.
    fun getAverageHeifaScoreMale(): Flow<Float?> {
        return patientDao.getAverageHeifaScoreMale()
    }

    fun getAverageHeifaScoreFemale(): Flow<Float?> {
        return patientDao.getAverageHeifaScoreFemale()
    }
    fun getMaxDiscretionaryScore() = patientDao.getMaxDiscretionaryScore()
    fun getMinDiscretionaryScore() = patientDao.getMinDiscretionaryScore()
    fun getAvgDiscretionaryScore() = patientDao.getAvgDiscretionaryScore()
    fun getMaxVegetablesScore() = patientDao.getMaxVegetablesScore()
    fun getMinVegetablesScore() = patientDao.getMinVegetablesScore()
    fun getAvgVegetablesScore() = patientDao.getAvgVegetablesScore()
    fun getMaxFruitScore() = patientDao.getMaxFruitScore()
    fun getMinFruitScore() = patientDao.getMinFruitScore()
    fun getAvgFruitScore() = patientDao.getAvgFruitScore()
    fun getMaxGrainsScore() = patientDao.getMaxGrainsScore()
    fun getMinGrainsScore() = patientDao.getMinGrainsScore()
    fun getAvgGrainsScore() = patientDao.getAvgGrainsScore()
    fun getMaxWholeGrainsScore() = patientDao.getMaxWholeGrainsScore()
    fun getMinWholeGrainsScore() = patientDao.getMinWholeGrainsScore()
    fun getAvgWholeGrainsScore() = patientDao.getAvgWholeGrainsScore()
    fun getMaxMeatScore() = patientDao.getMaxMeatScore()
    fun getMinMeatScore() = patientDao.getMinMeatScore()
    fun getAvgMeatScore() = patientDao.getAvgMeatScore()
    fun getMaxDairyScore() = patientDao.getMaxDairyScore()
    fun getMinDairyScore() = patientDao.getMinDairyScore()
    fun getAvgDairyScore() = patientDao.getAvgDairyScore()
    fun getMaxSodiumScore() = patientDao.getMaxSodiumScore()
    fun getMinSodiumScore() = patientDao.getMinSodiumScore()
    fun getAvgSodiumScore() = patientDao.getAvgSodiumScore()
    fun getMaxAlcoholScore() = patientDao.getMaxAlcoholScore()
    fun getMinAlcoholScore() = patientDao.getMinAlcoholScore()
    fun getAvgAlcoholScore() = patientDao.getAvgAlcoholScore()
    fun getMaxWaterScore() = patientDao.getMaxWaterScore()
    fun getMinWaterScore() = patientDao.getMinWaterScore()
    fun getAvgWaterScore() = patientDao.getAvgWaterScore()
    fun getMaxSugarScore() = patientDao.getMaxSugarScore()
    fun getMinSugarScore() = patientDao.getMinSugarScore()
    fun getAvgSugarScore() = patientDao.getAvgSugarScore()
}

