package org.deplastic.Deplastic.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.deplastic.Deplastic.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(googleMap -> {
                // When map is loaded
                //TODO import variables from BD
                LatLng sydney = new LatLng(-33.852, 151.211);
                LatLng barcelona = new LatLng(-30.852, 150.211);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                googleMap.addMarker(new MarkerOptions().position(barcelona).title("Marker in Barcelona"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            });
        }
        // Return view
        return view;
    }
}