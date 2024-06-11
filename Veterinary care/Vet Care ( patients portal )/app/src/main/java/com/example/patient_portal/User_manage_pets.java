package com.example.patient_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class User_manage_pets extends AppCompatActivity implements JsonResponse{


    Button b1;
    EditText e1,e2,e3,e4,e5;
    ImageButton i1;

    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null;

    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_manage_pets);
        i1=(ImageButton)findViewById(R.id.ibimage);
        e1=(EditText)findViewById(R.id.etname);
        e2=(EditText)findViewById(R.id.ettype);
        e3=(EditText)findViewById(R.id.etbreed);
        e4=(EditText)findViewById(R.id.etage);
        e5=(EditText)findViewById(R.id.etothers);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }
        });

        b1 = (Button) findViewById(R.id.btupim);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(e1.getText().toString().equalsIgnoreCase("")){
                    e1.setError("Field is required");
                    e1.setFocusable(true);
                }else if(e2.getText().toString().equalsIgnoreCase("")){
                    e2.setError("Field is required");
                    e2.setFocusable(true);
                }else if(e3.getText().toString().equalsIgnoreCase("")){
                    e3.setError("Field is required");
                    e3.setFocusable(true);
                }else if(e4.getText().toString().equalsIgnoreCase("")){
                    e4.setError("Field is required");
                    e4.setFocusable(true);
                }else if(e5.getText().toString().equalsIgnoreCase("")){
                    e5.setError("Field is required");
                    e5.setFocusable(true);
                }else {







              sendAttach();
                }
            }
        });
    }

    /////////////////////////////////////////////


    private void sendAttach() {
        try {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String q = "http://" + sh.getString("ip", "") + "/api/User_manage_pets";

            Map<String, byte[]> aa = new HashMap<>();
            aa.put("image",byteArray);
            aa.put("pet_name",e1.getText().toString().getBytes());
            aa.put("type",e2.getText().toString().getBytes());
            aa.put("breed",e3.getText().toString().getBytes());
            aa.put("age",e4.getText().toString().getBytes());
            aa.put("others",e5.getText().toString().getBytes());
            aa.put("login_id",Login.logid.getBytes());


            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) User_manage_pets.this;
            fua.execute(aa);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImageOption() {

		/*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_manage_pets.this);
        builder.setTitle("Take Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
                path = FileUtils.getPath(this, uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                i1.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                i1.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void response(JSONObject jo) {

        try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "Result Found....\n Successfully Updated", Toast.LENGTH_LONG).show();
//		                JSONArray ja = (JSONArray) jo.getJSONArray("data");
//		                labels = ja.getJSONObject(0).getString("label");
//		                pre = ja.getJSONObject(0).getString("precatuions");



                //startActivity(new Intent(getApplicationContext(),Viewmodeldetails.class));
                startActivity(new Intent(getApplicationContext(),Users_home.class));

            }
            else if (status.equalsIgnoreCase("failed")) {

                Toast.makeText(getApplicationContext(),"Image Not Found", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),User_manage_pets.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Response Exc : " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    //////////////////////////////////////////////////////
}
