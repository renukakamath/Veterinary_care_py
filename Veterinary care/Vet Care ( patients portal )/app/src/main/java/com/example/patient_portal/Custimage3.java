package com.example.patient_portal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custimage3 extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;

	private String[] product;
	private String[] quantity;
	private String[] amount;
	private String[] date;

	private String[] image;
	private String[] name;
	private String[] status;


	 public Custimage3(Activity context, String[] product, String[] quantity, String[] date, String[] amount , String[] image, String[] name, String[] status ) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_images,image);
	        this.context = context;

		    this.product = product;
		 	this.quantity = quantity;
		    this.amount = amount;
			 this.date = date;
			 this.image = image;
		     this.name = name;
		 this.status = status;



	 }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.cust_images, null, true);
			//cust_list_view is xml file of layout created in step no.2

	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.textView3);

			TextView t2=(TextView)listViewItem.findViewById(R.id.textView3);
			t1.setText("product : "+product[position]+"\nquantity : "+quantity[position]+"\namount:"+amount[position]+"\ndate:"+date[position]+"\nname:"+name[position]+"\nstatus:"+status[position]);
//			t2.setText(caption[position]);
	        sh=PreferenceManager.getDefaultSharedPreferences(getContext());
	        
	       String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
	       pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
	        
	        Log.d("-------------", pth);
	        Picasso.with(context)
	                .load(pth)
	                .placeholder(R.drawable.ic_launcher_background)
	                .error(R.drawable.ic_launcher_background).into(im);
	        
	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}