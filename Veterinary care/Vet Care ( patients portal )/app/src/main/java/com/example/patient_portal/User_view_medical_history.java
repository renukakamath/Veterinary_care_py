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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_medical_history extends AppCompatActivity implements JsonResponse  {

    ListView l1;
    String[] booking_id,dname,desp,date,val;
    public static String booking_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_view_medical_history);


        l1=(ListView)findViewById(R.id.lvhistory);
//        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_view_medical_history.this;
        String q1 = "/user_view_medical_history?login_id="+Login.logid;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);

    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

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


            if (method.equalsIgnoreCase("user_view_medical_history")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
//                Toast.makeText(getApplicationContext(), "HELLOOOOOO", Toast.LENGTH_LONG).show();

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    booking_id=new String[ja1.length()];
                    dname = new String[ja1.length()];
                    desp = new String[ja1.length()];
                    date = new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        booking_id[i]=ja1.getJSONObject(i).getString("book_id");
                        dname[i] = ja1.getJSONObject(i).getString("dname");
                        desp[i] = ja1.getJSONObject(i).getString("description");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        val[i] = "Doctor Name  :  " + dname[i] + "\nDescription :  " + desp[i]+ "\nDate  :  " + date[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                } else {
                    Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_LONG).show();

                }
            }
        }
        catch (Exception e){
            // TODO: handle exception
        }

    }

//    @Override
//    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//        // TODO Auto-generated method stub
//        booking_ids=booking_id[arg2];
//
//        final CharSequence[] items = {"Medical History","Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_bookings.this);
//        // builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//
//                if (items[item].equals("Medical History"))
//                {
//                    startActivity(new Intent(getApplicationContext(),User_view_medical_history.class));
//                }
//
//
//                else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//
//        });
//        builder.show();
////	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        //startActivityForResult(i, GALLERY_CODE);
//    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }


}
