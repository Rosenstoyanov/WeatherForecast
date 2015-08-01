package com.example.rosen.weatherforecast.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rosen.weatherforecast.R;
import com.example.rosen.weatherforecast.data.Weather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rosen on 1.8.2015 ã..
 */
public class ForecastListAdapter extends BaseAdapter {
    private ArrayList<Weather> days;
    private Context context;

    public ForecastListAdapter(ArrayList<Weather> days, Context context) {
        this.days = days;
        this.context = context;
    }

    public void setDays(ArrayList<Weather> days) {
        this.days = days;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Weather getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Picasso.with(context).load(getItem(position).getImageURL()).resize(200, 200).into(holder.image);
        holder.dayTemp.setText(getItem(position).getDayTemperature());
        holder.nightTemp.setText(getItem(position).getNightTemperature());
        holder.description.setText(getItem(position).getDescription());
        holder.date.setText(getItem(position).getTime());

        return convertView;
    }

    private static class Holder{
        TextView dayTemp;
        TextView nightTemp;
        ImageView image;
        TextView description;
        TextView date;

        public Holder(View v){
            dayTemp = (TextView) v.findViewById(R.id.dayDegrees);
            nightTemp = (TextView) v.findViewById(R.id.nightDegrees);
            image = (ImageView) v.findViewById(R.id.imageView);
            description = (TextView) v.findViewById(R.id.description);
            date = (TextView) v.findViewById(R.id.tvData);
        }

    }
}
