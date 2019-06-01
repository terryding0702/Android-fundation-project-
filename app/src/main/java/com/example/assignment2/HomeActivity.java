package com.example.assignment2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2.Fragments.BlankFragment;
import com.example.assignment2.Fragments.DefaultFragment;
import com.example.assignment2.Fragments.DietFragment;
import com.example.assignment2.Fragments.StepFragment;
import com.example.assignment2.Fragments.TrackerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.assignment2.RegisterActivity.isInteger;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    String userId;
    String goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //send username to diet_fragment
        Intent getIntent = getIntent();
        String firstName = getIntent.getStringExtra("firstName");
        userId = getIntent.getStringExtra("userId");
        getIntent().putExtra("firstName",firstName);

        GoalAsyncTask ga = new GoalAsyncTask();
        ga.execute();

       // goalChangeCheck();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new DefaultFragment())
                    .commit();

            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    public void goalChangeCheck(){
        EditText goalChange = (EditText) findViewById(R.id.default_goal_change);
        Button goalBtn = (Button) findViewById(R.id.default_btn);
        TextView goalCheck = (TextView) findViewById(R.id.default_goal_check);
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
                    TextView goalDisplay = (TextView) findViewById(R.id.default_goalDisplay);
                    goalDisplay.setText("Your calorie goal is: " + goalString);
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new DefaultFragment())
                        .commit();
                break;
            case R.id.nav_diet:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new DietFragment())
                        .commit();
                break;
            case R.id.nav_step:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StepFragment())
                        .commit();
                break;
            case R.id.nav_tracker:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TrackerFragment())
                        .commit();
                break;
            case R.id.nav_report:
                Toast.makeText(this,"report",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_map:
                //Toast.makeText(this,"Map",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BlankFragment())
//                        .replace(R.id.fragment_container, new MapFragment())
                        .commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private class GoalAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.findById(userId);
        }
        @Override
        protected void onPostExecute (String userId1){
            //Toast.makeText(HomeActivity.this,userId1,Toast.LENGTH_LONG).show();

            try {
                JSONArray jArray = new JSONArray(userId1);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject = new JSONObject(jArray.getString(i));
                    String userList = jsonObject.get("caloriegoal").toString();
                    //JSONObject jsonObject1 = new JSONObject(userList);
                    goal = userList;
                    TextView goalDisplay = (TextView) findViewById(R.id.default_goalDisplay);
                    goalDisplay.setText("Your calorie goal is: " + goal);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}
