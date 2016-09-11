package com.example.android.quakereport;

/**
 * Created by hanssi on 9/3/16.
 */

/**
 * An Earthquake objects containing information about a single earthquake
 */
public class Earthquake {
    /* Magnitude of the earthquake */
    private String mMagnitude;

    /* Location of the earthquake */
    private String mLocation;

    /* Time of the earthquake */
    private long mTimeInMilliseconds;

    /**
     * Create a new Earthquake object
     * @param magnitude is the magnitude of the earthquake
     * @param location is the nearest city where the earthquake happened
     * @param timeInMilliseconds is the time in milliseconds when the earthquake happened
     */
    public Earthquake(String magnitude, String location, long timeInMilliseconds) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    /**
     * Returns the magnitude of the earthquake
     */
    public String getMagnitude() {
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

}
