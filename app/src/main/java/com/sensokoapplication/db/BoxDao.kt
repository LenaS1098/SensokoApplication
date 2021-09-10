package com.sensokoapplication.db

import androidx.room.*

@Dao
interface BoxDao {
    @Query("SELECT * from all_boxes")
    fun getAll(): List<Transportbox>

    @Query("SELECT  COUNT(boxId) FROM all_boxes")
    fun getBoxCount():Long

    @Query("SELECT * from all_boxes where boxId = :id")
    fun getById(id: Int) : Transportbox?

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertBox(transportbox: Transportbox)

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertParameter(parameter: Parameter)

    @Update
    suspend fun update(transportbox: Transportbox)

    @Delete
    suspend fun delete(transportbox: Transportbox)

    @Query("DELETE FROM all_boxes")
    suspend fun deleteAllBoxes()

    @Query("SELECT * FROM all_boxes where isCurrent = :wahr")
    suspend fun getCurrentBox(wahr: Boolean = true):Transportbox

    @Transaction
    @Query("SELECT * FROM all_boxes where boxId = :boxId")
    suspend fun getKammernFromBox(boxId : Long):List<BoxWithKammern>

    @Transaction
    @Query("SELECT * FROM all_boxes")
    suspend fun getKammern():List<BoxWithKammern>

    @Query("SELECT * FROM Parameter where parentKammerId = :kammerId")
    suspend fun getParameterFomKammer(kammerId:Long):List<Parameter>

    @Query("SELECT boxId FROM all_boxes where label=:label")
    suspend fun getBoxId(label:String):Long

}