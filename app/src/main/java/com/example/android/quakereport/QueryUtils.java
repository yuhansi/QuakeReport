package com.example.android.quakereport;

import android.util.Log;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanssi on 9/10/16.
 */

/**
 * Helper function used to request and receive earthquake information from USGS.
 */
public final class QueryUtils {

    /* Tag for the log message */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private empty constructor to hold static variables and methods, which
     * can be accessed from the class name QueryUtils
     */
    private QueryUtils() {

    }

    /**
     * Query the USGS database and return a list of Earthquake objects
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch(IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from JSON response and create a list of Earthquake objects
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);
        // Return the list of Earthquake objects
        return earthquakes;
    }

    /**
     * Returns the new URL object from the given string URL
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        }
        catch(MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make a HTTP request for the given URL and return a string as the response
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early
        if(url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request is successful (response code 200),
            // then read the input stream and parse the response
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch(IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }
        finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            // Closing the input stream here will throw an IOException, this is why
            // the makeHttpRequest method signature specifies that an IOException could
            // be thrown
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the InputStream into a string that contains the JSON response from the server
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of Earthquake objects that has been built up from parsing the given JSON response.
     */
    private static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
        // If the JSON string is empty or null, then return early
        if(TextUtils.isEmpty(earthquakeJSON))
            return null;

        // Create an empty ArrayList to add earthquakes
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the JSON response string.
        // If there is an issue of the formatting of the JSON, a JSONException
        // exception object wil be thrown
        // Catch the exception and print the error message to the logs
        try {
            // Create a JSONObject for the JSON response string
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            // Extract the JSONArray associated with the key "features", which
            // represents a list of earthquakes
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            // For each earthquake in the earthquakeArray, create an Earthquake object
            for(int i = 0; i < earthquakeArray.length(); i++) {
                // Get the earthquake at position i within the list of earthquakes
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                // For a given earthquake, extract the JSONObject associated with the key
                // "properties", which represents a list of all properties for that earthquake
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                // Extract the value associated with the key "mag"
                double magnitude = properties.getDouble("mag");

                // Extract the value associated with the key "place"
                String location = properties.getString("place");

                // Extract the value associated with the key "time"
                Long time = properties.getLong("time");

                // Extract the value associated with the key "url"
                String url = properties.getString("url");

                // Create a new Earthquake object with the magnitude, location, time and url from
                // the JSON response
                Earthquake earthquake = new Earthquake(magnitude, location, time, url);

                // Add the newly created Earthquake object to the list of earthquakes
                earthquakes.add(earthquake);
            }

        }
        catch(JSONException e) {
            // If any error id thrown when executing the above within the "try" code block,
            // catch the exception and print the error message to the logs
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
}
