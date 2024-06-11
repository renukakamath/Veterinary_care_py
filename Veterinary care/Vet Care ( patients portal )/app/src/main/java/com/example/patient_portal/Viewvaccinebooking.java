package com.example.patient_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewvaccinebooking extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] vaccination,date,amt,first_name,vaccination_id,val,booking_id;
    public static String amts,bid;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewvaccinebooking);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        l1=(ListView)findViewById(R.id.sss);
        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) Viewvaccinebooking.this;
        String q1 = "/Viewvaccinebooking?login_id="+Login.logid;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
//            Toast.makeText(getApplicationContext(), method, Toast.LENGTH_LONG).show();

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


            if (method.equalsIgnoreCase("user_view_bookings")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    vaccination=new String[ja1.length()];
                    vaccination_id=new String[ja1.length()];
                    date= new String[ja1.length()];
                    amt = new String[ja1.length()];
                    first_name = new String[ja1.length()];
                    booking_id= new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        vaccination[i]=ja1.getJSONObject(i).getString("vaccination");
                        vaccination_id[i]=ja1.getJSONObject(i).getString("vaccination_id");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        first_name[i] = ja1.getJSONObject(i).getString("first_name");
                        amt[i] = ja1.getJSONObject(i).getString("amt");
                        booking_id[i] = ja1.getJSONObject(i).getString("vaccinebooking_id");

                        val[i] ="Vaccination : "+ vaccination[i]+"\nDate  :  " + date[i] + "\namount :  " + amt[i]+"\nfirst_name  :  " + first_name[i] ;


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                } else {
                    Toast.makeText(getApplicationContext(), "No Bookings!!", Toast.LENGTH_LONG).show();

                }
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            // TODO: handle exception
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        amts=amt[position];
        bid=booking_id[position];
        final CharSequence[] items = {"Make Payment","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewvaccinebooking.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Make Payment"))


                {
                    startActivity(new Intent(getApplicationContext(),Usermakepayment.class));

                }


                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(i, GALLERY_CODE);
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }

}
