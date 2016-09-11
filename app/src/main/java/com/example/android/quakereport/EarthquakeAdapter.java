package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hanssi on 9/3/16.
 */

/**
 * An EarthquakeAdapter will create a list item layout for each earthquake
 * in the data source, which is a list of Earthquake objects
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * The part of the location string used to determine whether or not there
     * is a location offset present
     */
    private static final String LOCATION_SEPARATOR = " of ";

    /**
     * Creates a new EarthquakeAdapter
     * @param context is the context of the app
     * @param earthquakes is the list of earthquakes
     */
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check is there is an existing list item view, otherwise inflate a new list item layout
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        Earthquake currentEarthquake = getItem(position);

        //Find the TextView with view ID magnitude
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        // Display the magnitude of the current earthquake in that TextView
        magnitudeView.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the corresponding background color based on the magnitude of the current earthquake
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Get the original location string from the Earthquake object,
        // which will be used to determine whether or not a location offset is presented
        String originalLocation = currentEarthquake.getLocation();

        // If the original location string contains both a primary location and a
        // location offset, then store the primary location separately from the
        // location offset in two strings, so they can be displayed in two TextViews
        String primaryLocation;
        String locationOffset;

        // Check if the originalLocation string contains the LOCATION_SEPARATOR " of "
        if(originalLocation.contains(LOCATION_SEPARATOR)) {
            // Split the string into different parts (an array of strings)
            // based on the LOCATION_SEPARATOR " of "
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }
        else {
            // If there is no LOCATION_SEPARATOR contained in the original location string,
            // set the location offset as "Near the"
            locationOffset = getContext().getString(R.string.near_the);
            // The primary location will be the full original location string
            primaryLocation = originalLocation;
        }

        // Find the TextView with view ID location
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        // Display the location of the current earthquake in that TextView
        primaryLocationView.setText(primaryLocation);

        // Find the TextView with view ID location offset
        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        // Display the location offset of the current earthquake in that TextView
        locationOffsetView.setText(locationOffset);

        // Create a new Date object from the time in milliseconds of when the earthquake happened
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Format the date string
        String formattedDate = formatDate(dateObject);

        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string
        String formattedTime = formatTime(dateObject);
        // Displace the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        // Return the list item view that is currently showing appropriate data
        return listItemView;
    }

    /**
     * Return the color for the magnitude circle based on the magnitude of the earthquake
     * @param magnitude intensity of the earthquake
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        // Get the floor function of the current earthquake's magnitude
        int magnitudeFloor = (int) Math.floor(magnitude);
        // Set color ID for magnitudeColorResourceId based on the floor function of the magnitude
        switch(magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    /**
     * Return the formatted date string from a Date object
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted time string from a Date object
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}