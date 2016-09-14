package com.example.android.quakereport;

/**
 * Created by hanssi on 9/3/16.
 */

/**
 * An Earthquake objects containing information about a single earthquake
 */
public class Earthquake {
    /* Magnitude of the earthquake */
    private double mMagnitude;

    /* Location of the earthquake */
    private String mLocation;

    /* Time of the earthquake */
    private long mTimeInMilliseconds;

    /* URL linked to the earthquake */
    private String mUrl;

    /**
     * Create a new Earthquake object
     * @param magnitude is the magnitude of the earthquake
     * @param location is the location where the earthquake happened
     * @param timeInMilliseconds is the time in milliseconds when the earthquake happened
     * @param url is the USGS website url linked to details of the earthquake
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    /**
     * Returns the magnitude of the earthquake
     */
    public double getMagnitude() {
        return mMagnitude;
    }

    /**
     * Return the location of the earthquake
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Return the time of the earthquake
     */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    /**
     * Return the website url to find out more information about the earthquake
     */
    public String getUrl() {
        return mUrl;
    }

}
