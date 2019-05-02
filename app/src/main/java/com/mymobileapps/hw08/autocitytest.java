package com.mymobileapps.hw08;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

public class autocitytest extends AppCompatActivity implements PlaceSelectionListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment )
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(this);

    }

    public void onPlaceSelected(Place place) {

        Toast.makeText(getApplicationContext(), "" + place.getName() + place.getLatLng(), Toast.LENGTH_LONG).show();
    }

    public void onError(Status status) {
        Toast.makeText(getApplicationContext(), "" + status.toString(), Toast.LENGTH_LONG).show();
    }

}


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocitytest);


EditText txtauto;
        txtauto = (EditText) findViewById(R.id.txteditauto);

        txtauto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                  *//*  Task<AutocompletePredictionBufferResponse> results =
                            mGeoDataClient.getAutocompletePredictions(constraint.toString(), mBounds,
                                    mPlaceFilter);*//*
                    try {
                        Intent intent =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .setBoundsBias(new LatLngBounds(
                                                new LatLng(-33.880490, 151.184363),
                                                new LatLng(-33.858754, 151.229596)))
                                        .build(autocitytest.this);
                       *//* PlaceAutocompleteFragment pf =  new PlaceAutocompleteFragment();
                        pf.setBoundsBias(new LatLngBounds(
                                new LatLng(-33.880490, 151.184363),
                                new LatLng(-33.858754, 151.229596)));*//*

                        startActivityForResult(intent, CITY_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CITY_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("demo", "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("demo", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }*/


