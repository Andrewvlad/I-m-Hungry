package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.data.LocationInfo;
import com.example.finalproject.data.TempInfo;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LandingActivity extends AppCompatActivity {

    private static RequestQueue queue;
    String newString = "hey";
    LocationTrack locationTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ImageButton nearButton = (ImageButton)findViewById(R.id.near_me_button);
        nearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    fakePress(v);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new CountDownTimer(3000, 1000) {

                    @Override
                    public void onTick(long l) {
                        Snackbar.make(v,  "Loading", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(v.getContext(), MapsActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }.start();
            }
        });

        ImageButton onWayButton = (ImageButton)findViewById(R.id.on_my_way_button);
        onWayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    fakePress(v);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new CountDownTimer(2000, 2000) {

                    @Override
                    public void onTick(long l) {
                        Snackbar.make(v,  "Loading", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(v.getContext(), MapsActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }.start();
            }
        });

        ImageButton coffeeButton = (ImageButton)findViewById(R.id.coffee_button);
        coffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    fakePress(v);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new CountDownTimer(3000, 1000) {

                    @Override
                    public void onTick(long l) {
                        Snackbar.make(v,  "Loading", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(v.getContext(), MapsActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }.start();
            }
        });

        ImageButton snackButton = (ImageButton)findViewById(R.id.snacks_button);
        snackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    fakePress(v);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new CountDownTimer(3000, 1000) {

                    @Override
                    public void onTick(long l) {
                        Snackbar.make(v,  "Loading", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(v.getContext(), MapsActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }.start();
            }
        });
        queue = Volley.newRequestQueue(this);

    }

    public void fakePress(View v) throws JSONException {

//        String queryURL = "https://api.yelp.com/v3/businesses/search?location=New York?radius=40000";

        final List<String> setNameValue = new ArrayList<>();
        final List<Integer> setDistanceValue = new ArrayList<>();
        final List<List> setTransactionsValue = new ArrayList<>();
        final List<String> setImage_urlValue = new ArrayList<>();
        final List<String> setUrlValue = new ArrayList<>();
        final List<Integer> setReview_countValue = new ArrayList<>();
        final List<List> setCategoriesValue = new ArrayList<>();
        final List<Integer> setRatingValue = new ArrayList<>();
        final List<String> setPriceValue = new ArrayList<>();
        final List<Double> setLatitudeValue = new ArrayList<>();
        final List<Double> setLongitudeValue = new ArrayList<>();
        final List<String> setPhoneValue = new ArrayList<>();

        locationTrack = new LocationTrack(LandingActivity.this);

        String myUrl =
                "https://api.yelp.com/v3/businesses/search" +
                "?term=" + TempInfo.getSearch() +
                "&latitude=" + locationTrack.getLatitude() +
                "&longitude=" + locationTrack.getLongitude() +
                "&radius=" + TempInfo.getRadius() +
                "&limit=50" +
                "&open_now=True";
        Log.e("url", myUrl);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, myUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("businesses");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        setNameValue.add(jsonArray.getJSONObject(i).getString("name"));
                        Log.e("Test Error:", setNameValue.get(i));
                        setDistanceValue.add(jsonArray.getJSONObject(i).getInt("distance"));
                        List<String> tempList1 = new ArrayList<>();
                        if (jsonArray.getJSONObject(i).has("transactions")) {
                            for (int j = 0; j < jsonArray.getJSONObject(i).getJSONArray("transactions").length(); j++) {
                                tempList1.add((String) jsonArray.getJSONObject(i).getJSONArray("transactions").get(j));
                            }
                        }
                        else

                            tempList1.add("???");
                        setTransactionsValue.add(tempList1);
                        setImage_urlValue.add(jsonArray.getJSONObject(i).getString("image_url"));
                        setUrlValue.add(jsonArray.getJSONObject(i).getString("url"));
                        setReview_countValue.add(jsonArray.getJSONObject(i).getInt("review_count"));
                        List<String> tempList2 = new ArrayList<>();
                        for (int j = 0; j < jsonArray.getJSONObject(i).getJSONArray("categories").length(); j++) {
                            tempList2.add(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("title"));
                        }
                        setCategoriesValue.add(tempList2);
                        setRatingValue.add(jsonArray.getJSONObject(i).getInt("rating"));
                        if (jsonArray.getJSONObject(i).has("price"))
                            setPriceValue.add(jsonArray.getJSONObject(i).getString("price"));
                        else
                            setPriceValue.add("???");

                        setLatitudeValue.add(jsonArray.getJSONObject(i).getJSONObject("coordinates").getDouble("latitude"));
                        setLongitudeValue.add(jsonArray.getJSONObject(i).getJSONObject("coordinates").getDouble("longitude"));
                        setPhoneValue.add(jsonArray.getJSONObject(i).getString("display_phone"));
                    }
                    LocationInfo.setName(setNameValue);
                    LocationInfo.setDistance(setDistanceValue);
                    LocationInfo.setTransactions(setTransactionsValue);
                    LocationInfo.setImage_url(setImage_urlValue);
                    LocationInfo.setUrl(setUrlValue);
                    LocationInfo.setReview_count(setReview_countValue);
                    LocationInfo.setCategories(setCategoriesValue);
                    LocationInfo.setRating(setRatingValue);
                    LocationInfo.setPrice(setPriceValue);
                    LocationInfo.setLatitude(setLatitudeValue);
                    LocationInfo.setLongitude(setLongitudeValue);
                    LocationInfo.setPhone(setPhoneValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error: " + error.getMessage());
                Toast.makeText(LandingActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer eZfy9wBwtrWVde_zz1_DPD2j389Lyob4I7rQmOp4NBjeOU-55CmvOWcRW3LKbg4c5J8A94d0i9KHbIjHWNS4p4SSPKqJlTDmu1w2jlhjnm8XtZST6hZzbVeg_MDPX3Yx");
                return headers;
            }
        };
        queue.add(jsonRequest);
        return;
    }
}
