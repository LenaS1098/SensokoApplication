package com.sensokoapplication

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sensokoapplication.db.BoxDao
import com.sensokoapplication.db.BoxDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.Exception

@RunWith(AndroidJUnit4::class)
class TransportboxDatabaseTest {
    private lateinit var boxDao: BoxDao
    private lateinit var db: BoxDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context,BoxDatabase::class.java).allowMainThreadQueries().build()
        boxDao = db.boxDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb(){
        db.close()

    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetBox() = runBlocking{
        val box = Transportbox(1,"TestLabel","GM","K",false)
        boxDao.insertBox(box)
        val oneBox = boxDao.getById(1)
        Assert.assertEquals(oneBox!!.boxId, box.boxId)
    }
    @Test
    @Throws(Exception::class)
    fun insertAndGetKammer() = runBlocking{
        val box = Transportbox(1,"TestLabel","GM","K",false)
        val boxB = Transportbox(2,"TestLabel","GM","K",false)
        boxDao.insertBox(box)
        boxDao.insertBox(boxB)
        val kammerA = Kammer(parentBoxId = 1,currentTemp = 2f,medizin = "Paracetamol")
        val kammerB = Kammer(2,50f,"Ibu")
        val kammerC = Kammer(2,50f,"Ibu")
        boxDao.insertKammer(kammerA)
        boxDao.insertKammer(kammerB)
        boxDao.insertKammer(kammerC)
        val listKammern = boxDao.getKammernFromBox(1)
        listKammern.forEach { kammerList ->
            kammerList.kammern.forEach {
                Log.e("DB", "BoxId: "+ it.parentBoxId+ " KammerId: "+it.kammerId)
            }
        }
        Assert.assertEquals(1,listKammern.first().kammern.size)
    }

    @Test
    @Throws(Exception::class)
    fun getKammern() = runBlocking {
        val box = Transportbox(1,"TestLabel","GM","K",false)
        val boxB = Transportbox(2,"TestLabel","GM","K",false)
        boxDao.insertBox(box)
        boxDao.insertBox(boxB)
        val kammerA = Kammer(parentBoxId = 1,currentTemp = 2f,medizin = "Paracetamol")
        val kammerAB = Kammer(parentBoxId = 1,currentTemp = 2f,medizin = "Ibu")
        val kammerAC = Kammer(parentBoxId = 1,currentTemp = 2f,medizin = "Aspirin")
        val kammerB = Kammer(2,50f,"Ibu")
        val kammerC = Kammer(2,50f,"Ibu")
        val kammerD = Kammer(2,50f,"Aspirin")
        boxDao.insertKammer(kammerA)
        boxDao.insertKammer(kammerAB)
        boxDao.insertKammer(kammerAC)
        boxDao.insertKammer(kammerB)
        boxDao.insertKammer(kammerC)
        boxDao.insertKammer(kammerD)

        boxDao.getKammern()
        Assert.assertEquals(emptyList<BoxWithKammern>(),boxDao.getAll())
    }
}