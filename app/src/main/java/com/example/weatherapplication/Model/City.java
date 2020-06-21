package com.example.weatherapplication.Model;

public class City {
    private String id;
    private String city;
    private String country;
    private Coord coord;

    public City(String id, String city, String country){
        this.id = id;
        this.city = city;
        this.country = country;

    }

    public String getId(){
        return id;
    }
    public String getCity(){
        return  city;
    }
    public String getCountry(){
        return country;
    }


}
class Coord{
    String lat;
    String lon;
}
