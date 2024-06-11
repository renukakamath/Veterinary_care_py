package com.example.patient_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class User_book_doctor extends AppCompatActivity implements JsonResponse {

    TextView t1;
    Button b1;
    EditText e1,e2,e3,e4;
    String acno,name,cvv,date;
    public static String fee_amount;
    SharedPreferences sh;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_book_doctor);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t1=(TextView)findViewById(R.id.tvtotal);
        t1.setText(User_view_schedule.fee_amounts);


        b1=(Button)findViewById(R.id.btpay);


        e1 = findViewById(R.id.acno);
        e2 = findViewById(R.id.cvv);
        e3 = findViewById(R.id.date);
        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(User_book_doctor.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                e3.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        e4 = findViewById(R.id.name);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acno = e1.getText().toString();
                cvv = e2.getText().toString();
                date = e3.getText().toString();
                name = e4.getText().toString();

                if(acno.equalsIgnoreCase("")  || acno.length()!=16)
                {
                    e1.setError("please enter Valid 16 digit Account Number");
                    e1.setFocusable(true);
                }
                else if(cvv.equalsIgnoreCase("") || cvv.length()!=3)
                {
                    e2.setError("please enter # digit Cvv");
                    e2.setFocusable(true);
                }
                else if(date.equalsIgnoreCase("")  )
                {
                    e3.setError("enter Expiery date");
                    e3.setFocusable(true);
                }

                else if(name.equalsIgnoreCase("") )
                {
                    e4.setError("please Enter your Name");
                    e4.setFocusable(true);
                }
                else {




                JsonReq JR1=new JsonReq();
                JR1.json_response=(JsonResponse) User_book_doctor.this;
                String q1 = "/user_book_doctor?schedule_ids="+User_view_schedule.schedule_ids+"&pet_id="+User_pet_book_doctor.pet_ids+"&fee_amount="+User_view_schedule.fee_amounts+"&lid="+Login.logid;
                q1=q1.replace(" ","%20");
                JR1.execute(q1);
                }
            }
        });

    }

    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");


            if (method.equalsIgnoreCase("user_book_doctor")) {
                String status = jo.getString("status");
                Log.d("pearl",status);
                if (status.equalsIgnoreCase("success")) {
                    SharedPreferences.Editor e=sh.edit();
                    e.putString("type","doctor");
                    e.commit();
                    Toast.makeText(getApplicationContext(), " Payment Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Users_home.class));
                } else {
                    Toast.makeText(getApplicationContext(), " failed", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),User_view_doctors.class);
        startActivity(b);
    }

}
