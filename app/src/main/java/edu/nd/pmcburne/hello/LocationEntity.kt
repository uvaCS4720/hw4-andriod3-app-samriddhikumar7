package edu.nd.pmcburne.hello

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.nd.pmcburne.hello.Location
import edu.nd.pmcburne.hello.LocationDto

@Entity(tableName="locations")
data class LocationEntity(
    @PrimaryKey val id:Int,
    val name:String,
    val description:String,
    val tagList:String,
    val longitude:Double,
    val latitude:Double
)
{
    fun toLocation(): Location{
        return Location(
            id=id,
            name=name,
            description=description,
            tagList=tagList.split(",").map{ it.trim() },
            longitude =longitude,
            latitude= latitude
        )
    }

    companion object {
        fun fromDto(dto: LocationDto):LocationEntity{
            return LocationEntity(
                id=dto.id,
                name =dto.name,
                description = dto.description,
                tagList= dto.tagList.joinToString(","),
                longitude = dto.visualCenter.longitude,
                latitude= dto.visualCenter.latitude

            )
        }

    }

}
