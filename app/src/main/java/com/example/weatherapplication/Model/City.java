package com.example.weatherapplication.Model;

import androidx.annotation.NonNull;

public class City {
    private String id;
    private String name;
    private String country;
    private String state;
    private Coord coord;

    public City(String id, String name, String state,String country,Coord coord){
        this.id = id;
        this.name = name;
        this.country = country;
        this.state = state;
        this.coord = coord;

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
        return coord.getLat();
    }
    public String getLon(){
        return coord.getLon();
    }

    @NonNull
    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", coord=" + coord.getLat() +"," +coord.getLon()+
                '}';
    }
}
class Coord{
    String lat;
    String lon;

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
