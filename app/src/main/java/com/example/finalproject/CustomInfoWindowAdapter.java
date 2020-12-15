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

    public void getDirections(Marker marker){
        final String latPosition = String.valueOf(marker.getPosition().latitude);
        final String lonPosition = String.valueOf(marker.getPosition().longitude);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Open Google Maps?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        String latitude = latPosition;
                        String longitude = lonPosition;
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");

//                        try{
//                            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
//                                startActivity(mapIntent);
//                            }
//                        }catch (NullPointerException e){
//                            Log.e("Error test", "onClick: NullPointerException: Couldn't open map." + e.getMessage() );
//                            Toast.makeText(context, "Couldn't open map", Toast.LENGTH_SHORT).show();
//                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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