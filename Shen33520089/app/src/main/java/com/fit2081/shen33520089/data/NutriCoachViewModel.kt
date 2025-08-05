package com.fit2081.shen33520089.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NutriCoachViewModel(context: Context) : ViewModel() {

    //creates a repo object that will be used to interact with the database
    private val nutriCoachRepo = NutriCoachRepository(context)
    //gets all the users stored in the repo
    val allMessages: Flow<List<NutriCoach>> = nutriCoachRepo.getAllMessages()

    fun insert(nutriCoach: NutriCoach) {
        viewModelScope.launch {
            nutriCoachRepo.insert(nutriCoach)
        }
    }
    //deletes a user from the repo
    fun delete(nutriCoach: NutriCoach) = viewModelScope.launch {
        nutriCoachRepo.delete(nutriCoach)
    }
    //updates a user in the repo
    fun update(nutriCoach: NutriCoach) = viewModelScope.launch {
        nutriCoachRepo.update(nutriCoach)
    }
    //deletes all the users in the repo
    fun deleteAllMessages() = viewModelScope.launch {
        nutriCoachRepo.deleteAllMessages()
    }

    // Function to get messages by user ID from the repository
    fun getMessagesByUserId(userId: String): Flow<List<NutriCoach>> {
        return nutriCoachRepo.getMessagesByUserId(userId)
    }

    //a view model factory that sets the context for the viewmodel
    //The ViewModelProvider.Factory interface is used to create view models.
    class nutriCoachViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NutriCoachViewModel(context) as T
    }

}