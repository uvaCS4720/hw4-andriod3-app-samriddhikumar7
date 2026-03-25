package edu.nd.pmcburne.hello
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CampusApiService{
    @GET("placemarks.json")
    suspend fun getLocations(): List<LocationDto>
}

object RetrofitInstance{
    val BASEURL = "https://www.cs.virginia.edu/~wxt4gm/"
    val api:CampusApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CampusApiService::class.java)
    }
}

