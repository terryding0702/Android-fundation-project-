package com.example.assignment2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText loginUsername;
    EditText loginPassword;
    Button loginBtn;
    TextView loginRegister;
    LocalDbHelper db;
    String user;
    String pwd;

    String user1;
    String pwd1;
    String firstName1;

    int steps;
    String userId1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = (EditText) findViewById(R.id.login_username);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginRegister = (TextView) findViewById(R.id.login_register);
        //db = new DatabaseHelper(this);
        db = new LocalDbHelper(this);

        //user = loginUsername.getText().toString().trim();
        //pwd = loginPassword.getText().toString().trim();

        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginFunction();

    }

    private void loginFunction(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserNameAsyncTask findUserName = new UserNameAsyncTask();
                findUserName.execute();
                user = loginUsername.getText().toString();
                pwd = loginPassword.getText().toString();
                //Boolean res = db.checkUser(user,pwd);
                // data hashing
                // hashPassword(pwd);
                // get current time
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                if(user.equals(user1) && pwd.equals(pwd1)){
                   // db.addSteps(user,steps,currentDate);
                    Intent LoginScreen = new Intent(MainActivity.this, HomeActivity.class);
                    LoginScreen.putExtra("firstName",firstName1);
                    LoginScreen.putExtra("userId",userId1);
                    startActivity(LoginScreen);
                }else{
                    Toast.makeText(MainActivity.this, "log in error", Toast.LENGTH_LONG).show();
                }

//                if (res){
//                    Toast.makeText(MainActivity.this, "Successfully log in", Toast.LENGTH_LONG).show();
//                    Intent LoginScreen = new Intent(MainActivity.this, HomeActivity.class);
//                    LoginScreen.putExtra("username",user);
//                    startActivity(LoginScreen);
//
//                }else {
//                    //Toast.makeText(MainActivity.this, "Successfully log in", Toast.LENGTH_LONG).show();
//                    Toast.makeText(MainActivity.this, "log in error", Toast.LENGTH_LONG).show();
//                }

            }
        });

    }

    private class UserNameAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.findByUserName(user);
        }
        @Override
        protected void onPostExecute (String userName){
            try {
                JSONArray jArray = new JSONArray(userName);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject = new JSONObject(jArray.getString(i));
                    String userList = jsonObject.get("credentialPK").toString();
                    pwd1 = jsonObject.get("passwordhash").toString();
                    String firstNameList = jsonObject.get("users").toString();
                    JSONObject jsonObject1 = new JSONObject(userList);
                    JSONObject jsonObject2 = new JSONObject(firstNameList);
                    user1 = jsonObject1.get("username").toString();
                    userId1 = jsonObject1.get("userid").toString();
                    firstName1 = jsonObject2.get("name").toString();

                    //Toast.makeText(MainActivity.this,firstName1,Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Toast.makeText(MainActivity.this,userName,Toast.LENGTH_LONG).show();

        }

    }

//    private class PasswordAsyncTask extends AsyncTask<Void, Void, String>
//    {
//        @Override
//        protected String doInBackground (Void...params){
//            return RestClient.findByPwd(pwd);
//        }
//        @Override
//        protected void onPostExecute (String password){
//            //Toast.makeText(MainActivity.this,password,Toast.LENGTH_LONG).show();
//            passwordCheck = password;
//
//        }
//    }


}
