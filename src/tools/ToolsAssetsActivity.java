package tools;


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

import tool_dto.Tools;
import tool_dto.ToolsProvider;
import tools.ToolManpowerAssetActivity.RegisterTask;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bhumiputra.AssetsActivity;
import com.example.bhumiputra.FarmerHomeActivity;
import com.example.bhumiputra.FarmerLoginActivity;
import com.example.bhumiputra.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ToolsAssetsActivity extends Activity
{

	Spinner spinnerTools;
	EditText etToolRate,etToolCount;
	Button btnAddTools,btnSubmit;
	
	//ArrayList<String>listToolName=new ArrayList<String>();
	ArrayAdapter<String>adapterToolName;
	
	
	String[] toolName={"Cultivator","Tractor","Rotary Cultivator","Animal Drawn Cultivator","Bottom Plough","Chisel Plough","Reversible Plough","Disc Harrow","Puddler","Groundnut Thresher","Combine Harvester","Paddy Harvester","Sugar Cane Harvester","Wheat Harvester","Potato Digger","Seed Driller","Potato Planterr","Hand Sprayer","Rocker Sprayer","Mini Power Sprayer"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools_assets_activity);
		
		etToolRate=(EditText)findViewById(R.id.editText1);
		etToolCount=(EditText)findViewById(R.id.editText2);
		btnAddTools=(Button)findViewById(R.id.button1);
		btnSubmit=(Button)findViewById(R.id.button2);
		spinnerTools=(Spinner)findViewById(R.id.spinner1);
		//adapterToolName=new ArrayAdapter<String>(Tools_Assets.this, android.R.layout.simple_list_item_1,toolName);
		//spinnerTools.setAdapter(adapterToolName);
		
		adapterToolName=new ArrayAdapter<String>(ToolsAssetsActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tools_assets_array));
		spinnerTools.setAdapter(adapterToolName);
		
		Intent intent=getIntent();
		
		 final ToolsProvider toolsProvider=(ToolsProvider)intent.getSerializableExtra("toolsProvider");	
		
		 
		 final String status=intent.getStringExtra("manpower");
		 
			
		 Toast.makeText(ToolsAssetsActivity.this, toolsProvider.toString(), Toast.LENGTH_LONG).show();
		
		btnAddTools.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				String toolname=toolName[spinnerTools.getSelectedItemPosition()];
				double rate=Double.parseDouble(etToolRate.getText().toString());
				int count=Integer.parseInt(etToolCount.getText().toString());
				
				Tools tool=new Tools(toolname, rate, count);
				
				  toolsProvider.setTools(tool);
	                etToolCount.setText("");
				
			}
		});
		
		
btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(status.equals("true"))
				{
					Intent intent2=new Intent(ToolsAssetsActivity.this,ToolManpowerAssetActivity.class);
					intent2.putExtra("toolsProvider", toolsProvider);
					startActivity(intent2);
				}
				else
				{
 				Toast.makeText(ToolsAssetsActivity.this, toolsProvider.toString(), Toast.LENGTH_LONG).show();
 				Gson gson =new  GsonBuilder().create();
 				String json=gson.toJson(toolsProvider);
 				Toast.makeText(ToolsAssetsActivity.this,json, Toast.LENGTH_LONG).show();
 				String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/ToolRegisterServlet";
 				RegisterTask task=new RegisterTask();
 				
 				
 				String	_path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();  

 				Log.e("path",_path);  
 				_path= _path + "/" + "temp"+".jpg";  
 				
 				task.execute(json,registerUrl,_path);
				}
				
			}
		});
		
		
	}
	
	
	
	class RegisterTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			
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
			String temp="0";
			try {
			
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(listParams);
				
				postRequest.setEntity(entity);
				
				//send req to the server
				HttpClient client=new DefaultHttpClient();
				HttpResponse response=client.execute(postRequest);		
				InputStream input=response.getEntity().getContent();
				InputStreamReader read=new InputStreamReader(input);
				BufferedReader br=new BufferedReader(read);
				
				temp=br.readLine();
				
			//	int result=Integer.parseInt(temp);
		//		Log.e("RESULT-------->", "he -------"+temp);
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
		protected void onPostExecute(String rst) {
			// TODO Auto-generated method stub
			super.onPostExecute(rst);

			
			if(rst.equals("0"))
			{
			
				Toast.makeText(ToolsAssetsActivity.this, "Something went wrong !!", Toast.LENGTH_LONG).show();
				
			}
			else 
			{	
			Toast.makeText(ToolsAssetsActivity.this,"id"+rst, Toast.LENGTH_LONG).show();
			Intent intent=new Intent(ToolsAssetsActivity.this,ToolsManpowerLoginActivity.class);
			 startActivity(intent);
				
			}
		}
		

	
	}

	
}
