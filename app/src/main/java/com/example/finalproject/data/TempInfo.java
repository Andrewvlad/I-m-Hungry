package com.example.finalproject.data;


public class TempInfo {

    private static int ratingPreference = 3;

    private static String mapHeader;

    private static double currentLongitude;

    private static double currentLatitude;

    private static int radius = 20 * 1609;

    private static float zoomLevel = 12.0f;

    public static void setRatingPreference(int newRating){
        ratingPreference = newRating;
    }

    public static int getRatingPreference(){
        return ratingPreference;
    }

    public static void setMapHeader(String newHeader){
        mapHeader = newHeader;
    }

    public static String getMapHeader(){
        return mapHeader;
    }

    public static void setCurrentLongitude(double newLongitude){
        currentLongitude = newLongitude;
    }

    public static double getCurrentLongitude(){
        return currentLongitude;
    }

    public static void setCurrentLatitude(double newLatitude){
        currentLatitude = newLatitude;
    }

    public static double getCurrentLatitude(){
        return currentLatitude;
    }

    public static void setRadius(int newRadius){
        radius = newRadius;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setZoomLevel(float newZoomLevel){
        zoomLevel = newZoomLevel;
    }

    public static float getZoomLevel() {
        return zoomLevel;
    }
}

