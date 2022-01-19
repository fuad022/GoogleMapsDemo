package com.example.googlemapsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.googlemapsdemo.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.MapStyleOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_types_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.normal_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            R.id.hybrid_map -> {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
            R.id.satellite_map -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
            R.id.terrain_map -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
            R.id.none_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NONE
            }
        }
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val losAngeles = LatLng(34.05139603923977, -118.2934366445839)
        map.addMarker(MarkerOptions().position(losAngeles).title("Marker in Los Angeles"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))
        map.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        setMapStyle(map)
    }

    private fun setMapStyle(googleMap: GoogleMap) {
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.style
                )
            )
            if (!success) {
                Log.d("Maps", "Failed to add Style")
            }
        } catch (e: Exception) {
            Log.d("Maps", e.toString())
        }
    }
}