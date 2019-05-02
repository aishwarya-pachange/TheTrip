/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - AddPlacesTabAdapter.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */
package com.mymobileapps.hw08;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AddPlacesTabAdapter extends  RecyclerView.Adapter<AddPlacesTabAdapter.AddPlacesTabViewHolder>{

    Context context;
    List<TripUO> lstMyTrips;

    public AddPlacesTabAdapter(Context context, List<TripUO> myTripsList) {
        this.context = context;
        this.lstMyTrips = myTripsList;
    }

    @NonNull
    @Override
    public AddPlacesTabAdapter.AddPlacesTabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.custom_my_trip_item,viewGroup,false);
        AddPlacesTabAdapter.AddPlacesTabViewHolder VH = new AddPlacesTabAdapter.AddPlacesTabViewHolder(view);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull AddPlacesTabAdapter.AddPlacesTabViewHolder addPlacesTabViewHolder, int i) {

        addPlacesTabViewHolder.txt_Place.setText(lstMyTrips.get(i).getRestaurantName());
        addPlacesTabViewHolder.txt_Day.setText(lstMyTrips.get(i).getDayPlanned());

        TripUO obj = lstMyTrips.get(i);
        addPlacesTabViewHolder.txt_Place.setText(obj.getRestaurantName());
        addPlacesTabViewHolder.txt_Day.setText(obj.getDayPlanned());
        addPlacesTabViewHolder.id = obj.getPlaceID();
        addPlacesTabViewHolder.trip_id = obj.getTripID();
    }

    @Override
    public int getItemCount() {
        return lstMyTrips.size();
    }

    public static class AddPlacesTabViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_Place;
        private TextView txt_Day;
        private ImageView img_btnDelete;
        private String trip_id;
        //private ProgressBar pg_progress;

        private String id;

        public String status = "";
        public AddPlacesTabViewHolder(@NonNull final View itemView) {
            super(itemView);

            txt_Place = (TextView) itemView.findViewById(R.id.txtPlaceName);
            txt_Day = (TextView) itemView.findViewById(R.id.txtDayPlanned);
            // pg_progress = (ProgressBar) itemView.findViewById(R.id.pgLoad);

            img_btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);

            img_btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // pg_progress.setVisibility(View.VISIBLE);

                    Log.d("demo", "Clicked on ID " + id);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference("Trips/");
                    //    final String time = String.valueOf(Calendar.getInstance().getTime());

                    myRef.child(trip_id).child("POI").child(id).removeValue();

               /*     DatabaseReference dref2 = myRef.child(id);


                    dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            myRef.child(id).removeValue();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                    // pg_progress.setVisibility(View.GONE);

                }
            });
        }
    }
}
