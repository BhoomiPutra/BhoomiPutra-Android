package com.example.bhumiputra;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import farmer_dto.Cattles;
import farmer_dto.Farmer;

public class AssetsActivity extends Activity {
	
	
	TextView tvformableland,tvcattle,tvchooseCattle;
	EditText etCount,etFarmableland;
	RadioGroup rgchooseOption;
	Button btnaddCattle,btnRegisterNow;
	Spinner spinnerCattle;
	
	//ArrayList<String>listCattles=new ArrayList<String>();
	ArrayAdapter<String> adapterCattles;
	String[] cattleName={"Cow","Ox","Buffalo","Goat","Sheep"};

	
//	ArrayList<Cattles> cattles=new ArrayList<Cattles>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assets);
		tvformableland=(TextView)findViewById(R.id.textView1);
		tvcattle=(TextView)findViewById(R.id.textView2);
		tvchooseCattle=(TextView)findViewById(R.id.textView3);
		etFarmableland=(EditText)findViewById(R.id.editText1);
		etCount=(EditText)findViewById(R.id.editText2);
		rgchooseOption=(RadioGroup)findViewById(R.id.radioGroup1);		
		btnaddCattle=(Button)findViewById(R.id.button1);
		btnRegisterNow=(Button)findViewById(R.id.button2);
		spinnerCattle=(Spinner)findViewById(R.id.spinner1);
		adapterCattles=new ArrayAdapter<String>(AssetsActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.cattle_array ));
		spinnerCattle.setAdapter(adapterCattles);
		
		//getting data from Register Activity
		Intent intent=getIntent();
		final	Farmer farmer=(Farmer) intent.getSerializableExtra("farmer");
		
	/*	int id = rgchooseOption.getCheckedRadioButtonId();
		switch (id) {
		case R.id.radioButton1:
			spinnerCattle.setVisibility(visibility); 
			
			break;

		default:
			break;
		}*/
		btnaddCattle.setOnClickListener(new OnClickListener() 
		{
			
			public void onClick(View v)
			{
				int count=Integer.parseInt(etCount.getText().toString());
				String cattlename=cattleName[spinnerCattle.getSelectedItemPosition()];
				Cattles		cattle=new Cattles(cattlename, count); 
				//	cattles.add(cattle);
				farmer.setCattle(cattle);
				etCount.setText("");
			
				
			}
		});
		
		
		
		
		btnRegisterNow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			farmer.setTotalLand(Double.parseDouble(etFarmableland.getText().toString()));
			Gson gson =new  GsonBuilder().create();
			String json=gson.toJson(farmer);
			Toast.makeText(AssetsActivity.this,json, Toast.LENGTH_LONG).show();
			String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/FarmerRegisterServlet";
			RegisterTask task=new RegisterTask();
		
		
			String	_path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();  

			Log.e("path",_path);  
			_path= _path + "/" + "temp"+".jpg";  
			 
		//	Bitmap bitmap = BitmapFactory.decodeFile(_path);  
	
			task.execute(json,registerUrl,_path);

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
			Log.e("Id",""+rst);
			
			if(rst.equals("0"))
			{
			
				Toast.makeText(AssetsActivity.this, "Something went wrong !!", Toast.LENGTH_LONG).show();
				
			}
			else 
			{	
				//launch next activity
			/*SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
			SharedPreferences.Editor editor=sp.edit();
			editor.putInt("f_id",Integer.parseInt(rst));
			editor.commit();*/
			Toast.makeText(AssetsActivity.this,"id"+rst, Toast.LENGTH_LONG).show();
			Intent intent=new Intent(AssetsActivity.this,FarmerLoginActivity.class);
			startActivity(intent);
				
			}
		}
		
	}
}


