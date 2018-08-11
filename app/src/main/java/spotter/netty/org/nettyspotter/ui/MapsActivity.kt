package spotter.netty.org.nettyspotter.ui

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import android.location.LocationManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import spotter.netty.org.nettyspotter.R


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    // Keys for storing activity state
    private val mKeyLocation = "location"

    // A default location (Paris, France) and default zoom to use when location permission is
    // not granted.
    private val mDefaultLocation = LatLng(48.856667, 2.351944)
    private val mDefaultZoom = 17
    private var mLocationPermissionGranted: Boolean = false
    private val mPermissionsRequestAccessFineLocation = 1

    // The geographical location where the device is currently located.
    // That is, the last-known location retrieved by the Fused Location Provider.
    private var mLastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(mKeyLocation)
        }

        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(mKeyLocation, mLastKnownLocation)
        super.onSaveInstanceState(outState)
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            mPermissionsRequestAccessFineLocation -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                    updateLocationUI()
                }
            }
        }
    }

    override fun onResume() {
        // Check GPS status
        checkGPSStatus()

        super.onResume()
    }

    private fun checkGPSStatus() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is enabled in your device", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "GPS NOT enabled!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {

            override fun getInfoWindow(arg0: Marker): View? {
                // Return null here, so that getInfoContents() is called next
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                // Inflate the layouts for the info window, title and snippet
                val infoWindow = layoutInflater.inflate(R.layout.custom_info_contents,
                        findViewById<FrameLayout>(R.id.map), false)

                val title = infoWindow.findViewById<TextView>(R.id.title)
                title.text = marker.title

                val snippet = infoWindow.findViewById<TextView>(R.id.snippet)
                snippet.text = marker.snippet

                return infoWindow
            }
        })

        // Add a marker in Paris and move the camera
        val somewhere = LatLng(48.8668198774, 2.35272664515)
        mMap.addMarker(MarkerOptions().position(somewhere).title("Marker in Paris!").snippet("Awesome place: everyone, get in here!"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(somewhere))

        // Zoom to place
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(somewhere, mDefaultZoom.toFloat()))

        // Turn on the My Location layer and the related control on the map
        updateLocationUI()

    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    mPermissionsRequestAccessFineLocation)
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private fun updateLocationUI() {
        try {
            if (mLocationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                mLastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("MapsActivity", "updateLocationUI - Exception: ${e.message}")
        }
    }
}
