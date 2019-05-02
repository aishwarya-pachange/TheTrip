/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - AddTripTabFragment.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */
package com.mymobileapps.hw08;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTripTabFragment extends Fragment {

    View view;
    private RecyclerView myRecyclerTodo;
    private EditText txt_TripName;
    private EditText txt_City;
    private ImageView img_CreateNew;
    private ProgressBar prog_Loading;
    private Button btn_AddTrip;
    private Button btn_Clear;

    private TripUO objTripUO;
    private TextView txt_Date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Calendar myCalendar = Calendar.getInstance();
    // private List<TasksUO> lstObjTasks;
    // TasksUO objTaskUO = new TasksUO();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Trips/");
    String key;
    private FirebaseAuth mAuth;
    public static final String TRIP_ID= "tripid";

    public AddTripTabFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // lstObjTasks = new ArrayList<>();
       /* lstObjTasks.add(new TasksUO("Test task1", "Nov 23 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task2", "Nov 24 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task3", "Nov 25 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task4", "Nov 26 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task5", "Nov 27 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task6", "Nov 28 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task7", "Nov 29 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task8", "Nov 23 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task9", "Nov 23 2018", "Pending", UUID.randomUUID().toString()));
        lstObjTasks.add(new TasksUO("Test task10", "Nov 23 2018", "Pending", UUID.randomUUID().toString()));
*/

        mAuth = FirebaseAuth.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.add_trip_tab_fragment, container, false);
        prog_Loading = (ProgressBar) view.findViewById(R.id.pbLoad);

        btn_AddTrip = (Button) view.findViewById(R.id.btnAddTrip);
        btn_AddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_TripName = (EditText)view.findViewById(R.id.txtTripName);
                txt_City = (EditText)view.findViewById(R.id.txtCity);
                String TripId = AddTripToFirebase();
                TextView lblTrip = (TextView) view.findViewById(R.id.hiddenTrip);
                lblTrip.setText(TripId);
                ViewPager viewPager;
                viewPager = (ViewPager) getActivity().findViewById(R.id.ViewPagerTabs);
               // Bundle bundle = getArguments();
              // savedInstanceState.putString(TRIP_ID, TripId);

                Bundle myBundle = new Bundle();
                myBundle.putString("Trip", TripId);

                FragmentManager mManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fTrans = mManager.beginTransaction();


                AddPlacesTabFragment tab = new AddPlacesTabFragment();
                tab.setArguments(myBundle);

                fTrans.commit();

                //savedInstanceState = myBundle;
/*
                Fragment fragment=new AddTripTabFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TRIP_ID,TripId);
                fragment.setArguments(bundle);*/

                viewPager.setCurrentItem(1);

            }
        });

        btn_Clear = (Button) view.findViewById(R.id.btnClear);
        btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_TripName.setText("");
                txt_City.setText("");

            }
        });

/*
        txt_DatePicker = (EditText) view.findViewById(R.id.editDate);


        //EditText edittext= (EditText) findViewById(R.id.Birthday);
       mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
               view.setMinDate(System.currentTimeMillis() - 1000);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                txt_DatePicker.setText(sdf.format(myCalendar.getTime()));            }

        };

        txt_DatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), mDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
*/

        //OLD CODE
        /*txt_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month  = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Holo_ButtonBar_AlertDialog,mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));

                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txt_Date.setText(month + "/" + dayOfMonth + "/" + year);
            }
        };*/

        //END OLD CODE


        // myRecyclerTodo = (RecyclerView) view.findViewById(R.id.recyler_Todo);
        // final TodoTabAdapter todoTabAdapter = new TodoTabAdapter(getContext(), lstObjTasks);
        //  myRecyclerTodo.setLayoutManager(new LinearLayoutManager(getActivity()));
        //  myRecyclerTodo.setAdapter(todoTabAdapter);
/*
        prog_Loading = (ProgressBar) view.findViewById(R.id.progLoading);
        prog_Loading.setVisibility(View.GONE);
        txt_NewTask = (EditText) view.findViewById(R.id.txtNewTask);
        txt_DatePicker = (EditText) view.findViewById(R.id.txtDatePicker);
        img_CreateNew = (ImageView) view.findViewById(R.id.imgCreateNew);*/

     /*   myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lstObjTasks.clear();
                ArrayList<TasksUO> TodoTaskList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    TasksUO obj = dataSnapshot1.getValue(TasksUO.class);
                    if(dataSnapshot1.getValue(TasksUO.class).getStatus().toString().toUpperCase().equals("PENDING")){
                        TodoTaskList.add(obj);
                    }
                }

                lstObjTasks.addAll(TodoTaskList);
                todoTabAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


       /* img_CreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidateControls()){
                    prog_Loading.setVisibility(View.VISIBLE);
                    key = key = myRef.push().getKey();

*//*
                    objTaskUO.setTaskID(key);
                    objTaskUO.setTaskName(txt_NewTask.getText().toString().trim());
                    objTaskUO.setDeadline(txt_DatePicker.getText().toString());
                    objTaskUO.setStatus("Pending");

                    myRef.child(key).setValue(objTaskUO);
                    Toast.makeText(view.getContext(),
                            "Task added successfully", Toast.LENGTH_SHORT).show();
                    //  mAdapter.notifyDataSetChanged();
                    txt_NewTask.setText("");
                    txt_DatePicker.setText("");
                    prog_Loading.setVisibility(View.INVISIBLE);*//*
                }
            }
        });*/

        return view;
    }

    private String AddTripToFirebase() {
        if(ValidateControls()){
            prog_Loading.setVisibility(View.VISIBLE);
            key  = myRef.push().getKey();

            objTripUO = new TripUO();
            objTripUO.setTripID(key);
            objTripUO.setTripName(txt_TripName.getText().toString().trim());
            objTripUO.setDestCity(txt_City.getText().toString());
            objTripUO.setTripCount(0);
            objTripUO.setUserName(mAuth.getCurrentUser().getDisplayName());
            objTripUO.setUserID(mAuth.getCurrentUser().getUid());

            myRef.child(key).setValue(objTripUO);
            Toast.makeText(view.getContext(),
                    "Trip added successfully", Toast.LENGTH_SHORT).show();
            //  mAdapter.notifyDataSetChanged();
            txt_TripName.setText("");
            txt_City.setText("");
            prog_Loading.setVisibility(View.INVISIBLE);
        }
        return objTripUO.tripID.toString().trim();
    }

    public Boolean ValidateControls() {
        if (txt_TripName.getText().toString().trim() == null || txt_TripName.getText().toString().trim().equals("")) {
            txt_TripName.setError("Enter trip name");
            return false;
        } else if (txt_City.getText().toString().trim() == null || txt_City.getText().toString().trim().equals("")) {
            txt_City.setError("Enter city");
            return false;
        }
        return true;
    }
}
