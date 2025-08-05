package com.fit2081.shen33520089.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This is the database class for the application. It is a Room database.
 * It contains one entity: [Patient].
 * The version is 1 and exportSchema is false.
 */
@Database(entities = [Patient::class, FoodIntake::class, NutriCoach::class], version = 1, exportSchema = false)
// this is a room database

abstract class AppDatabase: RoomDatabase() {

    /**
     * Returns the [PatientDao] object.
     * This is an abstract function that is implemented by Room.
     */
    abstract fun userDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun nutriCoachDao(): NutriCoachDao


    companion object {
        /**
         * This is a volatile variable that holds the database instance.
         * It is volatile so that it is immediately visible to all threads.
         */
        @Volatile
        private var Instance: AppDatabase? = null

        /**
         * Returns the database instance.
         * If the instance is null, it creates a new database instance.
         * @param context The context of the application.
         */
        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            //synchronized means that only one thread can access this code at a time.
            //hospital_database is the name of the database.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .build().also { Instance = it }
            }
        }
    }
}
