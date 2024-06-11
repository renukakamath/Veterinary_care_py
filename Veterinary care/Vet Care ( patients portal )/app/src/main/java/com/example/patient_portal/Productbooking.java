package com.example.patient_portal;

import androidx.appcompat.app.AppCompatActivity;

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

public class Productbooking extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] product, amount, quantity, date, image, name,value,product_id,shop_id,statu;
    public static String sid, pid, amt, stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productbooking);
        l1 = (ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Productbooking.this;
        String q = "/ViewProductbooking?login_id="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                product = new String[ja1.length()];
                quantity = new String[ja1.length()];
                amount = new String[ja1.length()];
                date = new String[ja1.length()];
                image = new String[ja1.length()];
                name = new String[ja1.length()];

                product_id = new String[ja1.length()];
                shop_id = new String[ja1.length()];
                value=new String[ja1.length()];
                statu = new String[ja1.length()];
                value=new String[ja1.length()];




                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    product[i] = ja1.getJSONObject(i).getString("product");
                    quantity[i] = ja1.getJSONObject(i).getString("quantity");
                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    name[i] = ja1.getJSONObject(i).getString("name");
                    product_id[i] = ja1.getJSONObject(i).getString("product_id");
                    shop_id[i] = ja1.getJSONObject(i).getString("shop_id");

                    statu[i] = ja1.getJSONObject(i).getString("status");




                    value[i] ="product:" + product[i]+ "\nquantity: " + quantity[i] + "\n amount: " + amount[i] + "\ndate: " + date[i] +"\nname:" +name[i]   +"\nstatus:" +statu[i]  ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);

                l1.setAdapter(ar);
                Custimage3 a=new Custimage3(this,product,quantity,amount,date,image,name,statu);
                l1.setAdapter(a);
            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}