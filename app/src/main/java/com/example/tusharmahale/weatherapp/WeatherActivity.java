package com.example.tusharmahale.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import android.location.Geocoder;


public class WeatherActivity extends AppCompatActivity {
static EditText zipEdit;
static ProgressDialog pd;
Button getButton;
    String message;
   static LinearLayout ll;
static DownloadTask downloadTask;
    String zipCode="";
double lat,lang;
int flag=0;
static TextView temperatureTextView,visibilityTextView,timezoneTextView,summaryTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        zipEdit = findViewById(R.id.editText);
        getButton = findViewById(R.id.button);
        temperatureTextView = findViewById(R.id.temperature);
        downloadTask = new DownloadTask();
        ll = findViewById(R.id.mainlayout);

        visibilityTextView= findViewById(R.id.visibility);
        timezoneTextView = findViewById(R.id.timezone);
        summaryTextView = findViewById(R.id.summary);
        pd = new ProgressDialog(this);
pd.setCancelable(false);
getButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        downloadTask = new DownloadTask();


        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(zipEdit.getWindowToken(), 0);
        if(!isOnline()){
            Toast.makeText(getApplicationContext(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
        }
        else{
            if(zipEdit.getText().toString().trim().length()<3)
            {
                Toast.makeText(getApplicationContext(),"Enter valid zip code",Toast.LENGTH_SHORT).show();
            }
            else {
                final Geocoder geocoder = new Geocoder(getApplicationContext());
                {
                    zipCode = zipEdit.getText().toString();
                    try {

                        List<Address> addresses = geocoder.getFromLocationName(zipCode, 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            lat=address.getLatitude();
                            lang=address.getLongitude();
                            message = String.format("Latitude: %f, Longitude: %f",
                                    address.getLatitude(), address.getLongitude());

                            pd.setMessage("Loading please wait");
                            pd.show();
                            downloadTask.execute("https://api.darksky.net/forecast/5e4edd4f2a73c0da35c2d213607d97d5/"+String.valueOf(lat)+","+String.valueOf(lang));
                        } else {
                            temperatureTextView.setText("");
                            visibilityTextView.setText("");
                            timezoneTextView.setText("");
                            summaryTextView.setText("");
                            Toast.makeText(getApplicationContext(), "Unable to locate zipcode", Toast.LENGTH_LONG).show();

                        }
                    } catch (IOException e) {
                    }
                    finally {
                        //Please place your API key in the below line// you can generate it from darksky
                        ;
                    }
                }}

        }
    }
    }
);}






    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
