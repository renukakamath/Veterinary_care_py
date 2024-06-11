package com.example.patient_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_pet_book_doctor extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {

    Spinner s1;
    String[] pet_id,name,val;
    Button b1;
    String det;
    public static String pet_ids,fee_amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_pet_book_doctor);
        s1=(Spinner) findViewById(R.id.sppet);
        s1.setOnItemSelectedListener(this);

        b1=(Button)findViewById(R.id.btpet) ;

        EditText e1 = findViewById(R.id.etdetails);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_pet_book_doctor.this;
        String q1 = "/User_pet_book_doctor?login_id="+Login.logid;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                det = e1.getText().toString();

                if(det.equalsIgnoreCase(""))
                {
                    e1.setError("please Enter details to Continue");
                    e1.setFocusable(true);
                }else {


                startActivity(new Intent(getApplicationContext(),User_book_doctor.class));
                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try {
            String method = jo.getString("method");


//            if (method.equalsIgnoreCase("user_book_doctor")) {
//                String status = jo.getString("status");
//                Log.d("pearl",status);
//                if (status.equalsIgnoreCase("success")) {
//
//                    Toast.makeText(getApplicationContext(), " Booking Success", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(), User_view_bookings.class));
//                } else {
//                    Toast.makeText(getApplicationContext(), " failed", Toast.LENGTH_LONG).show();
//                }
//            }


            if (method.equalsIgnoreCase("User_pet_book_doctor")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    pet_id=new String[ja1.length()];
                    name = new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        pet_id[i]=ja1.getJSONObject(i).getString("pets_id");
                        name[i] = ja1.getJSONObject(i).getString("name");

                        val[i] = "name  :  " + name[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    s1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                } else {
                    Toast.makeText(getApplicationContext(), "No data!!", Toast.LENGTH_LONG).show();

                }
            }
        }
        catch (Exception e){
            // TODO: handle exception
        }

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        pet_ids=pet_id[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),User_view_schedule.class);
        startActivity(b);
    }

}
