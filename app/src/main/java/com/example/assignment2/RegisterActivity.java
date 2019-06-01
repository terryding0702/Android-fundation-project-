package com.example.assignment2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2.Entities.Credentials;
import com.example.assignment2.Entities.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.assignment2.DataFetching.MD5;

public class RegisterActivity extends AppCompatActivity {

    LocalDbHelper db;
    TextView regCheck;
    EditText regFirstname, regSurname, regHeight, regWeight, regAddress, regStepsPerMile, regUsername, regPassword, regEmail, regRPassword,regUserid;
    String regDob;
    RadioGroup regGroup;
    RadioButton regMale, regFemale;
    Spinner regPostcode, regLevOfAct;
    String gender;
    Button regBtn;

    String userName, email;
    String pwd;

    Users user = new Users();

    String userName1;
    String email1;
    String selectedPostcode;
    String selectedAct;
    Boolean euFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        //postcode Array
        List<String> postcodeList = new ArrayList<String>();
        postcodeList.add("VIC3125");
        postcodeList.add("VIC3163");
        postcodeList.add("VIC3126");
        postcodeList.add("VIC3166");
        postcodeList.add("VIC3155");
        postcodeList.add("VIC3143");
        postcodeList.add("VIC3185");
        postcodeList.add("VIC3122");
        postcodeList.add("VIC3167");
        //actLevel Array
        List<Integer> actList = new ArrayList<Integer>();
        actList.add(1);
        actList.add(2);
        actList.add(3);
        actList.add(4);
        actList.add(5);

       // db = new DatabaseHelper(this);
        db = new LocalDbHelper(this);

        doRegist();


