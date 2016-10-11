package com.example.navanee.weatherupdate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by navanee on 07-10-2016.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {
    Context mContext;
    List<Weather> weatherList;
    int mResource;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");

    public WeatherAdapter(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);
        mContext = context;
        weatherList = objects;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new Holder();
            holder.imgView = (ImageView) convertView.findViewById(R.id.forecastImage);
            holder.timeView = (TextView) convertView.findViewById(R.id.timeText);
            holder.cloudView = (TextView) convertView.findViewById(R.id.cloudText);
            holder.temperatureView = (TextView) convertView.findViewById(R.id.temperatureText);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Weather curWeather = weatherList.get(position);
        Date dt = curWeather.getTimeStamp();
        holder.timeView.setText(dateFormat.format(dt));
        holder.cloudView.setText(curWeather.getClimateType());
        holder.temperatureView.setText(String.valueOf(curWeather.getTemperature()) + (char) 0x00B0 + "F");
        Picasso.with(mContext).load(curWeather.getIcon_url()).into(holder.imgView);
        return convertView;
    }
}

class Holder {
    ImageView imgView;
    TextView timeView;
    TextView cloudView;
    TextView temperatureView;
}


