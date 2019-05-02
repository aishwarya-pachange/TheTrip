/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - ViewTripsTabAdapter.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */

package com.mymobileapps.hw08;

import android.content.Context;
import android.content.Intent;
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

public class ViewTripsTabAdapter extends  RecyclerView.Adapter<ViewTripsTabAdapter.ViewTripsTabViewHolder>{

    Context context;
    List<TripUO> lstAllTrips;


    public ViewTripsTabAdapter(Context context, List<TripUO> allTrips) {
        this.context = context;
        this.lstAllTrips = allTrips;
    }

    @NonNull
    @Override
    public ViewTripsTabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.custom_view_trip_item,viewGroup,false);
        ViewTripsTabViewHolder VH = new ViewTripsTabViewHolder(view);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTripsTabViewHolder viewTripsTabViewHolder, int i) {

        viewTripsTabViewHolder.txt_Trip.setText(lstAllTrips.get(i).getTripName());
        viewTripsTabViewHolder.txt_User.setText(lstAllTrips.get(i).getUserName());

        TripUO obj = lstAllTrips.get(i);
        viewTripsTabViewHolder.txt_Trip.setText(obj.getTripName());
        viewTripsTabViewHolder.id = obj.getTripID();
    }

    @Override
    public int getItemCount() {
        return lstAllTrips.size();
    }

    public static class ViewTripsTabViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_Trip;
        private TextView txt_User;
        private ImageView img_btnShowInMap;

        //private ProgressBar pg_progress;

        private String id;
        public String status = "";
        public ViewTripsTabViewHolder(@NonNull final View itemView) {
            super(itemView);

            txt_Trip = (TextView) itemView.findViewById(R.id.txtTripitem);
            txt_User = (TextView) itemView.findViewById(R.id.txtUser);
            // pg_progress = (ProgressBar) itemView.findViewById(R.id.pgLoad);
            img_btnShowInMap = (ImageView) itemView.findViewById(R.id.btnShowMap);

            img_btnShowInMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapsIntent = new Intent(v.getContext(), ShowMapsActivity.class);
                   // mapsIntent.putExtra(TRIPITEM,id);
                   mapsIntent.putExtra("com.mymobileapps.hw08.ITEM_DATA", id);

                    itemView.getContext().startActivity(mapsIntent);
                  /*  // pg_progress.setVisibility(View.VISIBLE);

                    Log.d("demo", "Clicked on ID " + id);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference("MyTasks/");
                    //    final String time = String.valueOf(Calendar.getInstance().getTime());


                    DatabaseReference dref2 = myRef.child(id);
                    dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            status = dataSnapshot.child("status").getValue(String.class);

                            if (status.toUpperCase().equals("DOING")) {
                                myRef.child(id).child("status").setValue("Done");
                                // myRef.child(id).child("deadline").setValue(time);
                                Toast.makeText(itemView.getContext(), "Task completed!", Toast.LENGTH_SHORT).show();
                            }*//* else if (status.toUpperCase().equals("COMPLETED")) {
                                myRef.child(id).child("status").setValue("pending");
                                myRef.child(id).child("time").setValue(time);
                                Toast.makeText(itemView.getContext(), "Task pending", Toast.LENGTH_SHORT).show();
                            }*//*
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
