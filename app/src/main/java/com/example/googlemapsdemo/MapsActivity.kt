package com.example.googlemapsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.googlemapsdemo.databinding.ActivityMapsBinding
import com.example.googlemapsdemo.misc.CameraAndViewport
import com.example.googlemapsdemo.misc.TypeAndStyle
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val typeAndStyle by lazy { TypeAndStyle() }
    private val cameraAndViewport by lazy { CameraAndViewport() }

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
        typeAndStyle.setMapType(item, map)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val losAngeles = LatLng(34.04692123923977, -118.2474842145839)
        val newYork = LatLng(40.75525227312982, -74.01902321542899)
        map.addMarker(MarkerOptions().position(losAngeles).title("Marker in Los Angeles"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))
        map.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        typeAndStyle.setMapStyle(map, this)

//        lifecycleScope.launch {
//            delay(4000L)
//
////            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewport.losAngeles), 2000, object : GoogleMap.CancelableCallback {
////                override fun onFinish() {
////                    Toast.makeText(this@MapsActivity, "Finished", Toast.LENGTH_SHORT).show()
////                }
////
////                override fun onCancel() {
////                    Toast.makeText(this@MapsActivity, "Canceled", Toast.LENGTH_SHORT).show()
////                }
////            })
//////            map.animateCamera(CameraUpdateFactory.newLatLngBounds(cameraAndViewport.melbourneBounds, 100), 2000, null)
//////            map.setLatLngBoundsForCameraTarget(cameraAndViewport.melbourneBounds)
//        }

        onMapClicked()
        onMapLongClicked()
    }

    private fun onMapClicked() {
        map.setOnMapClickListener {
            Toast.makeText(this, "Single Click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onMapLongClicked() {
        map.setOnMapLongClickListener {
            map.addMarker(MarkerOptions().position(it).title("New Marker"))
        }
    }

}