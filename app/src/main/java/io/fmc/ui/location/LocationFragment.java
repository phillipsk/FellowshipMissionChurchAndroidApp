package io.fmc.ui.location;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.fmc.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class    LocationFragment extends Fragment implements OnMapReadyCallback {


    public LocationFragment() {
        // Required empty public constructor
    }


        SupportMapFragment mapFragment;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_location,container,false);



            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
                mapFragment.getMapAsync(this);
                getChildFragmentManager()
                        .beginTransaction()
                        .add(R.id.map_google_M, mapFragment)
                        .commit();
            }

            return view;
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(42.3284719,-71.097651));
            marker.title("Fellowship Mission Church");
            marker.snippet("Click for directions!");


            Marker myMarker = googleMap.addMarker(marker);
            LatLng center = new LatLng(42.3284719,-71.097651);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center,17));

            myMarker.showInfoWindow();

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String url = "https://www.google.com/maps/place/Fellowship+Mission+Church/@42.3284719,-71.097651,17z";
                    Uri mapUri = Uri.parse(url);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                    //mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    return false;
                }
            });


        }


    }
