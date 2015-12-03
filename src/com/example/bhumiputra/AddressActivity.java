package com.example.bhumiputra;

import java.util.ArrayList;

import farmer_dto.Address;
import farmer_dto.Farmer;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddressActivity extends Activity {
	
	
	EditText etdistrict,ettehsil,etvillage,ethouseno,etpincode;

	Spinner spinnerstate;
	Button btncontinue;
	

	ArrayAdapter<String> adapterstates;
	
	String[] stateName={"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Puducherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"};
	
	  String imgDecodableString;
	  private static int RESULT_LOAD_IMG = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register2);
		
	etdistrict=(EditText)findViewById(R.id.editText1);
	ettehsil=(EditText)findViewById(R.id.editText2);
	etvillage=(EditText)findViewById(R.id.editText3);
	ethouseno=(EditText)findViewById(R.id.editText4);
	etpincode=(EditText)findViewById(R.id.editText5);
		
		btncontinue=(Button)findViewById(R.id.button2);
		spinnerstate=(Spinner)findViewById(R.id.spinner1);
		
		
		

		adapterstates=new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.state_array));
		spinnerstate.setAdapter(adapterstates);
		
		
		Intent intent=getIntent();
		final Farmer farmer=(Farmer) intent.getSerializableExtra("farmer");

		btncontinue.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				
				String district=etdistrict.getText().toString();
				String tehsil=ettehsil.getText().toString();
				String village=etvillage.getText().toString();
				String housenoAndBlock=ethouseno.getText().toString();
				int pincode=Integer.parseInt(etpincode.getText().toString());
				String state=stateName[ spinnerstate.getSelectedItemPosition()];
				
				Address address=new Address(village,housenoAndBlock, tehsil, district, state, pincode);
				
				farmer.setAddr(address);
				Intent intent=new Intent(AddressActivity.this, AssetsActivity.class);
		
			
				intent.putExtra("farmer", farmer);
		
				startActivity(intent);
				//Toast.makeText(RegisterActivity.this, name, Toast.LENGTH_LONG).show();
				
				
				
			}
		});
		
		
		
	}	
		
		
		
		
	
	
	


}
