package com.sensokoapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sensokoapplication.BoxWithKammern
import com.sensokoapplication.Kammer
import com.sensokoapplication.Transportbox
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface BoxDao {
    @Query("SELECT * from all_boxes")
    fun getAll(): List<Transportbox>

    @Query("SELECT * from all_boxes where boxId = :id")
    fun getById(id: Int) : Transportbox?

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertBox(transportbox: Transportbox)

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertKammer(kammer: Kammer)

    @Update
    suspend fun update(transportbox: Transportbox)

    @Delete
    suspend fun delete(transportbox: Transportbox)

    @Query("DELETE FROM all_boxes")
    suspend fun deleteAllBoxes()

    @Query("SELECT * FROM all_boxes where IsCurrentBox = :wahr")
    suspend fun getCurrentBox(wahr: Boolean = true):Transportbox

    @Transaction
    @Query("SELECT * FROM all_boxes where boxId = :boxId")
    fun getKammernFromBox(boxId : Long):List<BoxWithKammern>

    @Transaction
    @Query("SELECT * FROM all_boxes")
    fun getKammern():List<BoxWithKammern>



}