package com.example.tusharmahale.weatherapp;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String,Void,String> {
    double temperature;
    String Summary,visibility;
    String result="";
    String timezone;
    URL url;
     int temperatureInteger;
    HttpURLConnection urlConnection = null;

    @Override
    protected String doInBackground(String... urls) {
    try {
        url = new URL(urls[0]);
        urlConnection = (HttpURLConnection)url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        int data = reader.read();
        while (data!=-1)
        {char current = (char) data;
        result +=current;
        data = reader.read();

    }
    return result;
    }

    catch (Exception e)
    {
        e.printStackTrace();;
    }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    try{
        JSONObject jsonObject = new JSONObject(result);

        JSONObject weatherData = new JSONObject(jsonObject.getString("currently"));

        temperature = Double.parseDouble(weatherData.getString("temperature"));
        Summary = String.valueOf(weatherData.getString("summary"));
        timezone = String.valueOf(jsonObject.getString("timezone"));
        visibility =String.valueOf(weatherData.getString("visibility"));
    }
    catch (Exception e)
    {}
    finally {

        double re=(temperature-32)*0.55;
        re = Math.round(re*100.0)/100.0;

        WeatherActivity.temperatureTextView.setText("Temperature: "+re +" C");
        WeatherActivity.visibilityTextView.setText(("Visibility: "+visibility));
        WeatherActivity.timezoneTextView.setText(("Timezone: "+timezone));
        WeatherActivity.summaryTextView.setText(("Summary: "+Summary));
        WeatherActivity.pd.dismiss();
    }
    }
}
