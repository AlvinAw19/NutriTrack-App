package com.fit2081.shen33520089.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FoodIntakeViewModel(context: Context) : ViewModel() {

    //creates a repo object that will be used to interact with the database
    private val foodIntakeRepo = FoodIntakesRepository(context)
    //gets all the users stored in the repo
    val allFoodIntakes: Flow<List<FoodIntake>> = foodIntakeRepo.getAllFoodIntakes()

    //inserts a user into the repo
    fun insert(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepo.insert(foodIntake)
    }
    //deletes a user from the repo
    fun delete(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepo.delete(foodIntake)
    }
    //updates a user in the repo
    fun update(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepo.update(foodIntake)
    }
    //deletes all the users in the repo
    fun deleteAllFoodIntakes() = viewModelScope.launch {
        foodIntakeRepo.deleteAllFoodIntakes()
    }
    fun getFoodIntakeById(userId: String): Flow<FoodIntake?> {
        return foodIntakeRepo.getFoodIntakeById(userId)
    }
    //a view model factory that sets the context for the viewmodel
    //The ViewModelProvider.Factory interface is used to create view models.
    class FoodIntakeViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FoodIntakeViewModel(context) as T
    }

}