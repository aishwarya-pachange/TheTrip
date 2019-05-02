/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - HomePage.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */
package com.mymobileapps.hw08;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private TabLayout taskTabLayout;
    private ViewPager taskViewPager;
    private ViewPagerAdapter myAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();

        setTitle("Welcome, " + mAuth.getCurrentUser().getDisplayName() + "!");
    /*    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);*/

        taskTabLayout = (TabLayout) findViewById(R.id.tripTabs);
        taskViewPager = (ViewPager) findViewById(R.id.ViewPagerTabs);
        myAdapter  = new ViewPagerAdapter(getSupportFragmentManager());

       // myAdapter.AddFragment(new AddTripTabFragment(),"Plan a Trip");
        myAdapter.AddFragment(new AddPlacesTabFragment(),"Plan a Trip");
       myAdapter.AddFragment(new ViewTripsTabFragment(),"Explore options");

        //taskTabLayout.getTabAt(0).setIcon(R.drawable.arrowtodo);

        taskViewPager.setAdapter(myAdapter);
        taskTabLayout.setupWithViewPager(taskViewPager);

        ActionBar actionBar  = getSupportActionBar();
        actionBar.setElevation(0);
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
*/                mAuth.signOut();
                finish();
                Intent myIntent = new Intent(HomePage.this, MainActivity.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
