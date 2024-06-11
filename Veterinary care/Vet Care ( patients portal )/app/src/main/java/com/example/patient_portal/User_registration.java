package com.example.patient_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class User_registration extends AppCompatActivity implements JsonResponse {

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    Button b1;
    RadioButton r1,r2;
    String fname,lname,phone,email,hname,place,landmark,username,password;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_registration);

        e1=(EditText)findViewById(R.id.etfirst);
        e2=(EditText)findViewById(R.id.etlast);
        e3=(EditText)findViewById(R.id.etphone);
        e4=(EditText)findViewById(R.id.etemail);
        e5=(EditText)findViewById(R.id.ethname);
        e6=(EditText)findViewById(R.id.etplace);
        e7=(EditText)findViewById(R.id.etland);
        e9=(EditText)findViewById(R.id.etusername);
        e10=(EditText)findViewById(R.id.etpassw);

        b1=(Button)findViewById(R.id.btreg);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname=e1.getText().toString();
                lname=e2.getText().toString();
                phone=e3.getText().toString();
                email=e4.getText().toString();
                hname=e5.getText().toString();
                place=e6.getText().toString();
                landmark=e7.getText().toString();

                username=e9.getText().toString();
                password=e10.getText().toString();


                if(fname.equalsIgnoreCase(""))
                {
                    e1.setError("please enter your first name");
                    e1.setFocusable(true);
                }
                else if(lname.equalsIgnoreCase(""))
                {
                    e2.setError("please enter your last name");
                    e2.setFocusable(true);
                }
                else if(phone.equalsIgnoreCase("")  || phone.length()!=10)
                {
                    e3.setError("enter your phone no. in correct format");
                    e3.setFocusable(true);
                }

                else if(email.equalsIgnoreCase("") || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))
                {
                    e4.setError("please enter email");
                    e4.setFocusable(true);
                }
                else if(hname.equalsIgnoreCase(""))
                {
                    e5.setError("please enter your housename");
                    e5.setFocusable(true);
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e6.setError("please enter your Place");
                    e6.setFocusable(true);
                }

                else if(landmark.equalsIgnoreCase(""))
                {
                    e7.setError("please enter your Landmark");
                    e7.setFocusable(true);
                }


                else if(username.equalsIgnoreCase(""))
                {
                    e9.setError("please enter your username");
                    e9.setFocusable(true);
                }
                else if(password.equalsIgnoreCase("")&&password.length()!=6)
                {
                    e10.setError("please enter your password..Password should be 6 characters");
                    e10.setFocusable(true);
                }
                else{
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) User_registration.this;
                    String q = "/userregister?fname="+fname+"&lname="+lname+"&phone="+phone+"&email="+email+"&hname="+hname+"&place="+place+"&landmark="+landmark+"&username="+username+"&password="+password +"&latitude="+LocationService.lati+"&longitude="+LocationService.logi;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }




            }
        });



    }


    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){

                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
            else if(status.equalsIgnoreCase("duplicate")){


                startActivity(new Intent(getApplicationContext(),User_registration.class));
                Toast.makeText(getApplicationContext(), "Username already Exist...", Toast.LENGTH_LONG).show();

            }
            else
            {
                startActivity(new Intent(getApplicationContext(),User_registration.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }

}

