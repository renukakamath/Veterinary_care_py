package com.example.patient_portal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class Viewproducts extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] product, amount, quantity, date, image, name,value,product_id,shop_id;
    public static String sid, pid, amt, stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewproducts);
        l1 = (ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewproducts.this;
        String q = "/Viewproducts";
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




                    value[i] ="product:" + product[i]+ "\nquantity: " + quantity[i] + "\n amount: " + amount[i] + "\ndate: " + date[i] +"\nname:" +name[i]   ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);

                l1.setAdapter(ar);
                Custimage2 a=new Custimage2(this,product,quantity,amount,date,image,name);
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

        sid=shop_id[i];
        pid=product_id[i];
        amt=amount[i];
        final CharSequence[] items = {"Make Order","View bookings", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewproducts.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Make Order")) {

                    JsonReq JR1=new JsonReq();
                    JR1.json_response=(JsonResponse) Viewproducts.this;
                    String q1 = "/productbooking?login_id="+Login.logid+"&sid="+sid +"&pid="+pid +"&amt="+amt ;
                    q1=q1.replace(" ","%20");
                    JR1.execute(q1);
                    Toast.makeText(getApplicationContext(), " Bookings successfully!!", Toast.LENGTH_LONG).show();






                } else if (items[item].equals("View bookings")) {

                     startActivity(new Intent(getApplicationContext(),Productbooking.class));
                }


                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();

    }
}