package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.data.LocationInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public CustomInfoWindowAdapter(Activity context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        TextView tvStars = (TextView) view.findViewById(R.id.tv_stars);
        ImageView tvImage = (ImageView) view.findViewById(R.id.tv_image);

        tvTitle.setText(LocationInfo.getName().get((int) marker.getZIndex()));
        tvDistance.setText(df.format(((LocationInfo.getDistance().get((int) marker.getZIndex())) / 1609.0)) + " miles away");
        tvStars.setText(LocationInfo.getRating().get((int) marker.getZIndex()) + "☆");

        String url = LocationInfo.getImage_url().get((int) marker.getZIndex());
        if (url.equals("")){
            url = "https://vignette.wikia.nocookie.net/progressivepartyofnoobs/images/0/07/NA_icon_292x225-584x450.jpg/revision/latest?cb=20180204041337";
        }

        Picasso.get()
                .load(url)
                .resize(600, 200)
                .centerInside()
                .into(tvImage);

        return view;


    }



}