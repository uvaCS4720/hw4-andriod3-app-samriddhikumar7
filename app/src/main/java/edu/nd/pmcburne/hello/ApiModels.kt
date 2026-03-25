package edu.nd.pmcburne.hello

import com.google.gson.annotations.SerializedName

data class VisualCenterDto(
    val longitude:Double,
    val latitude:Double
)
data class LocationDto(
    val id:Int,
    val name: String,
    val description: String,
    @SerializedName("tag_list")
    val tagList:List<String>,
    @SerializedName("visual_center")
    val visualCenter: VisualCenterDto
    )
