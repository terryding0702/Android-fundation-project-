package com.example.assignment2.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment2.R;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.assignment2.RegisterActivity.isInteger;


public class DefaultFragment extends Fragment {
    // get current time
    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default,container,false);
        TextView textView = (TextView)view.findViewById(R.id.default_firstName);

        String name = getActivity().getIntent().getStringExtra("firstName");
        textView.setText("Welcome " + name);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView dateTextView = (TextView)getView().findViewById(R.id.current_datetime);
        dateTextView.setText("Toady is " + currentDate);

        EditText goalChange = (EditText) getActivity().findViewById(R.id.default_goal_change);
        Button goalBtn = (Button) getActivity().findViewById(R.id.default_btn);
        TextView goalCheck = (TextView) getActivity().findViewById(R.id.default_goal_check);
        goalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalString = goalChange.getText().toString();
                if(!isInteger(goalString)){
                    goalCheck.setText("Only accept number, please try again");
                    goalCheck.setTextColor(getResources().getColor(R.color.traffic_red));
                    goalCheck.setVisibility(View.VISIBLE);
                }else if(goalString.isEmpty()){
                    goalCheck.setText("cannot accept empty");
                    goalCheck.setTextColor(getResources().getColor(R.color.traffic_red));
                    goalCheck.setVisibility(View.VISIBLE);
                }else{
                    TextView goalDisplay = (TextView) getActivity().findViewById(R.id.default_goalDisplay);
                    goalDisplay.setText("Your calorie goal is: " + goalString);
                }
            }
        });
    }

    //Edit Goal
//    private class PutAsyncTask extends AsyncTask<String, Void, String>
//    {
//        @Override
//        protected String doInBackground(String... params) {
//            Course course=new Course(Integer.valueOf(params[0]),params[1]);
//            RestClient.createCourse(course);
//            return "Course was added";
//        }
//        @Override
//        protected void onPostExecute(String response) {
//            resultTextView.setText(response);
//        }
//    }
}
