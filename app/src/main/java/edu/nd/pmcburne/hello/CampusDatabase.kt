package edu.nd.pmcburne.hello

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities=[LocationEntity::class], version=1, exportSchema=false)
abstract class CampusDatabase :RoomDatabase(){
    abstract fun locationDao():LocationDao
    companion object {
        @Volatile
        private var INSTANCE:CampusDatabase? =null
        fun getDatabase(context:Context):CampusDatabase {
            return INSTANCE ?:synchronized(this) {
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    CampusDatabase::class.java,
                    "campusMapsDB"
                ).build()
                INSTANCE=instance
                instance
            }

        }


    }
}