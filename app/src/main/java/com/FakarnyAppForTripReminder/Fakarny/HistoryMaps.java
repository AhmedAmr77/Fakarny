package com.FakarnyAppForTripReminder.Fakarny;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.FakarnyAppForTripReminder.Fakarny.database.Repository;
import com.FakarnyAppForTripReminder.Fakarny.database.TripData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class HistoryMaps extends FragmentActivity implements OnMapReadyCallback, RoutingListener {

    private GoogleMap mMap;

    Repository repository;
    List<TripData> historyTrips;
    List<HistoryTripInfo> historyTripInfoList;
    int historyCounter=0;

    int BLACK = 0xFF000000;
    int GRAY = 0xFF888888;
    int WHITE = 0xFF0000FF;
    int RED = 0xFFFF0000;
    int GREEN = 0xFF00FF00;
    int BLUE = 0xFF0000FF;
    int YELLOW = 0xFFFFFF00;
    int CYAN = 0xFF00FFFF;
    int MAGENTA = 0xFFFF00FF;
    int j = 0;
    int[] color = {RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA, BLACK, GRAY, WHITE};
    protected LatLng start = null;
    protected LatLng end = null;
    //polyline object
    private List<Polyline> polylines = null;

    int name = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        historyTripInfoList = new ArrayList<>();

        //data
        repository = new Repository(getApplication());
        repository.getDoneHistory().observeForever(new Observer<List<TripData>>() {
            @Override
            public void onChanged(List<TripData> tripData) {
                historyTrips = tripData;
                polylines = new ArrayList<>();
                mapFragment.getMapAsync(HistoryMaps.this);
                Log.i("map", "onCREATEEEE");
            }
        });

    }

    @Override
    public void onRoutingStart() {
        Log.i("map", "onRoutingStart");
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        Log.i("map", "polly start");
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;

        Log.i("map", "route of 0 Dist Valu => "+route.get(shortestRouteIndex).getDistanceValue());
        Log.i("map", "route of 0 Dist Text => "+route.get(shortestRouteIndex).getDistanceText());
        Log.i("map", "route of 0 Dura Valu => "+route.get(shortestRouteIndex).getDurationValue());
        Log.i("map", "route of 0 Dura Text => "+route.get(shortestRouteIndex).getDurationText());
        Log.i("map", "route of 0 Name => "+route.get(shortestRouteIndex).getName());
        Log.i("map", "route of 0 PoluLine => "+route.get(shortestRouteIndex).getPolyline());
        Log.i("map", "route of 0 Length => "+route.get(shortestRouteIndex).getLength());
        Log.i("map", "route of 0 Warning => "+route.get(shortestRouteIndex).getWarning());

        if (j > 8)
            j = 0;
        polyOptions.color(color[j]);
        polyOptions.width(7);
        polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
        Polyline polyline = mMap.addPolyline(polyOptions);
        polylineStartLatLng = polyline.getPoints().get(0);
        int k = polyline.getPoints().size();
        polylineEndLatLng = polyline.getPoints().get(k - 1);
        polylines.add(polyline);
        j++;

        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("Start : "+historyTrips.get(name).getTripName());
        mMap.addMarker(startMarker);
        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("End : "+historyTrips.get(name).getTripName());
        mMap.addMarker(endMarker);
        name++;

        setHistoryTripInfoList(historyTrips.get(historyCounter), route.get(shortestRouteIndex).getDistanceValue(), route.get(shortestRouteIndex).getDurationValue());
        historyCounter++;

        Log.i("map", "poly end");
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingCancelled() {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] s;
        for (int i = 0; i < historyTrips.size(); i++) {
            s = historyTrips.get(i).getLat_long_startPoint().split(",");
            start = new LatLng(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
            s = historyTrips.get(i).getLat_long_endPoint().split(",");
            end = new LatLng(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
            Findroutes(start, end);
        }
        if(start != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 6f));  
        } else {
            Toast.makeText(this, "No DONE trips ", Toast.LENGTH_SHORT).show();
        }
        Log.i("map", "onMapReadyyy");
    }

    public void Findroutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(HistoryMaps.this, "Unable to get location", Toast.LENGTH_LONG).show();
        } else {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyBr20rtMn4dMk3l0ydTGtku8AU_zCh6rRQ")  //also define your api key here.
                    .build();
            routing.execute();
        }

        Log.i("map", "FINDROUTES");
    }

    public void displayChart(View view) {
        Toast.makeText(this, "CHARTTTT", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Chart.class);
        i.putExtra("histInfo", (Serializable) historyTripInfoList);
        startActivity(i);
    }

    void setHistoryTripInfoList(TripData current, int distance, int duration){
        HistoryTripInfo historyTripInfo;
        historyTripInfo = new HistoryTripInfo(current.getTripName(), current.getAlarmTime(), distance, duration);
        historyTripInfoList.add(historyTripInfo);
    }
}