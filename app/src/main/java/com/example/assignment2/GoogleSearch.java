package com.example.assignment2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GoogleSearch {
    private static final String API_KEY = "AIzaSyC9-TltLuGgZabiUKWwTUfXI9Zz-VuaxjE";
    private static final String SEARCH_ID_cx = "011506952959858759455:utwclataqwg";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" +
                    API_KEY + "&cx=" + SEARCH_ID_cx + "&q=" + keyword + query_parameter);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }

    //custom search result format
    public static String getSnippet(String result) {
        String snippet = null;
        String title = null;
        String icon = null;
        String desc = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
//            JSONArray jsonArray = jsonObject.getJSONArray("items");
//            if (jsonArray != null && jsonArray.length() > 0) {
//                snippet = jsonArray.getJSONObject(0).getString("snippet");
//            }
//添加部分
            JSONArray movieArray = new JSONArray(jsonObject.get("items").toString());
            if (movieArray != null && movieArray.length() > 0) {
                JSONObject obj = movieArray.getJSONObject(0);
                JSONObject obj2 = (JSONObject) obj.get("pagemap");
                icon = (new JSONArray(obj2.get("cse_thumbnail").toString()).getJSONObject(0).getString("src"));
                desc = (new JSONArray(obj2.get("metatags").toString()).getJSONObject(0).getString("twitter:description"));
                title = obj.getString("title");
            }


        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        //return snippet;
        return icon;
    }

}

