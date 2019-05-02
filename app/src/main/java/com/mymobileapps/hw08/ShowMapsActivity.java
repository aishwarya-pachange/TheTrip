/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - ShowMapsActivity.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */
package com.mymobileapps.hw08;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String sTrip;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;

    DatabaseReference myTripRef = database.getReference("Trips/");
    private List<TripUO> lstViewMyTripsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_maps);
        setTitle("Your Plans");
        mAuth = FirebaseAuth.getInstance();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       sTrip =  getIntent().getSerializableExtra("com.mymobileapps.hw08.ITEM_DATA").toString();

        myTripRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstViewMyTripsItems = new ArrayList<>();
                lstViewMyTripsItems.clear();
                ArrayList<TripUO> AllTripsList = new ArrayList<>();

               /* if (tripKey == null || tripKey.equals("")) {
                    return;
                }*/
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    TripUO mvalue = itemSnapshot.getValue(TripUO.class);
                    TripUO obj = new TripUO();
                    if (itemSnapshot.getValue(TripUO.class).getTripID().toString().equals(sTrip)) {
                        DataSnapshot contentSnapshot = itemSnapshot.child("/POI");
                        Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                        for (DataSnapshot d1 : matchSnapShot) {
                            obj = new TripUO();
                            obj.restaurantName = d1.child("restaurantName").getValue().toString();
                            obj.dayPlanned = d1.child("dayPlanned").getValue().toString();
                            obj.placeID = d1.child("placeID").getValue().toString();
                            obj.sRestLatLng = d1.child("sRestLatLng").getValue().toString();
                            AllTripsList.add(obj);
                        }
                        //  AllTripsList.add(obj);
                    }
                    /*DataSnapshot contentSnapshot = itemSnapshot.child("/POI");
                    Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                    TripUO c = new TripUO();
                    c.tripName = itemSnapshot.child("tripName").getValue().toString() + " (" + contentSnapshot.getChildrenCount() + ")";
                    c.tripID = itemSnapshot.child("tripID").getValue().toString();
                    AllTripsList.add(c);*/

                 /*  for (DataSnapshot match : matchSnapShot){

                        TripUO c = match.getValue(TripUO.class);
                        c.tripName = itemSnapshot.child("tripName").getValue().toString();
                        AllTripsList.add(c);

                    }*/
                }
             /*   for(DataSnapshot dataSnapshot1  : dataSnapshot.getChildren()){

                    TripUO obj = dataSnapshot1.getValue(TripUO.class);
                    PlacesUO objPlaces = dataSnapshot1.getValue(PlacesUO.class);
                    dataSnapshot1.getValue(TripUO.class).getTripID().toString();
                  //  if(dataSnapshot1.getValue(TripUO.class).getTripID().toString().toUpperCase().equals("DOING")){
                    AllTripsList.add(obj);
                  //  }

                }*/
                lstViewMyTripsItems.addAll(AllTripsList);
                //addPlacesTabAdapter.notifyDataSetChanged();
                DisplayNearbyPlaces(lstViewMyTripsItems);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_person_menu_item:
                Log.d("demo", "onOptionsItemSelected: add person");
                // Intent intent = new Intent(this, HomePage.class);
                // startActivity(intent);
                ViewPager viewPager;

                viewPager = (ViewPager) findViewById(R.id.ViewPagerTabs);
                viewPager.setCurrentItem(2);
              /*Intent intent = new Intent(HomePage.this,autocitytest.class );
               startActivity(intent);*/

                return true;
            case R.id.logout_menu_item:
                //logout
                // go to the login activity
                // finish this activity
               /* Log.d(TAG, "onOptionsItemSelected: logout");
                mAuth.signOut();
*/
                mAuth.signOut();

                finish();
                Intent myIntent = new Intent(ShowMapsActivity.this, MainActivity.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void DisplayNearbyPlaces(List<TripUO> nearByPlacesList)
    {
        for (int i=0; i<nearByPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();

           // HashMap<String, String> googleNearbyPlace = nearByPlacesList.get(i);
            String nameOfPlace = nearByPlacesList.get(i).restaurantName;
            String latlngRef = nearByPlacesList.get(i).sRestLatLng;
            String[] strCoordinates = new String[3];

            strCoordinates = latlngRef.split(",");
            double lat = Double.parseDouble((strCoordinates[0]));
            double lng = Double.parseDouble((strCoordinates[1]));
            String placeId = strCoordinates[2];

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);

            markerOptions.title(nameOfPlace + ", Day Planned - " + nearByPlacesList.get(i).dayPlanned);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));


            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

            double iMeter =  1609.34;
            /*Circle circle = null;
            circle.remove();*/
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lng))
                    .radius(iMeter) // Converting Miles into Meters...
                    .strokeColor(Color.RED)
                    .strokeWidth(5));
            circle.setVisible(false);
            float currentZoomLevel = getZoomLevel(circle);
            float animateZomm = currentZoomLevel + 5;

            Log.d("Zoom Level:", currentZoomLevel + "");
            Log.d("Zoom Level Animate:", animateZomm + "");

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), animateZomm));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel), 2000, null);
            Log.e("Circle Lat Long:", lat + ", " + lng);

        }


    }
    public float getZoomLevel(Circle circle) {
        float zoomLevel=0;
        if (circle != null){
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel =(int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel +.5f;
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
