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
import tools.ToolManpowerAssetActivity.RegisterTask;
import vendor_dto.VendorSeller;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bhumiputra.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UpdateManpowerFragmet extends Fragment {
	
	
	
	Spinner spinnerManpower;
	EditText etManpowerRate,etManpowerCount;
	ArrayAdapter<String>adapterManpower;
	Button btnUpdate;
	String[] manpowerCategory={"Agricultural Equipments Operature","Harvester Machine operature","Grain Farm Worker","Dairy Farm Worker","Poultry Farm Worker","Vegetable Farm Worker"};

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.tools_manpower, container,false);
		
		SharedPreferences sp=getActivity().getSharedPreferences("settings",getActivity().MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		
		final int id =sp.getInt("f_id", 0);
		
		Log.d("fid",""+id);
		
		
		etManpowerRate=(EditText) rootView.findViewById(R.id.editText1);
		etManpowerCount=(EditText) rootView.findViewById(R.id.editText2);
		spinnerManpower=(Spinner) rootView.findViewById(R.id.spinner1);
		btnUpdate=(Button)rootView.findViewById(R.id.button1);
		adapterManpower=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.manpower_array));
		spinnerManpower.setAdapter(adapterManpower);
		
		btnUpdate.setText("Update");
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				double manpowerrate=Double.parseDouble(etManpowerRate.getText().toString());
				int manpowerCount=Integer.parseInt(etManpowerCount.getText().toString());
				String manpowerCategory2=manpowerCategory[spinnerManpower.getSelectedItemPosition()];
				Manpower manpower=new Manpower(manpowerCategory2, manpowerrate, manpowerCount);
				
                etManpowerCount.setText("");
                
        
				Gson gson =new  GsonBuilder().create();
 				String json=gson.toJson(manpower);
 				Toast.makeText(getActivity(),json, Toast.LENGTH_LONG).show();
 				String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/UpdateVendorToolManpowerServlet";
 				UpdateTask task=new UpdateTask();
 			
 				task.execute(json,registerUrl,id+"");
				
				
				
			}
		});
		
		return rootView;
		
	}

	
	class UpdateTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			
			String json=params[0];
			String registerUrl=params[1];
			String imagepath=params[2];

			HttpPost postRequest=new HttpPost(registerUrl);
			
			//set parameter in post request
			BasicNameValuePair pair1=new BasicNameValuePair("json", json);
		
			BasicNameValuePair pair2=new BasicNameValuePair("id", imagepath);
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
			
				Toast.makeText(getActivity(), "Something went wrong !!", Toast.LENGTH_LONG).show();
				
			}
			else 
			{	
				
			Toast.makeText(getActivity(),"id"+rst, Toast.LENGTH_LONG).show();
			
				
			}
		}
		


	}


	}
