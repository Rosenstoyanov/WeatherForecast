package com.example.rosen.weatherforecast;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.rosen.weatherforecast.Adapters.ForecastListAdapter;
import com.example.rosen.weatherforecast.data.Weather;
import com.example.rosen.weatherforecast.service.WeatherService;
import com.example.rosen.weatherforecast.service.WeatherServiceCallback;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements WeatherServiceCallback, AdapterView.OnItemSelectedListener {
    private ListView list;
    private ArrayList<Weather> dataList;
    private ForecastListAdapter adapter;
    private Spinner dropdown;
    private WeatherService service;
    private String[] items;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = new ArrayList<>();
        adapter = new ForecastListAdapter(dataList, this);
        list = (ListView) findViewById(R.id.forecastList);
        list.setAdapter(adapter);
        service = new WeatherService(this);
//        service.refreshWeather("London");
        dropdown = (Spinner)findViewById(R.id.spinner1);
        items = new String[]{"London", "Sofia"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(spinnerAdapter);
        dropdown.setOnItemSelectedListener(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void serviceSuccess(ArrayList<Weather> days) {
        adapter.setDays(days);
        adapter.notifyDataSetChanged();
        dialog.hide();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s = items[position];
        service.refreshWeather(items[position]);
        dialog.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        service.refreshWeather("Sofia");
    }
}
