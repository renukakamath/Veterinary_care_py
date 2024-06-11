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

public class User_view_schedule extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    String[] schedule_id,date,start_time,end_time,fee_amount,val;
    public static String schedule_ids,fee_amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_view_schedule);

        l1=(ListView)findViewById(R.id.lvschedule);
        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_view_schedule.this;
        String q1 = "/user_view_schedule?doctor_ids="+User_view_doctors.doctor_ids;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);

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


             if (method.equalsIgnoreCase("user_view_schedule")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    schedule_id=new String[ja1.length()];
                    date = new String[ja1.length()];
                    start_time = new String[ja1.length()];
                    end_time = new String[ja1.length()];
                    fee_amount = new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        schedule_id[i]=ja1.getJSONObject(i).getString("schedule_id");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        start_time[i] = ja1.getJSONObject(i).getString("start_time");
                        end_time[i] = ja1.getJSONObject(i).getString("end_time");
                        fee_amount[i] = ja1.getJSONObject(i).getString("fee_amount");

                        val[i] = "Date  :  " + date[i] + "\nStart Time  :  " + start_time[i]+ "\nEnd Time  :  " + end_time[i]+ "\nFee Amount  :  " + fee_amount[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                } else {
                    Toast.makeText(getApplicationContext(), "No Order!!", Toast.LENGTH_LONG).show();

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
        schedule_ids=schedule_id[arg2];
        fee_amounts=fee_amount[arg2];

        final CharSequence[] items = {"Book","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_schedule.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Book"))
                {
                    startActivity(new Intent(getApplicationContext(),User_pet_book_doctor.class));
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
        Intent b=new Intent(getApplicationContext(),User_view_doctors.class);
        startActivity(b);
    }




}
