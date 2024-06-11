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

public class Userviewstaff extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] fname,lname,phone,email,type,staff_id,val;
    public static String sid;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewstaff);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        l1=(ListView)findViewById(R.id.lvpets);
        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) Userviewstaff.this;
        String q1 = "/Userviewstaff?login_id="+Login.logid;
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


            if (method.equalsIgnoreCase("Userviewstaff")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    fname=new String[ja1.length()];
                    lname=new String[ja1.length()];
                    phone= new String[ja1.length()];
                    email = new String[ja1.length()];
                    type = new String[ja1.length()];
                    staff_id= new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        fname[i]=ja1.getJSONObject(i).getString("fname");
                        lname[i]=ja1.getJSONObject(i).getString("lname");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        type[i] = ja1.getJSONObject(i).getString("type");
                        staff_id[i] = ja1.getJSONObject(i).getString("staff_id");

                        val[i] ="fname : "+ fname[i]+"\nlname  :  " + lname[i] + "\nphone :  " + phone[i]+"\nemail:  " + email[i] +"\ntype:  " + type[i];


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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        sid=staff_id[i];

        final CharSequence[] items = {"Book","view bookings","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewstaff.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Book"))
                {

                    JsonReq JR1=new JsonReq();
                    JR1.json_response=(JsonResponse) Userviewstaff.this;
                    String q1 = "/userbookstaff?login_id="+Login.logid+"&sid="+sid ;
                    q1=q1.replace(" ","%20");
                    JR1.execute(q1);
                    Toast.makeText(getApplicationContext(), " Bookings successfully!!", Toast.LENGTH_LONG).show();


                }

                else if (items[item].equals("view bookings")) {

                    startActivity(new Intent(getApplicationContext(),Viewstaffbooking.class));

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