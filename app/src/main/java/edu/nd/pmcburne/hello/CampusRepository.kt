package edu.nd.pmcburne.hello

import android.content.Context

class CampusRepository(context: Context) {

    private val locationDao=CampusDatabase.getDatabase(context).locationDao()
    suspend fun syncApi(){
        val apiLocations= RetrofitInstance.api.getLocations()
        val entities=apiLocations.map{dto ->
            LocationEntity.fromDto(dto)
        }

        locationDao.insertAll(entities)
    }

    suspend fun getAllLocations():List<Location>{
        return locationDao.getAllLocations().map{ entity ->
            entity.toLocation()
        }

    }

    suspend fun getTags():List<String>{
        val tagList = mutableSetOf<String>()
        for(loco in getAllLocations()){
            tagList.addAll(loco.tagList)
        }
        return tagList.sorted()

    }
}


