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

import tool_dto.Manpower;
import tool_dto.ToolsProvider;
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


public class ToolManpowerAssetActivity extends Activity
{
Spinner spinnerManpower;
EditText etManpowerRate,etManpowerCount;
Button btnAddManpower,btnSubmit;
ArrayList<String>listManpowerCategory=new ArrayList<String>();
ArrayAdapter<String>adapterManpower;
String[] manpowerCategory={"Agricultural Equipments Operature","Harvester Machine operature","Grain Farm Worker","Dairy Farm Worker","Poultry Farm Worker","Vegetable Farm Worker"};

@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools_manpower_activity);
		etManpowerRate=(EditText)findViewById(R.id.editText1);
		etManpowerCount=(EditText)findViewById(R.id.editText2);
		btnAddManpower=(Button)findViewById(R.id.button1);
		btnSubmit=(Button)findViewById(R.id.button2);
		spinnerManpower=(Spinner)findViewById(R.id.spinner1);
		adapterManpower=new ArrayAdapter<String>(ToolManpowerAssetActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.manpower_array));
		spinnerManpower.setAdapter(adapterManpower);
		Intent intent=getIntent();
		final ToolsProvider toolsProvider=(ToolsProvider)intent.getSerializableExtra("toolsProvider");	
		 Toast.makeText(ToolManpowerAssetActivity.this, toolsProvider.toString(), Toast.LENGTH_LONG).show();
		 btnAddManpower.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				double manpowerrate=Double.parseDouble(etManpowerRate.getText().toString());
				int manpowerCount=Integer.parseInt(etManpowerCount.getText().toString());
				String manpowerCategory2=manpowerCategory[spinnerManpower.getSelectedItemPosition()];
				Manpower manpower=new Manpower(manpowerCategory2, manpowerrate, manpowerCount);
				toolsProvider.setManpowers(manpower);
                etManpowerCount.setText("");
            }
		});
		 btnSubmit.setOnClickListener(new OnClickListener() {
		 			@Override
		 			public void onClick(View v) {
		 				//Toast.makeText(ToolManpowerAssetActivity.this, toolsProvider.toString(), Toast.LENGTH_LONG).show();
		 				Gson gson =new  GsonBuilder().create();
		 				String json=gson.toJson(toolsProvider);
		 				Toast.makeText(ToolManpowerAssetActivity.this,json, Toast.LENGTH_LONG).show();
		 				String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/ToolRegisterServlet";
		 				RegisterTask task=new RegisterTask();
		 				String	_path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();  
		 				Log.e("path in tool manpower",_path);  
		 				_path= _path + "/" + "temp"+".jpg";  
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
		Log.e("result manpower tool", rst);
		
		if(rst.equals("0"))
		{
		
			Toast.makeText(ToolManpowerAssetActivity.this, "Something went wrong !!", Toast.LENGTH_LONG).show();
			
		}
		else 
		{	
		
		Toast.makeText(ToolManpowerAssetActivity.this,"id"+rst, Toast.LENGTH_LONG).show();
		Intent intent=new Intent(ToolManpowerAssetActivity.this,ToolsManpowerLoginActivity.class);
		 startActivity(intent);
			
		}
	}
	
}




}
