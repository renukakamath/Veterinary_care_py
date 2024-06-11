package com.example.patient_portal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_pets extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    String [] pet_id,name,type,breed,age,other_details,image;
    public static String pet_ids,company_ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_view_pets);
        lv1=(ListView)findViewById(R.id.lvpets);

        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_pets.this;
        String q = "/User_view_pets?loginid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }




    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("User_view_pets")){
                String status=jo.getString("status");
                Log.d("pearl",status);

                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    pet_id=new String[ja1.length()];
                    name=new String[ja1.length()];
                    type=new String[ja1.length()];
                    breed=new String[ja1.length()];
                    age=new String[ja1.length()];
                    other_details=new String[ja1.length()];
                    image=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        pet_id[i]=ja1.getJSONObject(i).getString("pets_id");
                        name[i]=ja1.getJSONObject(i).getString("name");
                        type[i]=ja1.getJSONObject(i).getString("type");
                        breed[i]=ja1.getJSONObject(i).getString("breed");
                        age[i]=ja1.getJSONObject(i).getString("age");
                        other_details[i]=ja1.getJSONObject(i).getString("other_details");
                        image[i]=ja1.getJSONObject(i).getString("image");

//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
//                        val[i]="Fuel Type : "+fuel_type[i]+"\nVehicle : "+vehicle[i]+"\nReg.No : "+regnum[i]+"\nDriver Name : "+dname[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i];


                    }
//                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
//                    lv1.setAdapter(ar);

                    Custimage ci=new Custimage(User_view_pets.this, image,name,type,breed,age,other_details);

                    lv1.setAdapter(ci);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

//            if(method.equalsIgnoreCase("user_send_work_request"))
//            {
//                String status=jo.getString("status");
//                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//                if(status.equalsIgnoreCase("success"))
//                {
//                    Toast.makeText(getApplicationContext(),"Requset Send Successfully!", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Sending Failed", Toast.LENGTH_LONG).show();
//                }
//            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final CharSequence[] items = {"View Medicine History", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_pets.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Medicine History")) {
            startActivity(new Intent(getApplicationContext(),User_view_medical_history.class));




                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();

    }
    }

