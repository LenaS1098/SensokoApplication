package com.sensokoapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KammerDao {

    @Query("SELECT * from Kammer")
    fun getAllKammern(): List<Kammer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKammer(kammer: Kammer)


}