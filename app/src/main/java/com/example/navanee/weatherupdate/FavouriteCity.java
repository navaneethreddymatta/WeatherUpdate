package com.example.navanee.weatherupdate;

import java.util.Date;

/**
 * Created by navanee on 08-10-2016.
 */

public class FavouriteCity {
    private String city;
    private String state;
    private int temperature;
    private Date date;

    public FavouriteCity(Date date, String city, String state, int temperature) {
        this.date = date;
        this.city = city;
        this.state = state;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
