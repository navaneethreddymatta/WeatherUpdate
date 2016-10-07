package com.example.navanee.weatherupdate;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by navanee on 07-10-2016.
 */

public class GetData extends AsyncTask<String,Void,ArrayList<Weather>> {
    DataRetreiver activity;
    ArrayList<Weather> weatherList = new ArrayList<Weather>();
    public GetData(DataRetreiver activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Weather> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = HttpURLConnection.HTTP_OK;
            if(con.getResponseCode() == statusCode) {

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        activity.setData(weathers);
    }

    interface DataRetreiver{
        void setData(ArrayList<Weather> weatherList);
    }
}
