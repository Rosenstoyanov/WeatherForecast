package com.example.rosen.weatherforecast.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rosen.weatherforecast.data.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Rosen on 31.7.2015 ã..
 */
public class WeatherService {
    //TODO: create real service or check for
    private WeatherServiceCallback callback;

    public WeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    public void refreshWeather(String location){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String fivedays = "http://api.openweathermap.org/data/2.5/forecast/daily?q=London&units=metric&cnt=5";
                String stringURL = String.format(
                        "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&cnt=5", params[0]);

                InputStream inputStream = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(fivedays);

                    URLConnection connection = url.openConnection();
                    inputStream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    Log.d("hem",result.toString());
                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s != null){
                    try {
                        JSONObject data = new JSONObject(s);
                        JSONArray list = data.getJSONArray("list");
                        ArrayList<Weather> weathers = new ArrayList<Weather>();
                        for (int i = 0; i < list.length(); i++) {
                            weathers.add(new Weather((JSONObject) list.get(i)));
                        }
                        callback.serviceSuccess(weathers);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute(location);
    }
}
