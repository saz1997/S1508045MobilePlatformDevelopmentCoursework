package com.example.sarathomsonS1508045;

//
// Name                 __Sara Thomson_______
// Student ID           __S1508045___________
// Programme of Study   __Computing BSc(Hons)
//

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class EarthquakeAdapter extends BaseAdapter {

    private Context eContext;
    private LinkedList<Earthquake> eAllQuakes;


    //Constructor creation

    public EarthquakeAdapter(Context eContext, LinkedList<Earthquake> eAllQuakes) {
        this.eContext = eContext;
        this.eAllQuakes = eAllQuakes;
    }

    //display all earthquakes
    @Override
    public int getCount() {
        return eAllQuakes.size();

    }

    @Override
    public Object getItem(int position) {
        return eAllQuakes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //getting the text view data for first screen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(eContext, R.layout.item_view, null);
        TextView tvMagnitude = (TextView) view.findViewById(R.id.tvMagnitude);
        //TextView tvDepth = (TextView) view.findViewById(R.id.tvDepth);
        //TextView tvLatLong = (TextView) view.findViewById(R.id.tvLatLong);
        TextView tvLocation = (TextView) view.findViewById(R.id.tvLocation);
        TextView tvODate = (TextView) view.findViewById(R.id.tvODate);
        TextView extreme = (TextView) view.findViewById(R.id.Extreme);

        //setting the text view for first screen
        tvODate.setText("Date/Time: " + eAllQuakes.get(position).getPubDate());
        tvLocation.setText("Location: " + eAllQuakes.get(position).getLocation());
        //tvLatLong.setText(eAllQuakes.get(position).getLatlong());
        //tvDepth.setText(eAllQuakes.get(position).getDepth());
        tvMagnitude.setText("Magnitude: " + eAllQuakes.get(position).getMagnitude());
        extreme.setText("Extreme: " + eAllQuakes.get(position).getCategory());

        return view;
    }



}


