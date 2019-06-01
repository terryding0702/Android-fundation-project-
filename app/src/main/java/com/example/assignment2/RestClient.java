package com.example.assignment2;

import android.util.Log;

import com.example.assignment2.Entities.Credentials;
import com.example.assignment2.Entities.Foods;
import com.example.assignment2.Entities.Users;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClient {
    private static final String BASE_URL = "http://118.138.26.16:8080/AssignmentTest/webresources/";
    //private static final String BASE_URL = "https://developer.nrel.gov/api/alt-fuel-stations/v1/nearest.json?api_key=tnosxROZbcLbHweaNrV1pN9hojP4PESqF7zH0OTH&location=Denver+CO";
    public static String dbRequest(String method, String methodPath) {
//        final String methodPath = "entities.consumption/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod(method);
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    //find username
    public static String findByUserName(String username){
        final String methodPath = "entities.credential/findByUsername/" + username;
        return dbRequest("GET", methodPath);
    }

    //find pwd
    public static String findByPwd(String pwd){
        final String methodPath = "entities.credential/findByPasswordhash/" + pwd;
        return dbRequest("GET", methodPath);
    }

    //find food by category
    public static String findByCategory(String category){
        final String methodPath = "entities.foods/findByCategory/" + category;
        return dbRequest("GET", methodPath);
    }

    //get food Name
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
//
            JSONArray movieArray = new JSONArray();
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

    // find Credential
    public static String findCredentialById(int userId){
        final String methodPath = "entities.credential/findByUserid/" + userId;
        return dbRequest("GET", methodPath);
    }

    // find UserByEmail
    public static String findUserByEmail(String email){
        final String methodPath = "entities.users/findByEmail/" + email;
        return dbRequest("GET", methodPath);
    }

    //find Report
    public static String findById(String userId){
        final String methodPath = "entities.report/findByUserid/" + userId;
        return dbRequest("GET", methodPath);
    }

    //get Calorie Detail
    public static String findCalorieByDetail(String userId, String startDate, String endData){
        final String methodPath = "entities.report/getCaloriesDetail/" + userId + "/" + startDate + "/" + endData;
        return dbRequest("GET", methodPath);
    }

    //get Report Detail
    public static String findReportByDetail(String userId, String Date){
        final String methodPath = "entities.report/getReportDetail/" + userId + "/" + Date;
        return dbRequest("GET", methodPath);
    }
    //create food
    public static void createFood(Foods food){
        final String methodPath = "entities.foods/";
        URL url = null;
        HttpURLConnection conn = null;
        try {
            Gson gson = new Gson();
            String stringCourseJson= gson.toJson(food);
            url = new URL(BASE_URL+methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    //create User
    public static void createUser(Users user){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="entities.users/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(user);
            url = new URL(BASE_URL + methodPath);
        //open the connection
            conn = (HttpURLConnection) url.openConnection();
        //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
        //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
        //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
        //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
        //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    //Create credential
    public static void createCredential(Credentials credential){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="entities.credential/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(credential);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    //edit goal
    public static void EditGoal(String userId){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="entities.report/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(userId);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to PUT
            conn.setRequestMethod("PUT");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String NNDBRequest(String method, String methodPath) {
        String URL1 = "http://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=LtcmFpxfyxZfhGLyvZhycuXRR7pr1Lx4ZPabCaOE&nutrients=205&nutrients=204&nutrients=208&nutrients=269";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod(method);
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


}

