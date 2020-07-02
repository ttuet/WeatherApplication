package com.example.weatherapplication.Model;

public class Temp {
    String time;
    String temp;
    String icon;
    public Temp(String time, String temp, String icon){
        this.time = time;
        this.temp = temp;
        this.icon = icon;
    }
    public String getIcon() {
        return icon;
    }

    public String getTemp() {
        return temp;
    }

    public String getTime() {
        return time;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
