package vendor;

import java.util.ArrayList;

import tools.ToolAddressActivity;
import vendor_dto.Vendor;
import vendor_dto.VendorAddress;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bhumiputra.R;


public class VendorAddressActivity extends Activity {
	
	
	EditText etdistrict,ettehsil,etvillage,ethouseno,etpincode;
	RadioGroup rgchooseOption;
	Spinner spinnerstate;
	Button btncontinue;
	
	String[] stateName={"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Puducherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"};
	ArrayAdapter<String> adapterstates;
	
	  String imgDecodableString;
	  private static int RESULT_LOAD_IMG = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendoraddressregister);
		
	etdistrict=(EditText)findViewById(R.id.editText1);
	ettehsil=(EditText)findViewById(R.id.editText2);
	etvillage=(EditText)findViewById(R.id.editText3);
	ethouseno=(EditText)findViewById(R.id.editText4);
	etpincode=(EditText)findViewById(R.id.editText5);
	rgchooseOption=(RadioGroup)findViewById(R.id.radioGroup1);
		
		btncontinue=(Button)findViewById(R.id.button1);
		spinnerstate=(Spinner)findViewById(R.id.spinner1);
		
		
		

		//adapterstates=new ArrayAdapter<String>(VendorAddressActivity.this, android.R.layout.simple_list_item_1,stateName);
		//spinnerstate.setAdapter(adapterstates);
		adapterstates=new ArrayAdapter<String>(VendorAddressActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.state_array));
		spinnerstate.setAdapter(adapterstates);
		
		Intent intent=getIntent();
		final Vendor vendor=(Vendor) intent.getSerializableExtra("vendor");

		btncontinue.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				
				String district=etdistrict.getText().toString();
				String tehsil=ettehsil.getText().toString();
				String village=etvillage.getText().toString();
				String housenoAndBlock=ethouseno.getText().toString();
				long pincode=Long.parseLong(etpincode.getText().toString());
				String state=stateName[ spinnerstate.getSelectedItemPosition()];
			
				
				VendorAddress address=new VendorAddress(village,housenoAndBlock, tehsil, district, state, pincode);
				vendor.setAddr(address);
				Toast.makeText(VendorAddressActivity.this, address.toString(), Toast.LENGTH_LONG).show();
				int id = rgchooseOption.getCheckedRadioButtonId();
				
				//vendor.setAddr(address);
				//Intent intent=new Intent(VendorAddressActivity.this, VendorSellerAssetsActivity.class);

switch (id) {
				
				case R.id.radioButton1:
				
					
					Intent intent=new Intent(VendorAddressActivity.this, VendorSellerAssetsActivity.class);
					intent.putExtra("buyer","false");
					intent.putExtra("vendor", vendor);
					startActivity(intent);
					break;
				case R.id.radioButton2:
					
					
					
					Intent intent1=new Intent(VendorAddressActivity.this,VendorBuyerAssetsActivity.class);
			
				
					intent1.putExtra("vendor", vendor);
			
					startActivity(intent1);
					
					break;
					case R.id.radioButton3:
						
					Intent intent3=new Intent(VendorAddressActivity.this,VendorSellerAssetsActivity.class);
					intent3.putExtra("vendor", vendor);
					intent3.putExtra("buyer","true");
					startActivity(intent3);
					
					break;
					
				}
				
			
				
				
				
			}
		});
		
		
		
	}	
		
		
		
		
	
	
	


}
