package com.example.patient_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class User_test_payment extends AppCompatActivity  implements JsonResponse  {

    TextView t1;
    Button b1;
    EditText e1,e2,e3,e4;
    String acno,name,cvv,date;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public static String pamount,pay_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_test_payment);
        t1=(TextView)findViewById(R.id.tvtotal);
        t1.setText(User_view_test_pres.rates);
        pamount=t1.toString();

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


                DatePickerDialog datePickerDialog = new DatePickerDialog(User_test_payment.this,
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


        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_test_payment.this;
        String q1 = "/user_view_test_payment?book_id="+User_view_test_pres.book_ids;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);

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
                JR1.json_response=(JsonResponse) User_test_payment.this;
                String q1 = "/user_test_payment?pay_id="+pay_id+"&test_pres_ids="+User_view_test_pres.test_pres_ids;
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


            if (method.equalsIgnoreCase("user_test_payment")) {
                String status = jo.getString("status");
                Log.d("pearl",status);
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Users_home.class));
                } else {
                    Toast.makeText(getApplicationContext(), " failed", Toast.LENGTH_LONG).show();
                }
            }

            if(method.equalsIgnoreCase("user_view_test_payment")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){

                    t1.setText(jo.getString("amount"));
                    pay_id=jo.getString("pay_id");
                }
                else

                {
                    Toast.makeText(getApplicationContext(), "No Payments!!", Toast.LENGTH_LONG).show();

                }
            }





        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }
}