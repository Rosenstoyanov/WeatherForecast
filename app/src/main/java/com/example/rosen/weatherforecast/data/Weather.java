package com.example.rosen.weatherforecast.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Rosen on 1.8.2015 ã..
 */
public class Weather {
    private String cityName;
    private String dayTemperature;
    private String nightTemperature;
    private String description;
    private String time;
    private String imageURL;

    private static final String patternImgURL = "http://openweathermap.org/img/w/%s.png";

    public Weather(JSONObject jsonObject) {
        populateObject(jsonObject);
    }

    public void populateObject(JSONObject data) {
        cityName = "L";
        int timeMil = 0;
        try {
            timeMil = data.getInt("dt");
            JSONObject temp = data.optJSONObject("temp");
            dayTemperature = Double.toString(temp.optDouble("day"));
            nightTemperature = Double.toString(temp.optDouble("night"));
            JSONObject weather = (JSONObject) data.optJSONArray("weather").get(0);
            description = weather.optString("description");
            imageURL = String.format(patternImgURL, weather.optString("icon"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMil);
        time =  formatter.format(calendar.getTime());
    }

    public String getCityName() {
        return cityName;
    }

    public String getDayTemperature() {
        return dayTemperature;
    }

    public String getNightTemperature() {
        return nightTemperature;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getImageURL() {
        return imageURL;
    }
}
