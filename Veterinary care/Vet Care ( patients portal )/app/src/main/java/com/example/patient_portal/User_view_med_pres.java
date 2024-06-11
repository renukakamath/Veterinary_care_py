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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_med_pres extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    SharedPreferences sh;
    ListView l1;
    String[] book_id,med_pres_id,pharmacy_name,email,ph_phone,medicine_name,med_pres_description,rate,pre_date_time,pre_status,val;
    public static String book_ids,pre_statuss,med_pres_ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_view_med_pres);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvmedpres);
        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_view_med_pres.this;
        String q1 = "/user_view_med_pres?booking_id="+User_view_bookings.booking_ids;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);

    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try {
            String method = jo.getString("method");
//            Toast.makeText(getApplicationContext(), method, Toast.LENGTH_LONG).show();

//            if (method.equalsIgnoreCase("user_payment")) {
//                String status = jo.getString("status");
//                Log.d("pearl",status);
//                if (status.equalsIgnoreCase("success")) {
//
//                    Toast.makeText(getApplicationContext(), " Payment Success", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(), User_view_bookings.class));
//                } else {
//                    Toast.makeText(getApplicationContext(), " failed", Toast.LENGTH_LONG).show();
//                }
//            }

            if (method.equalsIgnoreCase("user_forward_med_pres")) {
                String status = jo.getString("status");
                Log.d("pearl",status);
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), " Forward Success", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), User_view_bookings.class));
                } else {
                    Toast.makeText(getApplicationContext(), " failed", Toast.LENGTH_LONG).show();
                }
            }


            if (method.equalsIgnoreCase("user_view_med_pres")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    book_id=new String[ja1.length()];
                    med_pres_id=new String[ja1.length()];
                    pharmacy_name=new String[ja1.length()];
                    email = new String[ja1.length()];
                    ph_phone = new String[ja1.length()];
                    medicine_name = new String[ja1.length()];
                    med_pres_description = new String[ja1.length()];
                    rate = new String[ja1.length()];
                    pre_date_time = new String[ja1.length()];
                    pre_status = new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        book_id[i]=ja1.getJSONObject(i).getString("book_id");
                        med_pres_id[i]=ja1.getJSONObject(i).getString("med_pres_id");
                        pharmacy_name[i]=ja1.getJSONObject(i).getString("pharmacy_name");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        ph_phone[i] = ja1.getJSONObject(i).getString("ph_phone");
                        medicine_name[i] = ja1.getJSONObject(i).getString("medicine_name");
                        med_pres_description[i] = ja1.getJSONObject(i).getString("med_pres_description");
                        rate[i] = ja1.getJSONObject(i).getString("rate");
                        pre_date_time[i] = ja1.getJSONObject(i).getString("pre_date_time");
                        pre_status[i] = ja1.getJSONObject(i).getString("pre_status");

                        val[i] = "Pharmacy Name  :  " + pharmacy_name[i] + "\nEmail :  " + email[i]+ "\nContact Number  :  " + ph_phone[i]+ "\nPrescription  :  " + med_pres_description[i]+ "\nDate  :  " + pre_date_time[i]+ "\nStatus  :  " + pre_status[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Currently no Prescription!!", Toast.LENGTH_SHORT).show();

                }
            }
        }
        catch (Exception e){
            // TODO: handle exception
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        pre_statuss = pre_status[arg2];
        med_pres_ids=med_pres_id[arg2];
        book_ids=book_id[arg2];

        if (pre_statuss.equalsIgnoreCase("Accept")) {

            final CharSequence[] items = {"Make Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_view_med_pres.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Make Payment")) {

                        SharedPreferences.Editor e=sh.edit();
                        e.putString("type","medicine");
                        e.commit();
                        startActivity(new Intent(getApplicationContext(),User_payment.class));

//                        JsonReq JR1=new JsonReq();
//                        JR1.json_response=(JsonResponse) User_view_med_pres.this;
//                        String q1 = "/user_payment?action=medi&booking_id="+User_view_bookings.booking_ids;
//                        q1=q1.replace(" ","%20");
//                        JR1.execute(q1);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(i, GALLERY_CODE);
        }

        else if(pre_statuss.equalsIgnoreCase("Pending")) {

            final CharSequence[] items = {"Forward to Pharmacy", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_view_med_pres.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Forward to Pharmacy")) {

//                        Toast.makeText(getApplicationContext(),"Hiii Pharmacy",Toast.LENGTH_LONG).show();

                        JsonReq JR1=new JsonReq();
                        JR1.json_response=(JsonResponse) User_view_med_pres.this;
                        String q1 = "/user_forward_med_pres?med_pres_ids="+med_pres_ids;
                        q1=q1.replace(" ","%20");
                        JR1.execute(q1);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(i, GALLERY_CODE);
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }


}
