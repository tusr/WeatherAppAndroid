package com.example.tusharmahale.weatherapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent weatherIntent = new Intent(MainActivity.this,WeatherActivity.class);
               startActivity(weatherIntent);
                finish();
           }
       },2500);

}}
