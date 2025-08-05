package com.fit2081.shen33520089.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PatientViewModel(context: Context) : ViewModel() {

    //creates a repo object that will be used to interact with the database
    private val patientRepo = PatientsRepository(context)
    //gets all the users stored in the repo
    val allPatients: Flow<List<Patient>> = patientRepo.getAllPatients()

    //inserts a user into the repo
    fun insert(patient: Patient) = viewModelScope.launch {
        patientRepo.insert(patient)
    }
    //deletes a user from the repo
    fun delete(patient: Patient) = viewModelScope.launch {
        patientRepo.delete(patient)
    }
    //updates a user in the repo
    fun update(patient: Patient) = viewModelScope.launch {
        patientRepo.update(patient)
    }
    //deletes all the users in the repo
    fun deleteAllPatients() = viewModelScope.launch {
        patientRepo.deleteAllPatients()
    }
    // Function to get a specific patient by ID from the repository
    fun getPatientById(userId: String): Flow<Patient?> {
        return patientRepo.getPatientById(userId)
    }

    // Functions to get the statistics for all patients
    fun getAverageHeifaScoreMale(): Flow<Float?> {
        return patientRepo.getAverageHeifaScoreMale()
    }
    fun getAverageHeifaScoreFemale(): Flow<Float?> {
        return patientRepo.getAverageHeifaScoreFemale()
    }

    fun getMaxDiscretionaryScore() = patientRepo.getMaxDiscretionaryScore()
    fun getMinDiscretionaryScore() = patientRepo.getMinDiscretionaryScore()
    fun getAvgDiscretionaryScore() = patientRepo.getAvgDiscretionaryScore()
    fun getMaxVegetablesScore() = patientRepo.getMaxVegetablesScore()
    fun getMinVegetablesScore() = patientRepo.getMinVegetablesScore()
    fun getAvgVegetablesScore() = patientRepo.getAvgVegetablesScore()
    fun getMaxFruitScore() = patientRepo.getMaxFruitScore()
    fun getMinFruitScore() = patientRepo.getMinFruitScore()
    fun getAvgFruitScore() = patientRepo.getAvgFruitScore()
    fun getMaxGrainsScore() = patientRepo.getMaxGrainsScore()
    fun getMinGrainsScore() = patientRepo.getMinGrainsScore()
    fun getAvgGrainsScore() = patientRepo.getAvgGrainsScore()
    fun getMaxWholeGrainsScore() = patientRepo.getMaxWholeGrainsScore()
    fun getMinWholeGrainsScore() = patientRepo.getMinWholeGrainsScore()
    fun getAvgWholeGrainsScore() = patientRepo.getAvgWholeGrainsScore()
    fun getMaxMeatScore() = patientRepo.getMaxMeatScore()
    fun getMinMeatScore() = patientRepo.getMinMeatScore()
    fun getAvgMeatScore() = patientRepo.getAvgMeatScore()
    fun getMaxDairyScore() = patientRepo.getMaxDairyScore()
    fun getMinDairyScore() = patientRepo.getMinDairyScore()
    fun getAvgDairyScore() = patientRepo.getAvgDairyScore()
    fun getMaxSodiumScore() = patientRepo.getMaxSodiumScore()
    fun getMinSodiumScore() = patientRepo.getMinSodiumScore()
    fun getAvgSodiumScore() = patientRepo.getAvgSodiumScore()
    fun getMaxAlcoholScore() = patientRepo.getMaxAlcoholScore()
    fun getMinAlcoholScore() = patientRepo.getMinAlcoholScore()
    fun getAvgAlcoholScore() = patientRepo.getAvgAlcoholScore()
    fun getMaxWaterScore() = patientRepo.getMaxWaterScore()
    fun getMinWaterScore() = patientRepo.getMinWaterScore()
    fun getAvgWaterScore() = patientRepo.getAvgWaterScore()
    fun getMaxSugarScore() = patientRepo.getMaxSugarScore()
    fun getMinSugarScore() = patientRepo.getMinSugarScore()
    fun getAvgSugarScore() = patientRepo.getAvgSugarScore()


    //a view model factory that sets the context for the viewmodel
    //The ViewModelProvider.Factory interface is used to create view models.
    class patientViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PatientViewModel(context) as T
    }

}