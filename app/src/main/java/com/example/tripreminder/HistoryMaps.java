package com.example.tripreminder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class HistoryMaps extends FragmentActivity implements OnMapReadyCallback, RoutingListener {

    private GoogleMap mMap;

    Repository repository;
    List<TripData> historyTrips;

    int BLACK = 0xFF000000;
    int GRAY = 0xFF888888;
    int WHITE = 0xFFFFFFFF;
    int RED = 0xFFFF0000;
    int GREEN = 0xFF00FF00;
    int BLUE  = 0xFF0000FF;
    int YELLOW  = 0xFFFFFF00;
    int CYAN  = 0xFF00FFFF;
    int MAGENTA = 0xFFFF00FF;
    int j=0;
    int [] color = {RED ,GREEN, BLUE,YELLOW, CYAN ,MAGENTA,BLACK,GRAY,WHITE};
    /////////////////////////
    protected LatLng start=null;
    protected LatLng end=null;
    //polyline object
    private List<Polyline> polylines=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //data
        repository=new Repository(getApplication());
        repository.getHistory().observeForever(new Observer<List<TripData>>() {
            @Override
            public void onChanged(List<TripData> tripData) {
                historyTrips = tripData;
            }
        });

        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] s;
        for (int i = 0; i < historyTrips.size(); i++) {
            s=historyTrips.get(i).getLat_long_startPoint().split(",");
            start = new LatLng(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
            s=historyTrips.get(i).getLat_long_endPoint().split(",");
            end = new LatLng(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
            Findroutes(start,end);
            Toast.makeText(this, "one", Toast.LENGTH_SHORT).show();
        }

        /*for (int i = 0; i < allStartPoints.size(); i++) {

            locationStart=dam1;
            locationEnd=dam4;

            if (locationStart != null && locationEnd != null) {
                mMap.addMarker(new MarkerOptions().position(locationStart).title("Start Point OF " + "Trip1"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationStart, 10f));
                if (j > 8)
                    j = 0; //chande color from begining
                mMap.addPolyline(
                        new PolylineOptions().add(locationStart).add(locationEnd).width(5f).color(color[j])
                );
                mMap.addMarker(new MarkerOptions().position(locationEnd).title("End Point OF" + "Trip1"));
                locationStart = null;
                locationEnd = null;
                j++;
            }
        }*/
        //----------------------------------------------------------------------------------------------
    }
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
        Log.i("map","Routing Fail");
        Findroutes(start,end);
        Log.i("map","Routing Fail called again");
    }
    public void Findroutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            Toast.makeText(HistoryMaps.this,"Unable to get location", Toast.LENGTH_LONG).show();
        }
        else
        {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyBr20rtMn4dMk3l0ydTGtku8AU_zCh6rRQ")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        Log.i("map","polly start");
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if(polylines!=null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng=null;
        LatLng polylineEndLatLng=null;

        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {
            if(i==shortestRouteIndex)
            {
                polyOptions.color(GREEN);
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);
            }
            else {
            }
        }
        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);
        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);
        Log.i("map","poly end");
    }
    @Override
    public void onRoutingCancelled() {
    }
    @Override
    public void onRoutingStart() {
        Toast.makeText(this,"Finding Route...",Toast.LENGTH_LONG).show();
    }

}