package edu.nd.pmcburne.hello


import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CampusViewModel(application:Application):AndroidViewModel(application){
    var allLocations by mutableStateOf<List<Location>>( emptyList())
        private set

    var allTags by mutableStateOf<List<String>>(emptyList() )
        private set
    var isLoading by mutableStateOf(true)
        private set
    init {
        loadData() }
    private fun loadData(){
        viewModelScope.launch(Dispatchers.IO ){
            val repo=CampusRepository(getApplication())
            try{
                repo.syncApi()
            } catch (e: Exception) {
            }
            val locations=repo.getAllLocations()
            val tags =repo.getTags()
            withContext(Dispatchers.Main){
                allLocations= locations
                allTags=tags
                isLoading= false
            }

        }
    }


}







