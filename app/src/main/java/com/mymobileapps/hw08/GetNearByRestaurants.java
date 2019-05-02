/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - GetNearByRestaurants.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */
package com.mymobileapps.hw08;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class GetNearByRestaurants extends AsyncTask<String, String, String> {

    private String googleplaceData, url;
    private GoogleMap mMap;
    PlacesListIData iData;

    public GetNearByRestaurants(PlacesListIData iData) {
        this.iData = iData;
    }
 /*  public GetNearByRestaurants() {

    }
*/
    @Override
    protected String doInBackground(String... objects)
    {
       // mMap = (GoogleMap) objects[0];
        url = (String) objects[0];


        try
        {
            googleplaceData = ReadTheURL(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return googleplaceData;
    }


    @Override
    protected void onPostExecute(String s)
    {
        try {
            List<HashMap<String, String>> nearByPlacesList = null;
            DataParser dataParser = new DataParser();
           nearByPlacesList = dataParser.parse(s);
            iData.placesListPostExecute(nearByPlacesList);
        }
        catch (Exception ex){
            Log.d("demo", "onPostExecute: " + ex.getMessage());
        }
      //  DisplayNearbyPlaces(nearByPlacesList);
    }


    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearByPlacesList)
    {
        for (int i=0; i<nearByPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> googleNearbyPlace = nearByPlacesList.get(i);
            String nameOfPlace = googleNearbyPlace.get("place_name");
            String vicinity = googleNearbyPlace.get("vicinity");
            double lat = Double.parseDouble(googleNearbyPlace.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlace.get("lng"));


            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);

            markerOptions.title(nameOfPlace + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

    public String ReadTheURL(String placeURL) throws IOException
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
    }



   public static  interface PlacesListIData{
        public void placesListPostExecute( List<HashMap<String, String>> PlaceListing);
    }


}
