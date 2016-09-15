package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by hanssi on 9/14/16.
 */

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the network
 * request to the given URL
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /* Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /* Query URL */
    private String mUrl;

    /**
     * Constructor to create a new EarthquakeLoader
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread
     */
    @Override
    public List<Earthquake> loadInBackground() {
        if(mUrl == null)
            return null;

        // Perform the network request, parse the JSON response and return a list of earthquakes
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;

    }
}
