package org.deplastic.Deplastic.ui.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import org.deplastic.Deplastic.BuildConfig;
import org.deplastic.Deplastic.MainActivity;
import org.deplastic.Deplastic.R;
import org.json.JSONException;
import org.json.JSONObject;

public class MapFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    // Keys for storing activity state.
    private static final String KEY_LOCATION = "location";
    // A default location (Sydney, Australia) and default zoom to use when location permission is not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted;
    // The geographical location where the device is currently located. That is, the last-known location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Context app_context = view.getContext();

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        Places.initialize(app_context.getApplicationContext(), BuildConfig.MAPS_API_KEY);
        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(app_context);

        // Async map
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(googleMap -> {
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(@NonNull Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(@NonNull Marker marker) {
                        View infoWindow = inflater.inflate(R.layout.custom_info_contents, container, false);
                        TextView title = infoWindow.findViewById(R.id.info);
                        title.setText(marker.getTitle());
                        TextView snippet = infoWindow.findViewById(R.id.snippet);
                        snippet.setText(marker.getSnippet());
                        return infoWindow;
                    }
                });

                // Prompt the user for permission.
                getLocationPermission();
                // Turn on the My Location layer and the related control on the map.
                updateLocationUI(googleMap);
                // Get the current location of the device and set the position of the map.
                getDeviceLocation(googleMap);

                // Get markers from api rest and show them all
                RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
                String url = "https://deplastic.netlify.app/.netlify/functions/api/markers";
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject marker = response.getJSONObject(i);
                                    LatLng location = new LatLng(marker.getDouble("latitude"), marker.getDouble("longitude"));
                                    googleMap.addMarker(new MarkerOptions().position(location).title(marker.getString("name")));
                                }
                            } catch (JSONException e) { e.printStackTrace(); }
                        }, error -> Toast.makeText(requireActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
                // Add the request to the RequestQueue.
                queue.add(jsonArrayRequest);
            });
        }
        // Return view
        return view;
    }

    // Gets the current location of the device, and positions the map's camera.
    private void getDeviceLocation(GoogleMap map) {
        try {
            if (locationPermissionGranted) {
                @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    //Updates the map's UI settings based on whether the user has granted location permission.
    @SuppressLint("MissingPermission")
    private void updateLocationUI(GoogleMap map) {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    //Prompts the user for permission to use the device location.
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

}