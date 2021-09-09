package com.sensokoapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ParameterDao {
    @Query("SELECT * from Parameter")
    fun getAllParameter(): List<Parameter>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParamter(parameter: Parameter)
}