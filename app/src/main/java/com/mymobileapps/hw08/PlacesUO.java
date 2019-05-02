/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - PlacesUO.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */
package com.mymobileapps.hw08;

import com.google.android.gms.maps.model.LatLng;

public class PlacesUO {

    public String restaurantName;
    public String dayPlanned;
    public int tripCount;
    public String tripID;
    public String userName;
    public String userID;
    public String placeID;
    /*public LatLng cityLatLan;
    public LatLng restaurantLatLan;*/
    public String currCityLat;
    public String currCityLong;
    public String currRestaurantLat;
    public String currRestaurantLon;
    public String sRestaurantLatLon;

    public PlacesUO() {
    }

    public PlacesUO(String restaurantName, String dayPlanned, int tripCount, String tripID, String userName, String userID, String placeID, /*LatLng cityLatLan, LatLng restaurantLatLan,*/ String currCityLat, String currCityLong, String currRestaurantLat, String currRestaurantLon, String sRestaurantLatLon) {
        this.restaurantName = restaurantName;
        this.dayPlanned = dayPlanned;
        this.tripCount = tripCount;
        this.tripID = tripID;
        this.userName = userName;
        this.userID = userID;
        this.placeID = placeID;
       /* this.cityLatLan = cityLatLan;
        this.restaurantLatLan = restaurantLatLan;*/
        this.currCityLat = currCityLat;
        this.currCityLong = currCityLong;
        this.currRestaurantLat = currRestaurantLat;
        this.currRestaurantLon = currRestaurantLon;
        this.sRestaurantLatLon = sRestaurantLatLon;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDayPlanned() {
        return dayPlanned;
    }

    public void setDayPlanned(String dayPlanned) {
        this.dayPlanned = dayPlanned;
    }

    public int getTripCount() {
        return tripCount;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

   /* public LatLng getCityLatLan() {
        return cityLatLan;
    }

    public void setCityLatLan(LatLng cityLatLan) {
        this.cityLatLan = cityLatLan;
    }

    public LatLng getRestaurantLatLan() {
        return restaurantLatLan;
    }

    public void setRestaurantLatLan(LatLng restaurantLatLan) {
        this.restaurantLatLan = restaurantLatLan;
    }*/

    public String getCurrCityLat() {
        return currCityLat;
    }

    public void setCurrCityLat(String currCityLat) {
        this.currCityLat = currCityLat;
    }

    public String getCurrCityLong() {
        return currCityLong;
    }

    public void setCurrCityLong(String currCityLong) {
        this.currCityLong = currCityLong;
    }

    public String getCurrRestaurantLat() {
        return currRestaurantLat;
    }

    public void setCurrRestaurantLat(String currRestaurantLat) {
        this.currRestaurantLat = currRestaurantLat;
    }

    public String getCurrRestaurantLon() {
        return currRestaurantLon;
    }

    public void setCurrRestaurantLon(String currRestaurantLon) {
        this.currRestaurantLon = currRestaurantLon;
    }

    public String getsRestaurantLatLon() {
        return sRestaurantLatLon;
    }

    public void setsRestaurantLatLon(String sRestaurantLatLon) {
        this.sRestaurantLatLon = sRestaurantLatLon;
    }

    @Override
    public String toString() {
        return "PlacesUO{" +
                "restaurantName='" + restaurantName + '\'' +
                ", dayPlanned='" + dayPlanned + '\'' +
                ", tripCount=" + tripCount +
                ", tripID='" + tripID + '\'' +
                ", userName='" + userName + '\'' +
                ", userID='" + userID + '\'' +
                ", placeID='" + placeID + '\'' +
                /*", cityLatLan=" + cityLatLan +
                ", restaurantLatLan=" + restaurantLatLan +*/
                ", currCityLat='" + currCityLat + '\'' +
                ", currCityLong='" + currCityLong + '\'' +
                ", currRestaurantLat='" + currRestaurantLat + '\'' +
                ", currRestaurantLon='" + currRestaurantLon + '\'' +
                ", sRestaurantLatLon='" + sRestaurantLatLon + '\'' +
                '}';
    }
}
