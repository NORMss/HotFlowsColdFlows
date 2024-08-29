package com.norm.myhotflowscoldflows

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@SuppressLint("MissingPermission")
fun observeLocation(context: Context): Flow<Location> {
    if (!context.hasLocationPermission()) {
        throw RuntimeException("No permission!")
    }

    return callbackFlow {
        val client = LocationServices.getFusedLocationProviderClient(context)

        val request = LocationRequest.Builder(5000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
//            .also {
//                Log.d("MyLog","LocationRequest")
//            }

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                println("Location callback triggered")
                result.locations.lastOrNull()?.let { location ->
                    trySend(location)
                }
            }
        }

        client.requestLocationUpdates(request, callback, context.mainLooper)

        awaitClose {
            client.removeLocationUpdates(callback)
        }
    }
}

private fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

private fun Context.hasLocationPermission(): Boolean {
    return isPermissionGranted(android.Manifest.permission.ACCESS_COARSE_LOCATION)
}