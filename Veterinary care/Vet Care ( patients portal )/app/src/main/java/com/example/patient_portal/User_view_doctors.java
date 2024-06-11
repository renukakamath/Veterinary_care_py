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

public class User_view_doctors extends AppCompatActivity implements JsonResponse,AdapterView.OnItemClickListener {
    ListView l1;
    String[] doctor_id,dname,dphone,demail,dplace,dqual,val;
    public static String doctor_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_view_doctors);

        l1=(ListView)findViewById(R.id.lvdoctors);
        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_view_doctors.this;
        String q1 = "/user_view_doctors";
        q1=q1.replace(" ","%20");
        JR1.execute(q1);

    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try {
            String method = jo.getString("method");



            if (method.equalsIgnoreCase("user_view_doctors")) {
                String status = jo.getString("status");
                Log.d("pearl", status);




                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    doctor_id=new String[ja1.length()];
                    dname = new String[ja1.length()];
                    dphone = new String[ja1.length()];
                    demail = new String[ja1.length()];
                    dplace = new String[ja1.length()];
                    dqual = new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        doctor_id[i]=ja1.getJSONObject(i).getString("doctor_id");
                        dname[i] = ja1.getJSONObject(i).getString("dname");
                        dphone[i] = ja1.getJSONObject(i).getString("phone");
                        demail[i] = ja1.getJSONObject(i).getString("email");
                        dplace[i] = ja1.getJSONObject(i).getString("place");
                        dqual[i] = ja1.getJSONObject(i).getString("qualification");

                        val[i] = "Doctor Name  :  " + dname[i] + "\nContact Number  :  " + dphone[i]+ "\nEmail  :  " + demail[i]+ "\nPlace  :  " + dplace[i]+"\nQualification  :  " + dqual[i]+"\n";


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
        doctor_ids=doctor_id[arg2];

        final CharSequence[] items = {"View Schedules","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_doctors.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("View Schedules"))
                {
                    startActivity(new Intent(getApplicationContext(),User_view_schedule.class));
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
