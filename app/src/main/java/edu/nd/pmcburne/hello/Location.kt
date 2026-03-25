package edu.nd.pmcburne.hello

data class Location(
    val id: Int,
    val name: String,
    val tagList:List<String>,
    val description:String,
    val longitude:Double,
    val latitude:Double
)