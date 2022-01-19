package com.example.googlemapsdemo.misc

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class CameraAndViewport {

    val losAngeles: CameraPosition = CameraPosition.Builder()
        .target(LatLng(34.05139603923977, -118.2934366445839))
        .zoom(17f)
        .bearing(100f)
        .tilt(45f)
        .build()

}