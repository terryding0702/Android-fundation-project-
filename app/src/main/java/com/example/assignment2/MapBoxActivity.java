package com.example.assignment2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;

import java.util.ArrayList;

public class MapBoxActivity extends AppCompatActivity {

    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private final LatLng SAN_FRAN = new LatLng(37.7749, -122.4194);
    ArrayList<String> mMarkerLocationNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());
        setContentView(R.layout.content_map);
        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                //mMapView.setStreetMode();
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SAN_FRAN, 11));
                addMarker(mMapboxMap, SAN_FRAN, "San Francisco", "Welcome to San Fran");
            }
        });

    }

//    private void addMarker(MapboxMap mapboxMap) {
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(SAN_FRAN);
//        markerOptions.title("MapQuest");
//        markerOptions.snippet("Welcome to San Francisco!");
//        mapboxMap.addMarker(markerOptions);
//    }

    private void addMarker(MapboxMap mapboxMap, LatLng latlng, String title, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title(title);
        markerOptions.snippet(snippet);
        mapboxMap.addMarker(markerOptions);
    }

    private void addMarkerLocation(String location){
        mMarkerLocationNames.add(location);
        updateMapView();
    }

    private void updateMapView(){
        for (String location : mMarkerLocationNames){
            GetLatLngTask task = new GetLatLngTask();
            task.execute(location);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    private static final int MSG_UPDATE_MARK = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_MARK:
                    Bundle data = msg.getData();
                    LatLng latLng = (LatLng) data.getParcelable("latlng");
                    String locationName = data.getString("locationName");
                    String snippet = "Welcome to " + locationName;

                    //addMarker(mMapboxMap, latLng, locationName, snippet);
            }
        }
    };



    class GetLatLngTask extends AsyncTask<String, Void, LatLng> {
        String locationName = "";
        @Override
        protected LatLng doInBackground(String... strings) {
            locationName = strings[0];
            String resut = MapLocation.getFromLocationName(locationName);
            double lat = MapLocation.getLatitude(resut);
            double lng = MapLocation.getLngitude(resut);
            return new LatLng(lat, lng);
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
//            super.onPostExecute(latLng);
            Message message = new Message();
            Bundle data = new Bundle();
            data.putParcelable("latlng", latLng);
            data.putString("locationName", locationName);
            message.what = MSG_UPDATE_MARK;
            message.setData(data);
            mHandler.sendMessage(message);
        }
    }

}
