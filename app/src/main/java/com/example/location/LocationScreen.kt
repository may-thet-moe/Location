import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.location.LocationViewModel
import com.example.location.MainActivity
import eu.tutorials.locationapp.LocationUtils

@Composable
fun LocationScreen(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context
) {
    val location = viewModel.location.value

    val address = location?.let{
        locationUtils.reverseGeocodeLocation(location)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions() ,
        onResult = { permissions ->
            if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true){
                // I HAVE ACCESS to location

                locationUtils.requestLocationUpdates(viewModel = viewModel)
            }else{
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if(rationaleRequired){
                    Toast.makeText(context,
                        "Location Permission is required for this feature to work", Toast.LENGTH_LONG)
                        .show()
                }else{
                    Toast.makeText(context,
                        "Location Permission is required. Please enable it in the Android Settings",
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        if(location != null){
            Text("Address: ${location.latitude} ${location.longitude} \n $address")
        }else{
            Text(text = "Location not available")
        }


        Button(onClick = {
            if(locationUtils.hasLocationPermission(context)){
                // Permission already granted update the location
                locationUtils.requestLocationUpdates(viewModel)
            }else{
                // Request location permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "Get Location")
        }
    }
}