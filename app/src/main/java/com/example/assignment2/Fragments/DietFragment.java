package com.example.assignment2.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.assignment2.Entities.Foods;
import com.example.assignment2.GoogleSearch;
import com.example.assignment2.MyImageView;
import com.example.assignment2.NBDSearch;
import com.example.assignment2.R;
import com.example.assignment2.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DietFragment extends Fragment {
    //define Spinner value
    String str = null;
    String fdStr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diet, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Spinner foodCategorySpinner = getActivity().findViewById(R.id.diet_category);
        List<String> list = new ArrayList<String>();
        list.add("vegetable");
        list.add("beef");
        list.add("cheese");
        list.add("chocolate");
        list.add("bread");
        list.add("fish");
        list.add("Cake");
        list.add("snack");

        final ArrayAdapter<String> foodSpinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, list);
        foodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodCategorySpinner.setAdapter(foodSpinnerAdapter);
        //add listener to get real category
        foodCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView foodShow = (TextView) getActivity().findViewById(R.id.diet_categoryShow);
                str = (String) foodCategorySpinner.getSelectedItem();
                foodShow.setText(str);
                //Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
//                FoodsAsyncTask getFoodsDetail = new FoodsAsyncTask();
//                getFoodsDetail.execute();

                //
//foodName list (测试)
//                List<String> foodName = new ArrayList<String>();
//                final Spinner foodNameSpinner = getActivity().findViewById((R.id.diet_foodName));
//                final ArrayAdapter<String> foodNameSpinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, foodName);
//                foodNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                foodNameSpinner.setAdapter(foodNameSpinnerAdapter);

                FoodNameAsyncTask ft = new FoodNameAsyncTask();
                ft.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Add food function
        Button foodBtn = (Button) getActivity().findViewById(R.id.diet_addBtn);
        final EditText foodEdit = (EditText) getActivity().findViewById(R.id.diet_foodAdd);
        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Task6 post
//                PostAsyncTask postAsyncTask = new PostAsyncTask();
//                if (!(foodEdit.getText().toString().isEmpty()))
//                    postAsyncTask.execute(foodEdit.getText().toString());

                //Google Search Result
                String searchKeyword = foodEdit.getText().toString();
                SearchAsyncTask searchAsyncTask=new SearchAsyncTask();
                searchAsyncTask.execute(searchKeyword);

                SearchFoodDetailAsyncTask sa = new SearchFoodDetailAsyncTask();
                sa.execute(searchKeyword);
            }
        });
    }

    //not used
    private class FoodsAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return RestClient.findByCategory(str);
        }

        @Override
        protected void onPostExecute(String foods) {
//            TextView foodDetail = (TextView) getActivity().findViewById(R.id.diet_foodDetail);
//            foodDetail.setText("Food Detail shows below: \n" + foods);
        }
    }

    //Post method for creating food
    private class foodPostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Foods foods = new Foods(Integer.valueOf(params[0]), params[1], params[2], Integer.valueOf(params[3]), params[4], Integer.valueOf(params[5]), Integer.valueOf(params[6]));
            RestClient.createFood(foods);
            return "new food was added";
        }

        @Override
        protected void onPostExecute(String response) {
            //resultTextView.setText(response);
        }
    }

    //Search method
    private class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return GoogleSearch.search(params[0], new String[]{"num"}, new
                    String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            //TextView searchResult = (TextView) getActivity().findViewById(R.id.diet_search_result);
            //searchResult.setText(GoogleSearch.getSnippet(result));
            Log.d(GoogleSearch.getSnippet(result), "onPostExecute: ");
            MyImageView myImageView = (MyImageView) getActivity().findViewById(R.id.diet_image);
            myImageView.setImageURL(GoogleSearch.getSnippet(result));

        }
    }

    //Search food name
    private class FoodNameAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return RestClient.findByCategory(str);
        }

        @Override
        protected void onPostExecute(String foods) {
            TextView foodDetail = (TextView) getActivity().findViewById(R.id.diet_foodDetail);
            foodDetail.setText("Food Detail shows below: \n" + foods);
            List<String> foodName = new ArrayList<String>();
            if(foods!=null){
                try {
                    JSONArray jArray = new JSONArray(foods);
                    for(int i=0;i<jArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jArray.getString(i));
                        String aaa = jsonObject.get("foodname").toString();
                        foodName.add(aaa);
                        //Toast.makeText(getActivity(),aaa,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final Spinner foodNameSpinner = getActivity().findViewById((R.id.diet_foodName));
                final ArrayAdapter<String> foodNameSpinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, foodName);
                foodNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                foodNameSpinner.setAdapter(foodNameSpinnerAdapter);
            }
        }
    }

    //Search food detail
    private class SearchFoodDetailAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
//            return NBDSearch.search(params[0], new String[]{"num"}, new
//                    String[]{"1"});
            return NBDSearch.foodAPISearch(params[0]);
        }
        @Override
        protected void onPostExecute(String result) {
//            TextView searchResult = (TextView) getActivity().findViewById(R.id.diet_search_result);
//            searchResult.setText(GoogleSearch.getSnippet(result));
//            Log.d(GoogleSearch.getSnippet(result), "onPostExecute: ");
//            MyImageView myImageView = (MyImageView) getActivity().findViewById(R.id.diet_image);
//            myImageView.setImageURL(GoogleSearch.getSnippet(result));
            //NBDSearch.getFoodSnippet(result);
            TextView foodDetail = (TextView) getActivity().findViewById(R.id.diet_foodDetail);
            foodDetail.setText("Food Detail shows below: \n" + NBDSearch.getFoodsSnippet(result));
            //Toast.makeText(getActivity(),result,Toast.LENGTH_LONG);

        }
    }


}
