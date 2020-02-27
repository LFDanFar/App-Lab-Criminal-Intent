package com.example.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.criminalintent.database.CrimeDatabase
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {
    private val database : CrimeDatabase = databaseBuilder(
        context.applicationContext, CrimeDatabase::class.java, DATABASE_NAME
    ).build()

    private val crimeDao = database.CrimeDao()

    //fun getCrimes(): List<Crime> = crimeDao.getCrimes()
    //fun getCrime(id: UUID): Crime? = crimeDao.getCrime(id)
    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    companion object{
        private var INSTANCE: CrimeRepository? = null
        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository{
            return INSTANCE ?:
                    throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}