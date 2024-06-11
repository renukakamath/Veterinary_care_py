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

public class User_view_test_pres extends AppCompatActivity  implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    String[] book_id,test_pres_id,lab_name,lphone,test_name,description,rate,lab_pres_description,report_description,test_date,test_status,val;
    public static String test_pres_ids,test_statuss,book_ids,rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_view_test_pres);


        l1=(ListView)findViewById(R.id.lvtext);
        l1.setOnItemClickListener(this);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_view_test_pres.this;
        String q1 = "/user_view_test_pres?booking_id="+User_view_bookings.booking_ids;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);

    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try {
            String method = jo.getString("method");
//            Toast.makeText(getApplicationContext(), method, Toast.LENGTH_LONG).show();

            if (method.equalsIgnoreCase("user_forward_test_pres")) {
                String status = jo.getString("status");
                Log.d("pearl",status);
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), " Forward Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), User_view_test_pres.class));
                } else {
                    Toast.makeText(getApplicationContext(), " failed", Toast.LENGTH_SHORT).show();
                }
            }


            if (method.equalsIgnoreCase("user_view_test_pres")) {
                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    book_id=new String[ja1.length()];
                    test_pres_id=new String[ja1.length()];
                    lab_name=new String[ja1.length()];
                    lphone = new String[ja1.length()];
                    test_name = new String[ja1.length()];
                    description = new String[ja1.length()];
                    rate = new String[ja1.length()];
                    lab_pres_description = new String[ja1.length()];
                    report_description = new String[ja1.length()];
                    test_date = new String[ja1.length()];
                    test_status = new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        book_id[i]=ja1.getJSONObject(i).getString("book_id");
                        test_pres_id[i]=ja1.getJSONObject(i).getString("test_pres_id");
                        lab_name[i]=ja1.getJSONObject(i).getString("lab_name");
                        lphone[i] = ja1.getJSONObject(i).getString("lphone");
                        test_name[i] = ja1.getJSONObject(i).getString("test_name");
                        description[i] = ja1.getJSONObject(i).getString("description");
                        rate[i] = ja1.getJSONObject(i).getString("rate");
                        lab_pres_description[i] = ja1.getJSONObject(i).getString("lab_pres_description");
                        report_description[i] = ja1.getJSONObject(i).getString("report_description");
                        test_date[i] = ja1.getJSONObject(i).getString("test_date");
                        test_status[i] = ja1.getJSONObject(i).getString("test_status");

                        val[i] = "Lab Name  :  " + lab_name[i] +  "\nContact Number  :  " + lphone[i]+ "\nDate  :  " + test_date[i]+ "\nStatus  :  " + test_status[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                } else {
                    Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_SHORT).show();

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

        test_pres_ids = test_pres_id[arg2];
        test_statuss=test_status[arg2];
        book_ids=book_id[arg2];
        rates=rate[arg2];

        if (test_statuss.equalsIgnoreCase("Accept")) {

            final CharSequence[] items = {"Make Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_view_test_pres.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Make Payment")) {

                        startActivity(new Intent(getApplicationContext(),User_test_payment.class));

//                        JsonReq JR1=new JsonReq();
//                        JR1.json_response=(JsonResponse) User_view_test_pres.this;
//                        String q1 = "/user_payment?action=test&booking_id="+User_view_bookings.booking_ids;
//                        q1=q1.replace(" ","%20");
//                        JR1.execute(q1);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();

        }

        else if (test_statuss.equalsIgnoreCase("Pending")) {

            final CharSequence[] items = {"Forward to Lab", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_view_test_pres.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Forward to Lab")) {

                        JsonReq JR1=new JsonReq();
                        JR1.json_response=(JsonResponse) User_view_test_pres.this;
                        String q1 = "/user_forward_test_pres?test_pres_ids="+test_pres_ids;
                        q1=q1.replace(" ","%20");
                        JR1.execute(q1);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();

        }
        else if (test_statuss.equalsIgnoreCase("paid")) {

            final CharSequence[] items = {"View Result", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_view_test_pres.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("View Result")) {

                        startActivity(new Intent(getApplicationContext(),userViewResult.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();

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