        //postcode spinner
        ArrayAdapter<String> spinnerAdapterP = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, postcodeList);
        spinnerAdapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regPostcode.setAdapter(spinnerAdapterP);

        regPostcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPostcode = parent.getItemAtPosition(position).toString();
                if (selectedPostcode != null) {
                    //Toast.makeText(parent.getContext(), "Postcode is " + selectedPostcode, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<Integer> spinnerAdapterA = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, actList);
        spinnerAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regLevOfAct.setAdapter(spinnerAdapterA);

        regLevOfAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAct = parent.getItemAtPosition(position).toString();
                if (selectedAct != null) {
                    //parent.getContext()
                    //Toast.makeText(RegisterActivity.this, "Active level is " + selectedAct, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        regGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (regMale.getId() == checkedId) {
                    gender = regMale.getText().toString();
                } else if (regFemale.getId() == checkedId) {
                    gender = regFemale.getText().toString();
                }
                //Toast.makeText(RegisterActivity.this,gender, Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void doRegist() {
        //back to login page

        String firstName = regFirstname.getText().toString();
        String surName = regSurname.getText().toString();
        String height = regHeight.getText().toString();
        String weight = regWeight.getText().toString();
        String address = regAddress.getText().toString();
        String stepsPerMile = regStepsPerMile.getText().toString();
        String password = regPassword.getText().toString();
        String rePassword = regRPassword.getText().toString();
        String userId = regUserid.getText().toString();
        TextView stepsCheck = (TextView) findViewById(R.id.reg_check_stepspermile);
        TextView userIdCheck = (TextView) findViewById(R.id.reg_check_user_id);

        //hash password
        pwd = MD5(password);
        //Toast.makeText(RegisterActivity.this,pwd,Toast.LENGTH_LONG).show();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Boolean euFlag = true;
                email = regEmail.getText().toString();
                userName = regUsername.getText().toString();
                //Toast.makeText(RegisterActivity.this,userName,Toast.LENGTH_LONG).show();
//                String user = regUsername.getText().toString().trim();
//                String pwd = regPassword.getText().toString().trim();
//                String rePwd = regRPassword.getText().toString().trim();
                EmailAsyncTask et = new EmailAsyncTask();
                et.execute();
                UserNameAsyncTask ut = new UserNameAsyncTask();
                ut.execute();
                //Toast.makeText(RegisterActivity.this,userName1,Toast.LENGTH_LONG).show();
                if(euFlag){
                    if (email.equals(email1)) {
                        TextView emailCheck = (TextView) findViewById(R.id.reg_check_email);
                        emailCheck.setText("email has duplicted!");
                        emailCheck.setVisibility(View.VISIBLE);
                        emailCheck.setTextColor(getResources().getColor(R.color.traffic_red));
                        euFlag = false;
                    } else if (email.isEmpty()) {
                        TextView emailCheck = (TextView) findViewById(R.id.reg_check_email);
                        emailCheck.setText("email should not be empty!");
                        emailCheck.setVisibility(View.VISIBLE);
                        emailCheck.setTextColor(getResources().getColor(R.color.traffic_red));
                        euFlag = false;
                    } else if(!emailCheck(email)){
                        TextView emailCheck = (TextView) findViewById(R.id.reg_check_email);
                        emailCheck.setText("illegal email address!");
                        emailCheck.setVisibility(View.VISIBLE);
                        emailCheck.setTextColor(getResources().getColor(R.color.traffic_red));
                        euFlag = false;
                    }else {
                        euFlag = true;
                    }
                    //username check
                    if (userName.equals(userName1)) {
                        TextView userNameCheck = (TextView) findViewById(R.id.reg_check_username);
                        userNameCheck.setText("username has duplicted!");
                        userNameCheck.setVisibility(View.VISIBLE);
                        userNameCheck.setTextColor(getResources().getColor(R.color.traffic_red));
                        euFlag = false;
                    } else if (userName.isEmpty()) {
                        TextView userNameCheck = (TextView) findViewById(R.id.reg_check_username);
                        userNameCheck.setText("username should not be empty!");
                        userNameCheck.setVisibility(View.VISIBLE);
                        userNameCheck.setTextColor(getResources().getColor(R.color.traffic_red));
                        euFlag = false;
                    } else {
                        euFlag = true;
                    }
                }else {
                    euFlag = true;
                }
                // email check
                inNotEmpty();

                String firstName = regFirstname.getText().toString();
                String surName = regSurname.getText().toString();
                String height = regHeight.getText().toString();
                String weight = regWeight.getText().toString();
                String address = regAddress.getText().toString();
                String stepsPerMile = regStepsPerMile.getText().toString();
                String password = regPassword.getText().toString();
                String rePassword = regRPassword.getText().toString();
                String userId = regUserid.getText().toString();
                TextView stepsCheck = (TextView) findViewById(R.id.reg_check_stepspermile);
                TextView userIdCheck = (TextView) findViewById(R.id.reg_check_user_id);
                //tranfer formate
                //SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                DatePicker dp = (DatePicker) findViewById(R.id.reg_datePicker);
                String month;
                month = "" + dp.getMonth()+1;
                if((dp.getMonth()+1)<=9){
                    month = "0" + dp.getMonth();
                }
                String day = "" + dp.getDayOfMonth();
                if((dp.getDayOfMonth())<=9){
                    day = "0" + dp.getMonth();
                }
                //regDob = "" + dp.getYear() + "" + month + day;
                regDob = "" + dp.getYear() + "-" + month + "-" + day;
//login logical
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = sdf.format(new Date());
                if(inNotEmpty() && euFlag){
                    PostUserAsyncTask pa = new PostUserAsyncTask();
                    pa.execute(userId,firstName,surName,email,regDob,height,weight,gender,address,selectedPostcode,selectedAct,stepsPerMile);
//                    db.addSteps(userName,Integer.parseInt(stepsPerMile),currentDate);
                    PostCredentialAsyncTask pc = new PostCredentialAsyncTask();
                    pc.execute(userName,userId,password,currentDate);
//                    Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(loginIntent);
                }else{
                    Toast.makeText(RegisterActivity.this, "Registeration error", Toast.LENGTH_LONG).show();

                }

//                if (pwd.equals(rePwd)) {
//                    //hash password using
//                    //pwd = DataFetching.hashPassword(pwd);
//                    long val = db.addUser(user, pwd);
//                    if (val > 0) {
//                        Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_LONG).show();
//                        Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
//                        startActivity(loginIntent);
//                    } else {
//                        Toast.makeText(RegisterActivity.this, "Registeration error", Toast.LENGTH_LONG).show();
//
//                    }
//
//                } else {
//                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_LONG).show();
//                }

            }
        });

    }

    private void initViews() {
        regPostcode = (Spinner) findViewById(R.id.reg_postcode);
        regLevOfAct = (Spinner) findViewById(R.id.reg_levofact);
        regEmail = (EditText) findViewById(R.id.reg_email);
        regFirstname = (EditText) findViewById(R.id.reg_firstname);
        regSurname = (EditText) findViewById(R.id.reg_surname);
        regHeight = (EditText) findViewById(R.id.reg_height);
        regWeight = (EditText) findViewById(R.id.reg_weight);
        regAddress = (EditText) findViewById(R.id.reg_address);
        regStepsPerMile = (EditText) findViewById(R.id.reg_stepspermile);
        regPassword = (EditText) findViewById(R.id.reg_password);
        regRPassword = (EditText) findViewById(R.id.reg_repassword);
        regUsername = (EditText) findViewById(R.id.reg_username);
        regUserid = (EditText) findViewById(R.id.reg_user_id);
        regGroup = (RadioGroup) findViewById(R.id.reg_radioGroup);
        regMale = (RadioButton) findViewById(R.id.reg_male);
        regFemale = (RadioButton) findViewById(R.id.reg_female);
        regBtn = (Button) findViewById(R.id.reg_btn);



    }

    private Boolean inNotEmpty() {
//        String postCode = regPostcode.getSelectedItem().toString();
//        String LevOfAct = String.valueOf(regLevOfAct.getSelectedItem());
        Boolean flag = true;
        String firstName = regFirstname.getText().toString();
        String surName = regSurname.getText().toString();
        String height = regHeight.getText().toString();
        String weight = regWeight.getText().toString();
        String address = regAddress.getText().toString();
        String stepsPerMile = regStepsPerMile.getText().toString();
        String password = regPassword.getText().toString();
        String rePassword = regRPassword.getText().toString();
        String userId = regUserid.getText().toString();
        TextView stepsCheck = (TextView) findViewById(R.id.reg_check_stepspermile);
        TextView userIdCheck = (TextView) findViewById(R.id.reg_check_user_id);


        if (flag) {
            if (firstName.isEmpty()) {
                flag = false;
                TextView firstNameCheck = (TextView) findViewById(R.id.reg_check_first_name);
                firstNameCheck.setVisibility(View.VISIBLE);
                firstNameCheck.setText("FirstName should not empty ");
                firstNameCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            }

            if (surName.isEmpty()) {
                flag = false;
                TextView surNameCheck = (TextView) findViewById(R.id.reg_check_sur_name);
                surNameCheck.setVisibility(View.VISIBLE);
                surNameCheck.setText("FirstName should not empty ");
                surNameCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            }

            if (height.isEmpty()) {
                flag = false;
                TextView heightCheck = (TextView) findViewById(R.id.reg_check_height);
                heightCheck.setVisibility(View.VISIBLE);
                heightCheck.setText("height should not empty ");
                heightCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            }

            if (weight.isEmpty()) {
                flag = false;
                TextView weightCheck = (TextView) findViewById(R.id.reg_check_weight);
                weightCheck.setVisibility(View.VISIBLE);
                weightCheck.setText("weight should not empty ");
                weightCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            }

            if (address.isEmpty()) {
                flag = false;
                TextView addressCheck = (TextView) findViewById(R.id.reg_check_address);
                addressCheck.setVisibility(View.VISIBLE);
                addressCheck.setText("address should not empty ");
                addressCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            }

            if (stepsPerMile.isEmpty()) {
                flag = false;
                stepsCheck.setVisibility(View.VISIBLE);
                stepsCheck.setText("steps per mile should not empty ");
                stepsCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            } else if (!isInteger(stepsPerMile)) {
                flag = false;
                stepsCheck.setVisibility(View.VISIBLE);
                stepsCheck.setText("steps per mile only accept numbers ");
                stepsCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            } else {
                flag = true;
                stepsCheck.setVisibility(View.INVISIBLE);
            }

            if (!rePassword.equals(password)) {
                flag = false;
                TextView repwdCheck = (TextView) findViewById(R.id.reg_check_password);
                repwdCheck.setVisibility(View.VISIBLE);
                repwdCheck.setText("password enter is different");
                repwdCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            }

            if (password.isEmpty()) {
                flag = false;
                TextView pwdCheck = (TextView) findViewById(R.id.reg_check_password);
                pwdCheck.setVisibility(View.VISIBLE);
                pwdCheck.setText("password should not empty ");
                pwdCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            }

            if (rePassword.isEmpty()) {
                flag = false;
                TextView repwdCheck = (TextView) findViewById(R.id.reg_check_password);
                repwdCheck.setVisibility(View.VISIBLE);
                repwdCheck.setText("repassword should not empty ");
                repwdCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            }

            if (userId.isEmpty()) {
                flag = false;
                userIdCheck.setVisibility(View.VISIBLE);
                userIdCheck.setText("user id should not empty ");
                userIdCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            } else if (!isInteger(userId)) {
                flag = false;
                userIdCheck.setVisibility(View.VISIBLE);
                userIdCheck.setText("user id only accept numbers ");
                userIdCheck.setTextColor(getResources().getColor(R.color.traffic_red));
            } else {
                flag = true;
                userIdCheck.setVisibility(View.INVISIBLE);
            }
        }
        return flag;
    }


    public static boolean emailCheck(String email) {
        String rule  = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        if(email.matches(rule)) {
            return true;
        }else
            return false;
    }

    //check if is number
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private class EmailAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            //Toast.makeText(RegisterActivity.this,email1,Toast.LENGTH_LONG).show();
            //email1 = regEmail.getText().toString();
            return RestClient.findUserByEmail(email);
        }

        @Override
        protected void onPostExecute(String email11) {
            //Toast.makeText(RegisterActivity.this,email11,Toast.LENGTH_LONG).show();
            try {
                JSONArray jArray = new JSONArray(email11);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject(jArray.getString(i));
                    email1 = jsonObject.get("email").toString();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class UserNameAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return RestClient.findByUserName(userName);
        }

        @Override
        protected void onPostExecute(String userName) {
            try {
                JSONArray jArray = new JSONArray(userName);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject(jArray.getString(i));
                    String userList = jsonObject.get("credentialPK").toString();
                    JSONObject jsonObject1 = new JSONObject(userList);
                    userName1 = jsonObject1.get("username").toString();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Toast.makeText(RegisterActivity.this,userName1,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private class PostUserAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            Users user = new Users(Integer.valueOf(params[0]),params[1],params[2], params[3],params[4],Double.valueOf(params[5]),Double.valueOf(params[6]),params[7],params[8],params[9],Integer.valueOf(params[10]),Double.valueOf(params[11]));
            RestClient.createUser(user);
            return "Users added";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_LONG).show();
        }
    }

    private class PostCredentialAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            Credentials credential = new Credentials(params[0],Integer.valueOf(params[1]),params[2],params[3]);
            RestClient.createCredential(credential);
            return "Credential added";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_LONG).show();
        }
    }
}
