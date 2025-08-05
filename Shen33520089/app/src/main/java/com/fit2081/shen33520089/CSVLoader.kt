package com.fit2081.shen33520089

import android.content.Context
import android.util.Log
import com.fit2081.shen33520089.data.AppDatabase
import com.fit2081.shen33520089.data.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * CSVLoader is a utility object that loads patient data from a CSV file into the Room database.
 * It checks if the database is empty before loading the data to avoid duplicates.
 */
object CSVLoader {
    suspend fun loadCsvIntoRoomIfEmpty(context: Context) {
        val userDao = AppDatabase.getDatabase(context).userDao()
        if (userDao.getAllPatients().first().isEmpty()) {
            withContext(Dispatchers.IO) {
                val existingUsers = userDao.getAllPatients().first()
                if (existingUsers.isNotEmpty()) return@withContext // Already loaded

                try {
                    val inputStream = context.assets.open("userID.csv")
                    val reader = BufferedReader(InputStreamReader(inputStream))

                    val lines = reader.readLines()
                    if (lines.isEmpty()) return@withContext

                    val headers = lines[0].split(",").map { it.trim() }

                    for (line in lines.drop(1)) {
                        val values = line.split(",").map { it.trim() }
                        if (values.size != headers.size) continue

                        val rowMap = headers.zip(values).toMap()

                        val phoneNumber = rowMap["PhoneNumber"]
                        val userId = rowMap["User_ID"]
                        val gender = rowMap["Sex"]?: ""

                        if (!phoneNumber.isNullOrEmpty() && !userId.isNullOrEmpty()) {
                            val isMale = gender == "Male"

                            val patient = Patient(
                                userId = userId,
                                phoneNumber = phoneNumber,
                                gender = gender,
                                heifaTotalScore = if (isMale) rowMap["HEIFAtotalscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["HEIFAtotalscoreFemale"]?.toFloatOrNull() ?: 0f,
                                discretionaryScore = if (isMale) rowMap["DiscretionaryHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["DiscretionaryHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                vegetablesScore = if (isMale) rowMap["VegetablesHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["VegetablesHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                fruitScore = if (isMale) rowMap["FruitHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["FruitHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                grainsScore = if (isMale) rowMap["GrainsandcerealsHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["GrainsandcerealsHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                wholeGrainsScore = if (isMale) rowMap["WholegrainsHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["WholegrainsHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                meatScore = if (isMale) rowMap["MeatandalternativesHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["MeatandalternativesHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                dairyScore = if (isMale) rowMap["DairyandalternativesHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["DairyandalternativesHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                sodiumScore = if (isMale) rowMap["SodiumHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["SodiumHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                alcoholScore = if (isMale) rowMap["AlcoholHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["AlcoholHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                waterScore = if (isMale) rowMap["WaterHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["WaterHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                sugarScore = if (isMale) rowMap["SugarHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["SugarHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                saturatedFatScore = if (isMale) rowMap["SaturatedFatHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["SaturatedFatHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                unsaturatedFatScore = if (isMale) rowMap["UnsaturatedFatHEIFAscoreMale"]?.toFloatOrNull() ?: 0f else rowMap["UnsaturatedFatHEIFAscoreFemale"]?.toFloatOrNull() ?: 0f,
                                fruitServeSize = if (isMale) rowMap["Fruitservesize"]?.toFloatOrNull() ?: 0f else rowMap["Fruitservesize"]?.toFloatOrNull() ?: 0f,
                                fruitVariationScore = if (isMale) rowMap["Fruitvariationsscore"]?.toFloatOrNull() ?: 0f else rowMap["Fruitvariationsscore"]?.toFloatOrNull() ?: 0f
                            )
                            userDao.insert(patient)
                        }
                    }

                } catch (e: Exception) {
                    Log.e("CSVLoader", "Error loading CSV file", e)
                }
            }
        }

    }
}
