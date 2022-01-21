package com.example.googlemapsdemo.misc

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class CameraAndViewport {

    val losAngeles: CameraPosition = CameraPosition.Builder()
        .target(LatLng(34.04692123923977, -118.2474842145839))
        .zoom(17f)
        .bearing(100f)
        .tilt(45f)
        .build()

    val melbourneBounds = LatLngBounds(
        LatLng(-38.45007744467931, 144.25468813596153), //SW
        LatLng(-37.52250858424494, 145.56755480979693)  //NE
    )

}