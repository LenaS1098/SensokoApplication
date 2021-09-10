package com.sensokoapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sensokoapplication.*

@Database(entities = [Transportbox::class, Kammer::class,Transport::class,Parameter::class], version = 1, exportSchema = true)
abstract class BoxDatabase :RoomDatabase() {
    abstract val boxDao :BoxDao
    abstract val transportDao :TransportDao
    abstract val kammerDao:KammerDao
    abstract val parameterDao : ParameterDao

    companion object{
        @Volatile
        private var INSTANCE: BoxDatabase? = null

        fun getInstance(context: Context): BoxDatabase{
            synchronized(this){
                return INSTANCE?: Room.databaseBuilder(
                    context.applicationContext.applicationContext,
                    BoxDatabase::class.java,
                    "box_db"
                ).allowMainThreadQueries()
                    .createFromAsset("database/box.db")
                    .build().also {
                    INSTANCE = it
                }
            }
        }
    }
}