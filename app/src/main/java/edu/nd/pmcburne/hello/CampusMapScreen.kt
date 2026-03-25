package edu.nd.pmcburne.hello
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CampusMapScreen(viewModel:CampusViewModel){
    var expanded by rememberSaveable{ mutableStateOf(false) }
    var curTag by rememberSaveable{ mutableStateOf("core")}
    if(viewModel.isLoading){
        Box(
            modifier=Modifier.fillMaxSize(),
            contentAlignment= Alignment.Center
        )
        {
            CircularProgressIndicator()
        }
        return
    }
    if(curTag !in viewModel.allTags&&viewModel.allTags.isNotEmpty()){
        if("core" in viewModel.allTags){
            curTag="core"
        }
        else{
            curTag=viewModel.allTags.first()
        }
    }
    val filteredLocations=viewModel.allLocations.filter { curTag in it.tagList }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(38.0356, -78.5036),
            14f
        )
    }
    Column(
        modifier =Modifier
            .fillMaxSize()
            .padding(12.dp)
    ){
        Text(
            text= "Filter By Tag",
            style= MaterialTheme.typography.titleMedium,
            modifier= Modifier.padding(bottom =10.dp)
        )
        Box(modifier=Modifier.fillMaxWidth()){
            OutlinedTextField(
                value=curTag,
                onValueChange={},
                readOnly=true,
                label= {Text("Selected Tag") },
                modifier=Modifier.fillMaxWidth()
            )
            OutlinedButton(
                onClick={ expanded = true },
                modifier=Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end =10.dp)
            )
            {
                Text("Choose")
            }
            DropdownMenu(
                expanded=expanded,
                onDismissRequest={ expanded = false },
                modifier=Modifier.heightIn(max = 300.dp)
            ){
                viewModel.allTags.forEach { tag ->
                    DropdownMenuItem(
                        text= { Text(tag) },
                        onClick= {
                            curTag = tag
                            expanded = false
                        }
                    )
                }
            }

        }

        Text(
            text ="Showing ${filteredLocations.size} locations",
            modifier=Modifier.padding(top = 8.dp, bottom = 8.dp)
        )

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            filteredLocations.forEach { place ->
                MarkerInfoWindowContent(
                    state=MarkerState(
                        position =LatLng(place.latitude, place.longitude)
                    ),
                    title=place.name,
                    snippet =null
                ){ _->
                    Surface{
                        Column(
                            modifier= Modifier.width(220.dp).padding(8.dp)
                        ){
                            Text(
                                text=place.name,
                                style= MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text =place.description,
                                style=MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}