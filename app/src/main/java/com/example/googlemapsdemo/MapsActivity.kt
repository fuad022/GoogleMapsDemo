package com.example.googlemapsdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.googlemapsdemo.databinding.ActivityMapsBinding
import com.example.googlemapsdemo.misc.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.data.geojson.GeoJsonLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val losAngeles = LatLng(34.04692123923977, -118.2474842145839)
    private lateinit var binding: ActivityMapsBinding
    private val typeAndStyle by lazy { TypeAndStyle() }
    private val cameraAndViewport by lazy { CameraAndViewport() }
    private val shapes by lazy { Shapes() }
    private val overlays by lazy { Overlays() }

    private lateinit var clusterManager: ClusterManager<MyItem>

    private val locationList = listOf(
        LatLng(33.987459169366, -117.435146669222),
        LatLng(34.028441684965, -116.808925958207),
        LatLng(33.987459169366, -116.017910323241),
        LatLng(33.786815788420, -115.111538269954),
        LatLng(33.850708044143, -114.254604674543),
        LatLng(33.581124919824, -112.716518729984),
        LatLng(33.512445201506, -110.991665212836),
        LatLng(33.549084352008, -110.162197443690),
        LatLng(33.484967570542, -108.959194519608),
        LatLng(32.503644946358, -106.822353687115)
    )

    private val titleList = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10"
    )

    private val snippetList = listOf(
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum"
    )

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

        val losAngelesMarker = map.addMarker(
            MarkerOptions()
                .position(losAngeles)
                .title("Marker in Los Angeles")
                .snippet("Some random text")
        )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))
        map.uiSettings.apply {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }

        typeAndStyle.setMapStyle(map, this)

        clusterManager = ClusterManager(this, map)
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)
        addMarkers()

        //checkLocationPermission()

//        val layer = GeoJsonLayer(map, R.raw.map, this)
//        layer.addLayerToMap()
//
//        val polygonStyle = layer.defaultPolygonStyle
//        polygonStyle.apply {
////            fillColor = ContextCompat.getColor(this@MapsActivity, R.color.purple_200)
//            fillColor = Color.BLUE
//        }
//
//        layer.setOnFeatureClickListener {
//            Log.d("MapsActivity", "Feature ${it.getProperty("country")}")
//        }
//
//        for (feature in layer.features) {
//            if (feature.hasProperty("country")) {
//                Log.d("MapsActivity", "Success")
//            }
//        }
    }

    private fun addMarkers() {
        locationList.zip(titleList).zip(snippetList).forEach { pair ->
            val myItem =
//                MyItem(pair.first.first, pair.first.second, pair.second)
                MyItem(pair.first.first, "Title: ${pair.first.second}", "Snippet: ${pair.second}")
            clusterManager.addItem(myItem)
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            Toast.makeText(this, "Already Enabled", Toast.LENGTH_SHORT).show()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    }

    @SuppressLint("MissingPermission", "MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != 1) {
            return
        }
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Granted!", Toast.LENGTH_SHORT).show()
            map.isMyLocationEnabled = true
        } else {
            Toast.makeText(this, "We need your permission", Toast.LENGTH_SHORT).show()
        }
    }

}