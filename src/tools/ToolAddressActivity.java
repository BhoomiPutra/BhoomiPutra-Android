package tools;

import tool_dto.ToolAddress;
import tool_dto.ToolsProvider;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.bhumiputra.R;

public class ToolAddressActivity extends Activity {
	
	
	EditText etdistrict,ettehsil,etvillage,ethouseno,etpincode;
  
	Spinner spinnerstate;
	Button btncontinue;
	RadioGroup rgchooseOption;
	//RadioButton rbtnTools,rbtnManpower,rbtnBoth;

	String toolAssets;
	//ArrayList<String>listStates=new ArrayList<String>();
	ArrayAdapter<String> adapterstates;
	String[] stateName={"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Puducherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"};
	
	
	  String imgDecodableString;
	  private static int RESULT_LOAD_IMG = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tooladdressregister);
		
	etdistrict=(EditText)findViewById(R.id.editText1);
	ettehsil=(EditText)findViewById(R.id.editText2);
	etvillage=(EditText)findViewById(R.id.editText3);
	ethouseno=(EditText)findViewById(R.id.editText4);
	etpincode=(EditText)findViewById(R.id.editText5);
		rgchooseOption=(RadioGroup)findViewById(R.id.radioGroup1);
		btncontinue=(Button)findViewById(R.id.button1);
		spinnerstate=(Spinner)findViewById(R.id.spinner1);
		
		

		adapterstates=new ArrayAdapter<String>(ToolAddressActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.state_array));
		spinnerstate.setAdapter(adapterstates);
		
		//rbtnTools=(RadioButton)findViewById(R.id.radioButton1);
		//rbtnManpower=(RadioButton)findViewById(R.id.radioButton2);
		//rbtnBoth=(RadioButton)findViewById(R.id.radioButton3);
		
		

		//adapterstates=new ArrayAdapter<String>(ToolAddressActivity.this, android.R.layout.simple_list_item_1,stateName);
		//spinnerstate.setAdapter(adapterstates);
		
		
		Intent intent=getIntent();
		final ToolsProvider toolsProvider=(ToolsProvider) intent.getSerializableExtra("toolsprovider");

		btncontinue.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				
				String district=etdistrict.getText().toString();
				String tehsil=ettehsil.getText().toString();
				String village=etvillage.getText().toString();
				String housenoAndBlock=ethouseno.getText().toString();
				int pincode=Integer.parseInt(etpincode.getText().toString());
				String state=stateName[spinnerstate.getSelectedItemPosition()];
				
				ToolAddress address=new ToolAddress(village,housenoAndBlock, tehsil, district, state, pincode);
				
				int id = rgchooseOption.getCheckedRadioButtonId();
				
				toolsProvider.setAddr(address);
				Log.e("State",state);
				switch (id) {
				
				case R.id.radioButton1:
				
					
					Intent intent=new Intent(ToolAddressActivity.this, ToolsAssetsActivity.class);
					intent.putExtra("manpower","false");
					intent.putExtra("toolsProvider", toolsProvider);
					startActivity(intent);
					break;
				case R.id.radioButton2:
					
					
					
					Intent intent2=new Intent(ToolAddressActivity.this,ToolManpowerAssetActivity.class);
			
				
					intent2.putExtra("toolsProvider", toolsProvider);
			
					startActivity(intent2);
					
					break;
					case R.id.radioButton3:
						
					Intent intent3=new Intent(ToolAddressActivity.this,ToolsAssetsActivity.class);
					intent3.putExtra("toolsProvider", toolsProvider);
					intent3.putExtra("manpower","true");
					startActivity(intent3);
					
					break;
					
				}
				
			
				
				
			}
		});
		
		
		
	}	
		
		
		
		
	
	
	


}
