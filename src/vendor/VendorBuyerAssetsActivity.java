package vendor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import tool_dto.ToolsProvider;
import tools.ToolManpowerAssetActivity;
import tools.ToolsAssetsActivity;
import tools.ToolsManpowerHomeActivity;
import tools.ToolsManpowerLoginActivity;
import vendor_dto.Vendor;
import vendor_dto.VendorBuyer;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.bhumiputra.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class VendorBuyerAssetsActivity extends Activity {
	
	

TextView tvSelect;
Spinner spinnerVendor,spinnerItem;
EditText etPrice;
Button btnAdd,btnSubmit;
RadioButton rbbuyer,rbSeller,rbboth;

ArrayAdapter<String> adapterbuyer;
ArrayAdapter<String> adapterItem;
int j=12123;
String[] buyerCropArray1;

String[] buyerCropArray={"Paddy","Wheat","Vegetable","Pulse"};
String[] buyerPaddyArray={"Basmati","Rajlaxmi","Sonam","404Hybrid"};
String[] buyerWheatArray={"Amrok","Babbler","Beafort","Camm"};
String[] buyerVegetableArray={"Cowpea","Fieldpea","Gardenpea","Pak Choi","King Slaw"};
String[] buyerPulseArray={"Broade Bean","Bengal Gram","Black Bean","Lima Bean"};

@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendore_assets_activity);
		
		
		etPrice=(EditText)findViewById(R.id.editText2);
		
		btnAdd=(Button)findViewById(R.id.button1);
		btnSubmit=(Button)findViewById(R.id.button2);
		spinnerVendor=(Spinner)findViewById(R.id.spinner1);
		spinnerItem=(Spinner)findViewById(R.id.spinner2);
		
		adapterbuyer=new ArrayAdapter<String>(VendorBuyerAssetsActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.buyercrop_array ));
		spinnerVendor.setAdapter(adapterbuyer);
		
		Intent intent=getIntent();
		
		 final Vendor vendor=(Vendor)intent.getSerializableExtra("vendor");	
		
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				String cropType=buyerCropArray[spinnerVendor.getSelectedItemPosition()];
				String cropname = null;
			switch (j) {
			case 0:
				cropname=buyerPaddyArray[spinnerItem.getSelectedItemPosition()];
				
				break;
			case 1:
			 cropname=buyerWheatArray[spinnerItem.getSelectedItemPosition()];
				
				break;
				case 2:
				 cropname=buyerVegetableArray[spinnerItem.getSelectedItemPosition()];
					
					break;
					
				case 3:
						 cropname=buyerPulseArray[spinnerItem.getSelectedItemPosition()];
						
						break;
			default:
				break;
			}
				
				
				
			
				double cropprice=Double.parseDouble(etPrice.getText().toString());
			
				
				VendorBuyer buyer=new VendorBuyer(cropType, cropname, cropprice);
				
				
				 vendor.setBuyer(buyer);
				 
				 etPrice.setText("");
				 
				 
	      
				
			}
		});
		
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

 				Toast.makeText(VendorBuyerAssetsActivity.this, vendor.toString(), Toast.LENGTH_LONG).show();
				
 				Gson gson =new  GsonBuilder().create();
 				String json=gson.toJson(vendor);
 				Toast.makeText(VendorBuyerAssetsActivity.this,json, Toast.LENGTH_LONG).show();
 				String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/VendorRegisterServlet";
 				RegisterTask task=new RegisterTask();
 				
 				
 				
 				Log.e("hello", json);

				String _path = Environment.getExternalStorageDirectory().getAbsolutePath().toString(); 

 				Log.e("path",_path);  
 				_path= _path + "/" + "temp"+".jpg";  
 				
 				task.execute(json,registerUrl,_path);
 				
 				
 				
			}
		});
		
		
		spinnerVendor.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			
				
				switch (arg2) {
				case 0:
					adapterItem=new ArrayAdapter<String>(VendorBuyerAssetsActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.buyercrop_paddy_array));
					//etRate.setHint(R.string.ratekg);
		
					j=0;
					break;
				case 1:
					adapterItem=new ArrayAdapter<String>(VendorBuyerAssetsActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.buyercrop_wheat_array));
					//etRate.setHint(R.string.ratekg);
					j=1;
					break;
				case 2:
					
					adapterItem=new ArrayAdapter<String>(VendorBuyerAssetsActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.buyercrop_vegetable_array));
					//etRate.setHint(R.string.ratekg);
					j=2;
					break;
				case 3:
					adapterItem=new ArrayAdapter<String>(VendorBuyerAssetsActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.buyercrop_pulse_array));
					j=3;
					//etRate.setHint(R.string.rate);
					
					break;
				

				default:
					break;
				}
				
				spinnerItem.setAdapter(adapterItem);
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
	}




class RegisterTask extends AsyncTask<String, Void, Integer>
{

	@Override
	protected Integer doInBackground(String... params) {
		
		String json=params[0];
		String registerUrl=params[1];
		String imagepath=params[2];
		Bitmap btmap=BitmapFactory.decodeFile(imagepath);
		ByteArrayOutputStream bt=new ByteArrayOutputStream();
		btmap.compress(Bitmap.CompressFormat.JPEG, 100, bt);
		byte[] btArray=bt.toByteArray();
		String img=Base64.encodeToString(btArray, Base64.DEFAULT);
		//Log.e("image path", img);

		//send values using post method
		HttpPost postRequest=new HttpPost(registerUrl);
		
		//set parameter in post request
		BasicNameValuePair pair1=new BasicNameValuePair("json", json);
	
		BasicNameValuePair pair2=new BasicNameValuePair("image", img);
		ArrayList<BasicNameValuePair> listParams=new ArrayList<BasicNameValuePair>();
		listParams.add(pair1);
		listParams.add(pair2);
		//int	result1 = 0;
		Integer temp=0;
		try {
		
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(listParams);
			
			postRequest.setEntity(entity);
			
			//send req to the server
			HttpClient client=new DefaultHttpClient();
			HttpResponse response=client.execute(postRequest);		
			InputStream input=response.getEntity().getContent();
			InputStreamReader read=new InputStreamReader(input);
			BufferedReader br=new BufferedReader(read);
			
			temp=br.read();
			
		//	int result=Integer.parseInt(temp);
			Log.e("RESULT-------->", "temp -------"+temp);
			br.close();
			return temp;
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
	}//eof of doInBack
	@Override
	protected void onPostExecute(Integer rst) {
		// TODO Auto-generated method stub
		super.onPostExecute(rst);

		
		if(rst==0)
		{
		
			Toast.makeText(VendorBuyerAssetsActivity.this, "Something went wrong !!", Toast.LENGTH_LONG).show();
			
		}
		else 
		{	
		
		Toast.makeText(VendorBuyerAssetsActivity.this,"id"+rst, Toast.LENGTH_LONG).show();
		Intent intent=new Intent(VendorBuyerAssetsActivity.this,VendorLoginActivity.class);
		 startActivity(intent);
			
		}
	}
	


}




}
