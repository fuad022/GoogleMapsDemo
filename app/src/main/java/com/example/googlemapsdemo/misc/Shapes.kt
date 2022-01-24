package com.example.googlemapsdemo.misc

import android.graphics.Color
import com.example.googlemapsdemo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.delay

class Shapes {

    private val losAngeles = LatLng(34.04692123923977, -118.2474842145839)
    private val newYork = LatLng(40.75525227312982, -74.01902321542899)
    private val madrid = LatLng(40.63987189525695, -3.5627974558481872)
    private val panama = LatLng(8.45744235724678, -79.93696458061512)

    private val p0 = LatLng(34.61111283456, -119.61055187107762)
    private val p1 = LatLng(34.599014993181534, -117.15922754262057)
    private val p2 = LatLng(33.550677353674885, -117.14616252288198)
    private val p3 = LatLng(33.54387186558186, -119.81469280436997)

    private val p00 = LatLng(34.36342923763002, -118.82828381410476)
    private val p01 = LatLng(34.33646281801516, -117.87780362812079)
    private val p02 = LatLng(33.23707099789062, -118.05091514033636)
    private val p03 = LatLng(33.80888822068028, -118.82665068663746)

    private suspend fun addPolyline(map: GoogleMap) {
        val polyline = map.addPolyline(
            PolylineOptions().apply {
                add(losAngeles, newYork, madrid)
                width(5f)
                color(Color.BLUE)
                geodesic(true)
                clickable(true)
            }
        )

        delay(5000)

        val newList = listOf(
            losAngeles, panama, madrid
        )

        polyline.points = newList
    }

    fun addPolygon(map: GoogleMap) {
        val polygon = map.addPolygon(
            PolygonOptions().apply {
                add(p0, p1, p2, p3)
                fillColor(R.color.black)
                strokeColor(R.color.black)
                zIndex(1f)
            }
        )
        val polygon2 = map.addPolygon(
            PolygonOptions().apply {
                add(p00, p01, p02, p03)
                fillColor(R.color.black)
                strokeColor(R.color.black)
            }
        )
    }

}