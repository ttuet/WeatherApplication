package com.example.weatherapplication.Model;

import androidx.annotation.NonNull;

public class City {
    private String id;
    private String name;
    private String country;
    private String lat ;
    private String lon;

    public City(String id, String name,String country,String lat, String lon){
        this.id = id;
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;

    }

    public String getId(){
        return id;
    }
    public String getCity(){
        return  name;
    }
    public String getCountry(){
        return country;
    }
    public String getLat(){
        return lat;
    }
    public String getLon(){
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }


    @NonNull
    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name=" + name +

                '}';
    }
}
