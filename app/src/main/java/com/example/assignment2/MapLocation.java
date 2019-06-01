package com.example.assignment2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MapLocation {
    private static final String BASE_KEY = "&key=AIzaSyCl5ldmkEYP5iXQIUOcNfYJoQy9l97wWco";
    private static final String Path = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private double lat;
    private double lng;

    public static String getFromLocationName(String address) {

        URL url = null;
        HttpURLConnection conn = null;
        String textResult="";
        String query_parameter="";
        try{
            url = new URL(Path+ address + BASE_KEY);
            conn=(HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while(inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static Double getLatitude(String result){
        Double langtitude = 0.0;
        try{
            JSONObject obj = new JSONObject(result);
            JSONArray tempResult = obj.getJSONArray("results");
            JSONObject tempObject = tempResult.getJSONObject(0);
            JSONObject geome = tempObject.getJSONObject("geometry");
            JSONObject loca = geome.getJSONObject("location");
            langtitude = Double.valueOf(String.format("%.3f",loca.getDouble("lat")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return langtitude;
    }

    public static Double getLngitude(String result){
        Double longitude = 0.0;
        try{
            JSONObject obj = new JSONObject(result);
            JSONArray tempResult = obj.getJSONArray("results");
            JSONObject tempObject = tempResult.getJSONObject(0);
            JSONObject geome = tempObject.getJSONObject("geometry");
            JSONObject loca = geome.getJSONObject("location");
            longitude = Double.valueOf(String.format("%.3f",loca.getDouble("lng")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return longitude;
    }



}
