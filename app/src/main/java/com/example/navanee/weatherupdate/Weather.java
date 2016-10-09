package com.example.navanee.weatherupdate;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by navanee on 07-10-2016.
 */

public class Weather implements Serializable {
    private Date timeStamp;
    private int temperature;
    private int dewPoint;
    private String clouds;
    private String icon_url;
    private int windSpeed;
    private String windDir;
    private String climateType;
    private int humidity;
    private int feelsLike;
    private int pressure;
    private String city;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String state;

    public Weather(Date timeStamp, int temperature, int dewPoint, String clouds, String icon_url, int windSpeed, String windDir, String climateType, int humidity, int feelsLike, int pressure, String city, String state) {
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.dewPoint = dewPoint;
        this.clouds = clouds;
        this.icon_url = icon_url;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
        this.climateType = climateType;
        this.humidity = humidity;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.city = city;
        this.state = state;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(int dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getClimateType() {
        return climateType;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(int feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
}
