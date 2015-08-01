package com.example.rosen.weatherforecast.service;

import com.example.rosen.weatherforecast.data.Weather;

import java.util.ArrayList;

/**
 * Created by Rosen on 31.7.2015 ã..
 */
public interface WeatherServiceCallback {
    void serviceSuccess(ArrayList<Weather> days);
}
