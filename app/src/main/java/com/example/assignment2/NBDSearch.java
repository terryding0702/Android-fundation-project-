package com.example.assignment2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NBDSearch {
    //LtcmFpxfyxZfhGLyvZhycuXRR7pr1Lx4ZPabCaOE

    private static final String Application_Keys = "2f1f7b022441a7358183e3019c3cbb73";
    private static final String ID = "93daa67b";

    private static final String BASE_URL = "https://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=LtcmFpxfyxZfhGLyvZhycuXRR7pr1Lx4ZPabCaOE&nutrients=205&nutrients=204&nutrients=208&nutrients=269";
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
            url = new URL(BASE_URL);
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

    public static String foodAPISearch(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";

        try {
            url = new URL("https://api.edamam.com/api/food-database/parser?ingr=" +
                    keyword + "&app_id=" + ID + "&app_key=" + Application_Keys);
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
            JSONArray movieArray = new JSONArray(jsonObject.get("foods").toString());
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

    //custom search result format
    public static String getFoodSnippet(String result) {
        String snippet = null;
        String title = null;
        String icon = null;
        String desc = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObjectFood = (JSONObject) jsonObject.get("foods");
            JSONArray foodsArray = new JSONArray((jsonObjectFood).toString());
            icon = foodsArray.toString();
//            for (int i=0; i>foodsArray.length(); i++){
//                JSONObject obj = foodsArray.getJSONObject(i);
//                if ((JSONObject)obj.get("name"))
//            }
//            JSONArray jsonArray = jsonObject.getJSONArray("items");
//            if (jsonArray != null && jsonArray.length() > 0) {
//                snippet = jsonArray.getJSONObject(0).getString("snippet");
//            }
//modify part
//            JSONArray movieArray = new JSONArray(jsonObject.get("items").toString());
////            if (movieArray != null && movieArray.length() > 0) {
////                JSONObject obj = movieArray.getJSONObject(0);
////                JSONObject obj2 = (JSONObject) obj.get("pagemap");
////                icon = (new JSONArray(obj2.get("cse_thumbnail").toString()).getJSONObject(0).getString("src"));
////                desc = (new JSONArray(obj2.get("metatags").toString()).getJSONObject(0).getString("twitter:description"));
////                title = obj.getString("title");
////            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        //return snippet;
        return icon;
    }

    public static String getFoodsSnippet(String result) {
        String snippet = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("parsed");
            if (jsonArray!=null && jsonArray.length()>0){
                JSONObject js2 = jsonArray.getJSONObject(0);
                JSONObject js3 = js2.getJSONObject("food");
                JSONObject js4 = js3.getJSONObject("nutrients");

                String label = js3.getString("label");
                String enerc_kcal = js4.getString("ENERC_KCAL");
                String fat = js4.getString("FAT");
                String categoryLabel = js3.getString("categoryLabel");

                snippet = label + "\n Calorie amount: " + enerc_kcal + "\n food Fat is: " + fat + "\n food Label is: " + categoryLabel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

}

