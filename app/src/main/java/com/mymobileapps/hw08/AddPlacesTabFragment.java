/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - AddPlacesTabFragment.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */

package com.mymobileapps.hw08;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddPlacesTabFragment extends Fragment implements GetNearByRestaurants.PlacesListIData {


/*public class AddPlacesTabFragment extends android.support.v4.app.Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private static final String TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    TextView mNameView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));*/

    View view;
    private RecyclerView myRecyclerTodo;
    private EditText txt_Place;
    private EditText txt_Date;
    private EditText txt_NameofTrip;
    private EditText txt_Destination;
    private ImageView img_CreateNew;
    private ProgressBar prog_Loading;
    private ImageView btn_NewPlace;
    private Button btn_CreateTrip;
    private Integer iPlacesCount = 0;
    private TripUO objTripPlacesUO;
    private TripUO objTripUO;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Calendar myCalendar = Calendar.getInstance();
    // private List<TasksUO> lstObjTasks;
    // TasksUO objTaskUO = new TasksUO();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myPlacesRef = database.getReference("Places/");
    DatabaseReference myTripRef = database.getReference("Trips/");
    DatabaseReference myPOIRef = database.getReference("Trips/POI");

    private RecyclerView myRecyclerTrips;
    //private ProgressBar pg_Load;
    TripUO objTask = new TripUO();
    private List<TripUO> lstViewMyTrips;
    private Spinner spinner;
   private HashMap<Integer, String> spinnerMap;
    String tripKey;
    String placeKey;
    String poiKey;
    private FirebaseAuth mAuth;
    private DatePickerDialog mDatePickerDialog;
    private String currTripID;
    private LatLng currCitylatlang;
    private LatLng currRestaurantlatlang;

    private String scurrCityLat;
    private String scurrCitylong;
    private String scurrRestaurantLat;
    private String scurrRestaurantLong;
    private StringBuilder sbGoogleURL = new StringBuilder();
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    String googleplaceData;
    ArrayList lstRestaurants = new ArrayList();
    ArrayList<PlacesUO> arrPlaces = new ArrayList<>();
    ArrayList<PlacesUO> arrAllPlacedetailslist = new ArrayList<>();

  /*  private static final String TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    TextView mNameView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));*/


    public AddPlacesTabFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        // lstViewAllTrips = new ArrayList<>();
        lstViewMyTrips = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.add_place_tab_fragment, container, false);
        myRecyclerTrips = (RecyclerView) view.findViewById(R.id.recyler_MyPlaces);
        final AddPlacesTabAdapter addPlacesTabAdapter = new AddPlacesTabAdapter(getContext(), lstViewMyTrips);
        myRecyclerTrips.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerTrips.setAdapter(addPlacesTabAdapter);
        prog_Loading = (ProgressBar) view.findViewById(R.id.progLoading);


        //auto complete //

     /*   mAutocompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        mNameView = (TextView) view.findViewById(R.id.txtDestCity);

        try {
            mGoogleApiClient = new GoogleApiClient.Builder(view.getContext())
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                    .addConnectionCallbacks(this)
                    .build();
            mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
            mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                    BOUNDS_MOUNTAIN_VIEW, null);
            mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        } catch (Exception ex) {
            Log.d(TAG, "onCreateView: " + ex.getMessage());
        }
*/

        txt_NameofTrip = (EditText) view.findViewById(R.id.txtTrip);

        txt_Destination = (EditText) view.findViewById(R.id.txtDestCity);

        txt_Destination.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                  /*  Task<AutocompletePredictionBufferResponse> results =
                            mGeoDataClient.getAutocompletePredictions(constraint.toString(), mBounds,
                                    mPlaceFilter);*/
                    try {
                        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                .setCountry("US")
                                .build();
                        Intent intent =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .setFilter(typeFilter)
                                        /* .setBoundsBias(new LatLngBounds(
                                                 new LatLng(-33.8902, 151.1759),
                                                 new LatLng(-33.8474, 151.2631)))*/
                                        .build(AddPlacesTabFragment.this.getActivity());
                       /* PlaceAutocompleteFragment pf =  new PlaceAutocompleteFragment();
                        pf.setBoundsBias(new LatLngBounds(
                                new LatLng(-33.880490, 151.184363),
                                new LatLng(-33.858754, 151.229596)));*/

                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }
                }
                return false;
            }
        });
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spinner.getSelectedItem().toString();
                String restaurantid = spinnerMap.get(spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txt_Place = (EditText) view.findViewById(R.id.txtPlace);

      /*  txt_Place.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                  *//*  Task<AutocompletePredictionBufferResponse> results =
                            mGeoDataClient.getAutocompletePredictions(constraint.toString(), mBounds,
                                    mPlaceFilter);*//*
         *//* try {*//*
         *//*    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                .setCountry("US")
                                .build();
                        Intent intent =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .setFilter(typeFilter)
                                        *//**//* .setBoundsBias(new LatLngBounds(
                                                 new LatLng(-33.8902, 151.1759),
                                                 new LatLng(-33.8474, 151.2631)))*//**//*
                                        .build(AddPlacesTabFragment.this.getActivity());
                       *//**//* PlaceAutocompleteFragment pf =  new PlaceAutocompleteFragment();
                        pf.setBoundsBias(new LatLngBounds(
                                new LatLng(-33.880490, 151.184363),
                                new LatLng(-33.858754, 151.229596)));*//**//*

                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);*//*//AIzaSyC15BLJAfrAMy56vpswOr81AEJ0qyZOtt0
         *//* StringBuilder sbGoogleURL = new StringBuilder();*//*
                    sbGoogleURL.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + scurrCityLat + "," + scurrCitylong + "&radius=1609&type=restaurant&key=AIzaSyCTcVlioIq357CJCQAjLOfvydbzrbmVijs");


                    try {
                        // googleplaceData = ReadTheURL(sbGoogleURL.toString());
                            *//*GetNearByRestaurants getNearbyPlaces = new GetNearByRestaurants();

                            getNearbyPlaces.execute(sbGoogleURL.toString());*//*
                        //  new GetNearByRestaurants(getActivity()).execute("http://dev.theappsdr.com/apis/photos/keywords.php", strAPIName);

                        // new GetNearByRestaurants((GetNearByRestaurants.PlacesListIData))(view.getContext()).execute(sbGoogleURL.toString());


                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    *//* } *//**//*catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }*//**//* catch (Exception ex) {
                        Log.d("demo", "onTouch: " + ex.getMessage());
                    }*//*
                }
                return false;
            }
        });*/
        //  /*new LatLng(-33.880490, 151.184363),
        //                                                new LatLng(-33.858754, 151.229596)))
        //
        //  new google.maps.LatLng(-33.8902, 151.1759),
        //  new google.maps.LatLng(-33.8474, 151.2631)
        //
        // */

        //--auto complete //
        txt_Date = (EditText) view.findViewById(R.id.txtDatePicker);


        txt_Date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(v.getContext(), mDateSetListener, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }

                return false;
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                view.setMinDate(System.currentTimeMillis() - 1000);
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                txt_Date.setText(sdf.format(myCalendar.getTime()));
                txt_Date.setError(null);
            }

        };

        btn_NewPlace = (ImageView) view.findViewById(R.id.imgCreateNew);
        btn_NewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(iPlacesCount >= 15)
                {
                    Toast.makeText(view.getContext(), "Maximum count(15) reached. Cannot add more!", Toast.LENGTH_SHORT).show();
                    return;
                }
                txt_Place = (EditText) view.findViewById(R.id.txtPlace);
                txt_Date = (EditText) view.findViewById(R.id.txtDatePicker);
               /* txt_Date = (EditText) view.findViewById(R.id.txtDatePicker);
                txt_Date = (EditText) view.findViewById(R.id.txtDatePicker);*/


                txt_NameofTrip = (EditText) view.findViewById(R.id.txtTrip);
                txt_Destination = (EditText) view.findViewById(R.id.txtDestCity);
                // if(tripKey == null || tripKey.equals("")) {
                tripKey = AddTripToFirebase();
                if (tripKey != null && !tripKey.equals("")) {
                    txt_NameofTrip.setEnabled(false);
                    txt_Destination.setEnabled(false);
                    //txt_Place.setText("");
                    spinner.setSelection(0);
                    txt_Date.setText("");

                }
                //  }


                //   AddPlacesToFirebase(tripKey);

               /* ViewPager viewPager;
                viewPager = (ViewPager) getActivity().findViewById(R.id.ViewPagerTabs);
                viewPager.setCurrentItem(1);*/

            }
        });

        btn_CreateTrip = (Button) view.findViewById(R.id.btnCreateTrip);
        btn_CreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        txt_NameofTrip.setText("");
        txt_NameofTrip.setEnabled(true);
        txt_Destination.setEnabled(true);
        txt_Destination.setText("");
        txt_Date.setText("");
        spinner.setAdapter(null);
        spinnerMap.clear();
        tripKey = "";
                lstViewMyTrips.clear();

            }
        });
        myTripRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstViewMyTrips.clear();
                ArrayList<TripUO> AllTripsList = new ArrayList<>();

                if (tripKey == null || tripKey.equals("")) {
                    AllTripsList.clear();
                    return;
                }
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    TripUO mvalue = itemSnapshot.getValue(TripUO.class);
                    TripUO obj = new TripUO();
                    if (itemSnapshot.getValue(TripUO.class).getTripID().toString().equals(tripKey)) {
                        DataSnapshot contentSnapshot = itemSnapshot.child("/POI");
                        Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                        for (DataSnapshot d1 : matchSnapShot) {
                            obj = new TripUO();
                            obj.restaurantName = d1.child("restaurantName").getValue().toString();
                            obj.dayPlanned = d1.child("dayPlanned").getValue().toString();
                            obj.placeID = d1.child("placeID").getValue().toString();
                            obj.tripID = d1.child("tripID").getValue().toString();

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
                lstViewMyTrips.addAll(AllTripsList);
                iPlacesCount = lstViewMyTrips.size();
                addPlacesTabAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




       /* myTripRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstViewMyTrips.clear();
                ArrayList<TripUO> AllTripsList = new ArrayList<>();
                if ( tripKey == null) {
                return;
                }


                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    TripUO mvalue = itemSnapshot.getValue(TripUO.class);
                    DataSnapshot contentSnapshot = itemSnapshot.child("/POI");
                    Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();

                   *//* c.tripName = itemSnapshot.child("tripName").getValue().toString() + " (" + contentSnapshot.getChildrenCount() + ")";
                    c.tripID = itemSnapshot.child("tripID").getValue().toString();
                    AllTripsList.add(c);*//*

                    for (DataSnapshot match : matchSnapShot) {
                        TripUO c = new TripUO();
                         c = match.getValue(TripUO.class);
                        c.restaurantName = contentSnapshot.child("restaurantName").getValue().toString();
                        AllTripsList.add(c);

                    }
                }
             /*   for(DataSnapshot dataSnapshot1  : dataSnapshot.getChildren()){

                    TripUO obj = dataSnapshot1.getValue(TripUO.class);
                    PlacesUO objPlaces = dataSnapshot1.getValue(PlacesUO.class);
                    dataSnapshot1.getValue(TripUO.class).getTripID().toString();
                  //  if(dataSnapshot1.getValue(TripUO.class).getTripID().toString().toUpperCase().equals("DOING")){
                    AllTripsList.add(obj);
                  //  }

                }*//*
                lstViewMyTrips.addAll(AllTripsList);
                viewTripsTabAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        return view;


    }

    /*public String ReadTheURL(String placeURL) throws IOException
    {
        String Data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try
        {
            URL url = new URL(placeURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            String line = "";

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuffer.append(line);
            }

            Data = stringBuffer.toString();
            bufferedReader.close();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            inputStream.close();
            httpURLConnection.disconnect();
        }

        return Data;
    }*/
    private String AddTripToFirebase() {
        if (ValidateControls()) {
            prog_Loading.setVisibility(View.VISIBLE);
            if (tripKey == null || tripKey.equals("")) {
                tripKey = myTripRef.push().getKey();
                //}
                objTripUO = new TripUO();
                objTripPlacesUO = new TripUO();
                objTripUO.setTripID(tripKey);
                objTripUO.setTripName(txt_NameofTrip.getText().toString().trim());
                objTripUO.setDestCity(txt_Destination.getText().toString());
                objTripUO.setTripCount(0);
                objTripUO.setUserName(mAuth.getCurrentUser().getDisplayName());
                objTripUO.setUserID(mAuth.getCurrentUser().getUid());
                //objTripUO.setCityLatLan(currCitylatlang);
                objTripUO.setCurrCityLat(String.valueOf(currCitylatlang.latitude));
                objTripUO.setCurrCityLong(String.valueOf(currCitylatlang.longitude));
                myTripRef.child(tripKey).setValue(objTripUO);
            }
            // objPlacesUO.setRestaurantName(txt_Place.getText().toString().trim());
            // objPlacesUO.setDayPlanned(txt_Date.getText().toString());

            objTripPlacesUO.setTripID(tripKey);
//            objTripPlacesUO.setRestaurantName(txt_Place.getText().toString().trim());

            String name = spinner.getSelectedItem().toString();
            String restaurantid = spinnerMap.get(spinner.getSelectedItemPosition());

            objTripPlacesUO.setRestaurantName(name);
            objTripPlacesUO.setsRestLatLng(restaurantid);
            objTripPlacesUO.setDayPlanned(txt_Date.getText().toString());
            objTripPlacesUO.setTripCount(1);
            objTripPlacesUO.setUserName(mAuth.getCurrentUser().getDisplayName());
            objTripPlacesUO.setUserID(mAuth.getCurrentUser().getUid());


//            objTripPlacesUO.setPlaceID(placeKey);
            // myTripRef.child(tripKey).child("POI").setValue(objPlacesUO);
            poiKey = myPOIRef.push().getKey();
            // myPlacesRef.child(poiKey).setValue(objPlacesUO);
            objTripPlacesUO.setPlaceID(poiKey);

            myTripRef.child(tripKey).child("POI").child(poiKey).setValue(objTripPlacesUO);
            Toast.makeText(view.getContext(),
                    "Trip added successfully", Toast.LENGTH_SHORT).show();
            //  mAdapter.notifyDataSetChanged();
          /*  txt_NameofTrip.setText("");
            txt_Destination.setText("");*/
            prog_Loading.setVisibility(View.INVISIBLE);

            return objTripUO.tripID.toString().trim();

        }
        return "";
/*
        return objTripUO.tripID.toString().trim();
*/
    }


    private void AddPlacesToFirebase(String tripid) {
        if (ValidateControls()) {
            prog_Loading.setVisibility(View.VISIBLE);
            placeKey = myPlacesRef.push().getKey();


            Fragment fragment = new AddTripTabFragment();

            objTripPlacesUO = new TripUO();
            objTripPlacesUO.setTripID(tripid);
            objTripPlacesUO.setRestaurantName(txt_Place.getText().toString().trim());
            objTripPlacesUO.setDayPlanned(txt_Date.getText().toString());
            objTripPlacesUO.setTripCount(1);
            objTripPlacesUO.setUserName(mAuth.getCurrentUser().getDisplayName());
            objTripPlacesUO.setUserID(mAuth.getCurrentUser().getUid());
            objTripPlacesUO.setPlaceID(placeKey);

            myPlacesRef.child(placeKey).setValue(objTripPlacesUO);
            Toast.makeText(view.getContext(),
                    "Trip added successfully", Toast.LENGTH_SHORT).show();
            //  mAdapter.notifyDataSetChanged();
            txt_Place.setText("");
            txt_Date.setText("");
            txt_NameofTrip.setEnabled(false);
            txt_Destination.setEnabled(false);

            prog_Loading.setVisibility(View.INVISIBLE);
        }
    }

    public Boolean ValidateControls() {
       /* if (txt_Place.getText().toString().trim() == null || txt_Place.getText().toString().trim().equals("")) {
            txt_Place.setError("Enter Restaurant");
            return false;
        }*/
        if (txt_NameofTrip.getText().toString().trim() == null || txt_NameofTrip.getText().toString().trim().equals("")) {
            txt_NameofTrip.setError("Enter Trip Name");
            return false;
        } else if (txt_Destination.getText().toString().trim() == null || txt_Destination.getText().toString().trim().equals("")) {
            txt_Destination.setError("Enter city");
            return false;
        } else if (spinner.getSelectedItem() == null) {
            txt_Destination.setError("Select a Restaurant");
            return false;
        } else if (txt_Date.getText().toString().trim() == null || txt_Date.getText().toString().trim().equals("")) {
            txt_Date.setError("Select a day");
            return false;
        }
        return true;
    }

  /*  @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            mNameView.setText(Html.fromHtml(place.getAddress() + ""));


        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }*/


   /* @Override
    public void onConnected(@Nullable Bundle bundle) {

        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");

    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(view.getContext(), data);
                txt_Destination = (EditText) view.findViewById(R.id.txtDestCity);
                txt_Destination.setText(place.getName());
                txt_Destination.setError(null);
                Log.i("demo", "Place: " + place.getName() + place.getLatLng().toString());
                currCitylatlang = place.getLatLng();
                scurrCityLat = String.valueOf(currCitylatlang.latitude);
                scurrCitylong = String.valueOf(currCitylatlang.longitude);

                /*GetNearByRestaurants getNearbyPlaces = new GetNearByRestaurants();

                getNearbyPlaces.execute(sbGoogleURL.toString());*/
                sbGoogleURL.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + scurrCityLat + "," + scurrCitylong + "&radius=1609&type=restaurant&key=AIzaSyCTcVlioIq357CJCQAjLOfvydbzrbmVijs");
                //sbGoogleURL https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number&key=YOUR_API_KEY

                new GetNearByRestaurants(this).execute(sbGoogleURL.toString());
                sbGoogleURL = new StringBuilder();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(view.getContext(), data);
                // TODO: Handle the error.
                Log.i("demo", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    @Override
    public void placesListPostExecute(List<HashMap<String, String>> nearByPlacesList) {
        Log.d("demo", "placesListPostExecute: " + nearByPlacesList.size());
        // lstRestaurants = new List();
        ArrayList<HashMap> arrlst = new ArrayList<HashMap>();
        Spinner sp = (Spinner) view.findViewById(R.id.spinner);
        arrPlaces.clear();
        arrlst.clear();
        PlacesUO obj = new PlacesUO();
        lstRestaurants.addAll(nearByPlacesList);
        for (int i = 0; i < nearByPlacesList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> googleNearbyPlace = nearByPlacesList.get(i);
            String nameOfPlace = googleNearbyPlace.get("place_name");
            String vicinity = googleNearbyPlace.get("vicinity");
            double lat = Double.parseDouble(googleNearbyPlace.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlace.get("lng"));
            obj = new PlacesUO();
            obj.setPlaceID(googleNearbyPlace.get("reference"));
            obj.setRestaurantName(googleNearbyPlace.get("place_name"));

            // arrPlaces.add(nameOfPlace, vicinity);
            currRestaurantlatlang = new LatLng(lat, lng);
            // obj.restaurantLatLan = currRestaurantlatlang;
            obj.currRestaurantLat = String.valueOf(lat);
            obj.currRestaurantLon = String.valueOf(lng);
            arrPlaces.add(obj);

            arrAllPlacedetailslist.add(obj);
            arrlst.add(googleNearbyPlace);


        }
        String[] spinnerArray = new String[arrPlaces.size()];
        spinnerMap = new HashMap<Integer, String>();
        for (int j = 0; j < arrPlaces.size(); j++) {
            spinnerMap.put(j, arrPlaces.get(j).currRestaurantLat + "," + arrPlaces.get(j).currRestaurantLon + "," + arrPlaces.get(j).placeID);
            spinnerArray[j] = arrPlaces.get(j).restaurantName;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setPrompt("Select a Restaurant");
      /*  AlertDialog.Builder builderList = new AlertDialog.Builder(getActivity());

        builderList.setTitle("Choose a Keyword");
        final ArrayAdapter<PlacesUO> arrAdaptor = new ArrayAdapter<PlacesUO>(getActivity(), android.R.layout.simple_dropdown_item_1line);
        arrAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrAdaptor.addAll(arrPlaces);
        sp.setAdapter(arrAdaptor);*/
      /*  final ArrayAdapter<HashMap<String, String>> arrAdaptor = new ArrayAdapter<HashMap<String, String>>(getActivity(), android.R.layout.simple_dropdown_item_1line);
        arrAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrAdaptor.addAll(nearByPlacesList);
        sp.setAdapter(arrAdaptor);*/
       /* ArrayAdapter<String> adapter =
                new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);*/
       /* builderList.setAdapter(arrAdaptor, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selectedKeyword) {
                String strKeyword = arrAdaptor.getItem(selectedKeyword).restaurantName;
                Log.d("demo", strKeyword);
                txt_Place.setText(strKeyword.toUpperCase());

                dialog.dismiss();


            }
        });

        builderList.show();*/
        //Log.d("demo", "placesListPostExecute: " +  nameOfPlace +"," + lat + "," + lng);

    }
}
