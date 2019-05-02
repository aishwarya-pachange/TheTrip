/*
 *************** ASSIGNMENT# - HW 08 ***************
 *************** FILE NAME - TripUO.java ***************
 *************** FULL NAME - Aishwarya Nandkumar Pachange & Janani Krishnan (Group 18) ***************
 */
package com.mymobileapps.hw08;

import com.google.android.gms.maps.model.LatLng;

public class TripUO {

    public String tripName;
    public String destCity;
    public String restaurantName;
    public String dayPlanned;
    public int tripCount;
    public String tripID;
    public String userName;
    public String userID;
    public String id;
    public String placeID;
    /*public LatLng cityLatLan;
    public LatLng restaurantLatLan;*/
    public String currCityLat;
    public String currCityLong;
    public String currRestaurantLat;
    public String currRestaurantLon;
    public String sRestLatLng;


    //public String restaurantName;
    //public String dayPlanned;
    //public int tripCount;
    //public String tripID;
    //public String userName;
    //public String userID;
    //public String placeID;

    public TripUO() {
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

  /*  public LatLng getCityLatLan() {
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

    public String getsRestLatLng() {
        return sRestLatLng;
    }

    public void setsRestLatLng(String sRestLatLng) {
        this.sRestLatLng = sRestLatLng;
    }

    @Override
    public String toString() {
        return "TripUO{" +
                "tripName='" + tripName + '\'' +
                ", destCity='" + destCity + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", dayPlanned='" + dayPlanned + '\'' +
                ", tripCount=" + tripCount +
                ", tripID='" + tripID + '\'' +
                ", userName='" + userName + '\'' +
                ", userID='" + userID + '\'' +
                ", id='" + id + '\'' +
                ", placeID='" + placeID + '\'' +
               /* ", cityLatLan=" + cityLatLan +
                ", restaurantLatLan=" + restaurantLatLan +*/
                ", currCityLat='" + currCityLat + '\'' +
                ", currCityLong='" + currCityLong + '\'' +
                ", currRestaurantLat='" + currRestaurantLat + '\'' +
                ", currRestaurantLon='" + currRestaurantLon + '\'' +
                ", sRestLatLng='" + sRestLatLng + '\'' +
                '}';
    }
}

