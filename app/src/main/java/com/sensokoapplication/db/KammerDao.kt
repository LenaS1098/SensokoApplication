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

    @Query("SELECT kammerId FROM Kammer WHERE parentBoxId=:parentBoxId AND goalTemp=:goalTemp")
    suspend fun getKammerId(parentBoxId: Long, goalTemp: Float):Long

}