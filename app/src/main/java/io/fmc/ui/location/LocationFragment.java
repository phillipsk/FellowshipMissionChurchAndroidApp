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
import io.fmc.di.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;


    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_location);
        mapFragment = AppController.getInstance().getMapFragment();
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.map_google_M, mapFragment)
                    .commit();
        }
    }

    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_location,container,false);



/*            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
                mapFragment.getMapAsync(this);
                getChildFragmentManager()
                        .beginTransaction()
                        .add(R.id.map_google_M, mapFragment)
                        .commit();
            }*/

            return view;
        }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onMapReady(GoogleMap googleMap);
    }*/

    @Override
        public void onMapReady(GoogleMap googleMap) {
            MarkerOptions marker = new MarkerOptions();
//            42.387678, -71.100579
            LatLng latLng = new LatLng(42.387572, -71.101400);

//            marker.position(new LatLng(42.387678, -71.100579));
            marker.position(latLng);
            marker.title(getString(R.string.app_name_long));
            marker.snippet("Tap for directions!");


            Marker myMarker = googleMap.addMarker(marker);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));

            myMarker.showInfoWindow();

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String url = "https://goo.gl/maps/QL8GceAdJMgt3bYA6";
                    Uri mapUri = Uri.parse(url);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                    //mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    return false;
                }
            });


        }


    }
