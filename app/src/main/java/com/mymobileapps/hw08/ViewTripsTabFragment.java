/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - ViewTripsTabFragment.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */

package com.mymobileapps.hw08;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.TransitionRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewTripsTabFragment extends Fragment {

    View view;
    private RecyclerView myRecyclerInProgress;
    //private ProgressBar pg_Load;
    TripUO objTask = new TripUO();
    private List<TripUO> lstViewAllTrips;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRefTrips = firebaseDatabase.getReference("Trips/");
    DatabaseReference mRefPlaces = firebaseDatabase.getReference("Trips/POI");

   // DatabaseReference mRefPlaces = firebaseDatabase.getReference("Places/");

    public ViewTripsTabFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstViewAllTrips = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_all_trips,container,false);

        myRecyclerInProgress = (RecyclerView) view.findViewById(R.id.recyler_AllTrips);
        final ViewTripsTabAdapter viewTripsTabAdapter = new ViewTripsTabAdapter(getContext(), lstViewAllTrips);
        myRecyclerInProgress.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerInProgress.setAdapter(viewTripsTabAdapter);

      /*  pg_Load = (ProgressBar) view.findViewById(R.id.pgLoad);
        pg_Load.setVisibility(View.GONE);*/

        mRefTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstViewAllTrips.clear();
                ArrayList<TripUO> AllTripsList = new ArrayList<>();

               for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    TripUO mvalue = itemSnapshot.getValue(TripUO.class);
                    DataSnapshot contentSnapshot = itemSnapshot.child("/POI");
                    Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                   TripUO c = new TripUO();
                   c.tripName = itemSnapshot.child("tripName").getValue().toString() + " - " + mvalue.getDestCity() + " (" + contentSnapshot.getChildrenCount() + ")";
                   c.userName = itemSnapshot.child("userName").getValue().toString();
                   c.tripID = itemSnapshot.child("tripID").getValue().toString();
                   AllTripsList.add(c);

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
                lstViewAllTrips.addAll(AllTripsList);
                viewTripsTabAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;



    }
}
