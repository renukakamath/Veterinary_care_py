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

public class User_view_bookings extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener  {

    ListView l1;
    String[] doctor_id,booking_id,pet_name,dname,phone,email,place,qual,sh_date,start_time,end_time,fee_amount,booked_date,bstatus,val;
    public static String booking_ids,doctor_ids;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_view_bookings);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        l1=(ListView)findViewById(R.id.lvbookings);
        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_view_bookings.this;
        String q1 = "/user_view_bookings?login_id="+Login.logid;
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


            if (method.equalsIgnoreCase("user_view_bookings")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    doctor_id=new String[ja1.length()];
                    booking_id=new String[ja1.length()];
                    pet_name= new String[ja1.length()];
                    dname = new String[ja1.length()];
                    phone = new String[ja1.length()];
                    email = new String[ja1.length()];
                    place = new String[ja1.length()];
                    qual = new String[ja1.length()];
                    sh_date = new String[ja1.length()];
                    start_time = new String[ja1.length()];
                    end_time = new String[ja1.length()];
                    fee_amount = new String[ja1.length()];
                    booked_date = new String[ja1.length()];
                    bstatus = new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        doctor_id[i]=ja1.getJSONObject(i).getString("doctor_id");
                        booking_id[i]=ja1.getJSONObject(i).getString("book_id");
                        pet_name[i] = ja1.getJSONObject(i).getString("name");
                        dname[i] = ja1.getJSONObject(i).getString("dname");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        place[i] = ja1.getJSONObject(i).getString("place");
                        qual[i] = ja1.getJSONObject(i).getString("qualification");
                        sh_date[i] = ja1.getJSONObject(i).getString("sh_date");
                        start_time[i] = ja1.getJSONObject(i).getString("start_time");
                        end_time[i] = ja1.getJSONObject(i).getString("end_time");
                        fee_amount[i] = ja1.getJSONObject(i).getString("fee_amount");
                        booked_date[i] = ja1.getJSONObject(i).getString("booked_date");
                        bstatus[i] = ja1.getJSONObject(i).getString("status");

                        val[i] ="Pet Name : "+ pet_name[i]+"\nDoctor Name  :  " + dname[i] + "\nContact Number :  " + phone[i]+ "\nEmail  :  " + email[i]+ "\nPlace  :  " + place[i]+ "\nQualification  :  " + qual[i]+ "\nSchedule Date  :  " + sh_date[i]+ "\nStart Time  :  " + start_time[i]+ "\nEnd Time  :  " + end_time[i]+ "\nFee Amount  :  " + fee_amount[i]+ "\nBooked Date  :  " + booked_date[i]+ "\nStatus  :  " + bstatus[i];


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
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        booking_ids=booking_id[arg2];

        doctor_ids = doctor_id[arg2];

        SharedPreferences.Editor e=sh.edit();
        e.putString("receiver_id",doctor_ids);
        e.commit();


        final CharSequence[] items = {"Medicine Prescription","Test Prescription","Chat","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_bookings.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Medicine Prescription"))
                {
                    startActivity(new Intent(getApplicationContext(),User_view_med_pres.class));
                }

                if (items[item].equals("Test Prescription"))
                {
                    startActivity(new Intent(getApplicationContext(),User_view_test_pres.class));
                }
                if (items[item].equals("Chat"))
                {
                    startActivity(new Intent(getApplicationContext(),ChatHere.class));
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
