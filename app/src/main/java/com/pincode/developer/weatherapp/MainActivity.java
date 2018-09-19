package com.pincode.developer.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    private String cityName = "London";
    private String countryName = "UK";
    private  String temperatureUnit = "metric";

    // Only for test
    private String apiKey = "b6907d289e10d714a6e88b30761fae22";

    private String url = "https://samples.openweathermap.org/data/2.5/weather?q=" +
            cityName + "," + countryName + "&appid=" + apiKey;

    // For production, use your own API key
    // String url = "https://api.openweathermap.org/data/2.5/weather?q=" +
    //        cityName + "," + countryName + "&appid=" + apiKey + "&units=" + temperatureUnit;


    @BindView(R.id.temperatureTextView)
    TextView temperatureTextView;

    @BindView(R.id.weatherDescriptionTextView)
    TextView weatherDescriptionTextView;

    @BindView(R.id.cityTextView)
    TextView cityTextView;

    @BindView(R.id.weatherIconImageView)
    ImageView weatherIconImageView;

    @BindView(R.id.dateTextView)
    TextView dateTextView;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        runWeatherTimer();
    }

    private void runWeatherTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateWeather();
                    }
                });
            }
        }, 0, 60 * 1000); // run in one minute
    }

    private void updateWeather() {

        displayCurrentDate();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseObject) {

                        try {

                            // Get value of the main section
                            JSONObject mainJSONObject = responseObject.getJSONObject("main");
                            String temp = String.valueOf(Math.round(mainJSONObject.getDouble("temp")));
                            temperatureTextView.setText(temp);

                            // Get value of the weather section
                            JSONArray weatherArray = responseObject.getJSONArray("weather");
                            JSONObject firstWeatherObject = weatherArray.getJSONObject(0);

                            String weatherDescription = firstWeatherObject.getString("description");
                            weatherDescriptionTextView.setText(weatherDescription);

                            // Get name of the city
                            String city = responseObject.getString("name");
                            cityTextView.setText(city);

                            int iconResourceId = getResources().getIdentifier("icon_" +
                                            weatherDescription.replace(" ", "_"),
                                    "drawable", getPackageName());
                            weatherIconImageView.setImageResource(iconResourceId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void displayCurrentDate() {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat mdformat = new SimpleDateFormat("EEE, MMM, d"); //Thu, Aug, 30
////        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd "); //2018/08/30
//        String strDate = mdformat.format(calendar.getTime());
//        setCurrentDate(strDate);

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getInstance();
        String strDate = dateFormat.format(calendar.getTime());
        setCurrentDate(strDate);
    }

    private void setCurrentDate(String currentDate) {
        // Update date
        dateTextView.setText(currentDate);
    }
}
