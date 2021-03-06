package com.example.vijaya.earthquakeapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData2(String requestUrl) {
        // An empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        //  URL object to store the url for a given string
        URL url = null;
        // A string to store the response obtained from rest call in the form of string
        String jsonResponse = "";
        BufferedReader in;
        StringBuilder stringBuildTmp = new StringBuilder();

        try {
            //TODO: 1. Create a URL from the requestUrl string and make a GET request to it
            url = new URL(requestUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            //TODO: 2. Read from the Url Connection and store it as a string(jsonResponse)
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuildTmp.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();

            /*TODO: 3. Parse the jsonResponse string obtained in step 2 above into JSONObject to extract the values of
                    "mag","place","time","url"for every earth quake and create corresponding Earthquake objects with them
                    Add each earthquake object to the list(earthquakes) and return it.
            */
            jsonResponse = stringBuildTmp.toString();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray array = jsonObject.getJSONArray("features");

            for (int i = 0; i < array.length(); i++) {
                jsonObject = array.getJSONObject(i);
                JSONObject props = jsonObject.getJSONObject("properties");

                double magnitude        = props.isNull("mag") ? null : props.getDouble("mag");
                String quakeCoordinates = props.isNull("place") ? null : props.getString("place");
                long timeOfQuake        = props.isNull("time") ?  null : props.getLong("time");
                String quakeURL         = props.isNull("url") ? null : props.getString("url");
                Earthquake newQuake = new Earthquake(magnitude, quakeCoordinates, timeOfQuake, quakeURL);
                earthquakes.add(newQuake);
            }
            // Return the list of earthquakes

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  ", e);
        }
        // Return the list of earthquakes
        return earthquakes;
    }
}
