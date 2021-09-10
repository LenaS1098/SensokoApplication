package com.sensokoapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransportDao {
    @Query("SELECT * from Transport")
    fun getAllTransports(): List<Transport>

    @Query("SELECT  COUNT(transportId) FROM Transport")
    fun getTransportCount():Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransport(transport: Transport)

    @Query("SELECT * FROM Transport where transportId =:transportId")
    suspend fun getTransportById(transportId : Long):Transport

    @Query("SELECT transportId FROM Transport where carrier=:carrier AND transporter=:transporter AND origin=:origin AND destination=:destination")
    suspend fun getTransportId(carrier : String, transporter: String, origin: String, destination: String): Long

}