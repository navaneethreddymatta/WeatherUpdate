package com.example.navanee.weatherupdate;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by navanee on 07-10-2016.
 */

public class GetData extends AsyncTask<String,Void,ArrayList<Weather>> {
    DataRetreiver activity;
    String city, state, errorDescription = "";
    ArrayList<Weather> weatherList = new ArrayList<Weather>();
    Boolean isValidEntry = true;
    JSONObject root;

    public GetData(DataRetreiver activity, String city, String state) {
        this.activity = activity;
        this.city = city;
        this.state = state;
    }

    @Override
    protected ArrayList<Weather> doInBackground(String... params) {
        Date timeStamp;
        int temperature, dewPoint, windSpeed, humidity, feelsLike, pressure;
        String clouds, icon_url, windDir, degrees, climateType;
        Weather weatherObj;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = HttpURLConnection.HTTP_OK;
            if(con.getResponseCode() == statusCode) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = bf.readLine()) != null) {
                    sb.append(line + "\n");
                }
                root = new JSONObject(sb.toString());
                isValidEntry = true;
                JSONArray hourlyForecast = root.getJSONArray("hourly_forecast");
                for (int i = 0; i < hourlyForecast.length(); i++) {
                    JSONObject jsonObj = (JSONObject) hourlyForecast.get(i);
                    JSONObject timeObj = jsonObj.getJSONObject("FCTTIME");
                    timeStamp = dateFormat.parse(timeObj.getString("mday") + "-" + timeObj.getString("mon") + "-" + timeObj.getString("year") + " " + timeObj.getString("hour") + ":" + timeObj.getString("min") + ":" + timeObj.getString("sec"));
                    temperature = Integer.parseInt((jsonObj.getJSONObject("temp")).getString("english"));
                    dewPoint = Integer.parseInt((jsonObj.getJSONObject("dewpoint")).getString("english"));
                    clouds = jsonObj.getString("condition");
                    icon_url = jsonObj.getString("icon_url");
                    windSpeed = Integer.parseInt((jsonObj.getJSONObject("wspd")).getString("english"));
                    windDir = (jsonObj.getJSONObject("wdir")).getString("dir");
                    degrees = (jsonObj.getJSONObject("wdir")).getString("degrees");
                    climateType = jsonObj.getString("wx");
                    humidity = Integer.parseInt(jsonObj.getString("humidity"));
                    feelsLike = Integer.parseInt((jsonObj.getJSONObject("feelslike")).getString("english"));
                    pressure = Integer.parseInt((jsonObj.getJSONObject("mslp")).getString("metric"));
                    weatherObj = new Weather(timeStamp, temperature, dewPoint, climateType, icon_url, windSpeed, windDir, clouds, humidity, feelsLike, pressure, city, state, degrees);
                    weatherList.add(weatherObj);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            isValidEntry = false;
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        if(isValidEntry)
            activity.setData(weathers);
        else {
            try {
                JSONObject errorObj = root.getJSONObject("response").getJSONObject("error");
                errorDescription = errorObj.getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            activity.setError(errorDescription);
        }
    }

    interface DataRetreiver{
        void setData(ArrayList<Weather> weatherList);
        void setError(String description);
    }
}
