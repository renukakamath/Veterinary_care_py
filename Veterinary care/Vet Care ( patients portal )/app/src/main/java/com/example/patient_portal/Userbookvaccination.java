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

public class Userbookvaccination extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] vaccination,date,rate,vaccination_id,val;
    public static String amt,vid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userbookvaccination);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        l1=(ListView)findViewById(R.id.lvpets);
        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) Userbookvaccination.this;
        String q1 = "/user_viewvaccination?login_id="+Login.logid;
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


            if (method.equalsIgnoreCase("user_viewvaccination")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    vaccination=new String[ja1.length()];
                    vaccination_id=new String[ja1.length()];
                    date= new String[ja1.length()];
                    rate = new String[ja1.length()];

                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        vaccination[i]=ja1.getJSONObject(i).getString("vaccination");
                        vaccination_id[i]=ja1.getJSONObject(i).getString("vaccination_id");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        rate[i] = ja1.getJSONObject(i).getString("rate");


                        val[i] ="Vaccination : "+ vaccination[i]+"\nDate  :  " + date[i] + "\nRate :  " + rate[i];


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
        amt=rate[position];
        vid=vaccination_id[position];


        final CharSequence[] items = {"Book","view bookings","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userbookvaccination.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Book"))
                {

                    JsonReq JR1=new JsonReq();
                    JR1.json_response=(JsonResponse) Userbookvaccination.this;
                    String q1 = "/booknow?login_id="+Login.logid+"&amt="+amt +"&vid="+vid;
                    q1=q1.replace(" ","%20");
                    JR1.execute(q1);
                    Toast.makeText(getApplicationContext(), " Bookings successfully!!", Toast.LENGTH_LONG).show();


                }

                else if (items[item].equals("view bookings")) {

                    startActivity(new Intent(getApplicationContext(),Viewvaccinebooking.class));

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
