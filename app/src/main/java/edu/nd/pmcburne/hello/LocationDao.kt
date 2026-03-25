package edu.nd.pmcburne.hello

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.nd.pmcburne.hello.Location

@Dao
interface LocationDao{
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insertAll(places:List<LocationEntity>)

    @Query("SELECT * FROM locations ORDER BY name ASC")
    suspend fun getAllLocations():List<LocationEntity>

    @Query("SELECT * FROM locations WHERE id=:id")
    suspend fun getLocationById(id:Int): LocationEntity?
}

