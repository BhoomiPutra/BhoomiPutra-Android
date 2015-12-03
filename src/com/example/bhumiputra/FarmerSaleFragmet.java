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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tool_dto.Manpower;
import tool_dto.ToolAddress;
import tool_dto.Tools;
import tool_dto.ToolsProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import dao.FarmerParser;
import dao.ToolParser;
import farmer_dto.Farmer;

public class FarmerSaleFragmet extends Fragment {
	String[] sellarray;
	Spinner spinnerProductName,spinnerAddress;
	Spinner sellSpinner2;
	Button btndisplay;
	ListView sellerlist;
	ArrayAdapter<String> listAdapter;

	ArrayList<String> arrayListState=new ArrayList<String>();
	ArrayAdapter<String> sellAdapter;
	ArrayList< String> arrayDisplay=new ArrayList<String>();
	String[] buyerCropArray={"Paddy","Wheat","Vegetable","Pulse"};

	Farmer farmer=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView=inflater.inflate(R.layout.farmer_sale_fragment, container,false);
		sellarray=getResources().getStringArray(R.array.item_array);
		spinnerProductName=(Spinner) rootView.findViewById(R.id.spinner1);
		spinnerAddress=(Spinner) rootView.findViewById(R.id.spinner2);
		btndisplay=(Button) rootView.findViewById(R.id.button1);
		sellerlist=(ListView) rootView.findViewById(R.id.listView1);
			
		sellAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.item_array));
		spinnerProductName.setAdapter(sellAdapter);
		//buylist.setAdapter(buyAdapter);
		
		
		
		SharedPreferences sp=getActivity().getSharedPreferences("settings",getActivity().MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		
		String json =sp.getString("farmer", null);
		farmer =FarmerParser.parseFarmer(json);
		
		arrayListState.add(farmer.getAddr().getState());
		arrayListState.add(farmer.getAddr().getDistrict());
		arrayListState.add(farmer.getAddr().getVillage());
		
		
		
		spinnerAddress.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayListState));
		editor.commit();
		
	
		btndisplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String productName=buyerCropArray[spinnerProductName.getSelectedItemPosition()];
				String location=spinnerAddress.getSelectedItem().toString();
				String data=productName+"  "+location;
				arrayDisplay.add(data);
			
				
				
				
				VendorBuyerStateTask buyerStateTask=new VendorBuyerStateTask();
				
				
				switch (spinnerAddress.getSelectedItemPosition()) {
				case 0:
					
					String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/getBuyerBasedOnStateServlet";
					buyerStateTask.execute(registerUrl,location,productName);
					
					
					break;
				case 1:
					
					
					String registerUrl2="http://192.168.76.37:9292/BhumiPutraServer/getBuyerBasedOnDistrictServlet";
					buyerStateTask.execute(registerUrl2,location,productName);
					
					
					break;
					
				case 2:
					
					
					String registerUrl3="http://192.168.76.37:9292/BhumiPutraServer/getBuyerBasedOnVillageServlet";
					buyerStateTask.execute(registerUrl3,location,productName);
					
					
					break;
					
				default:
					break;
				}
				
			}
		});
		
		
		return rootView;
		
	
		
	}

	
	
	
	
	
	
class VendorBuyerStateTask extends AsyncTask<String, Void, String>
{

		@Override
		protected String doInBackground(String... params) {
			
			String registerUrl=params[0];
			String location=params[1];
			String cropName=params[2];
			
			//Log.e("image path", img);

			//send values using post method
			HttpPost postRequest=new HttpPost(registerUrl);
			
			//set parameter in post request
			BasicNameValuePair pair1=new BasicNameValuePair("state", location);
		
			BasicNameValuePair pair2=new BasicNameValuePair("cropName", cropName);
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
			Log.e("result of sel fragmen",""+rst);
			
			
			ArrayList<String> arrayListToolProvider=new ArrayList<String>();
			
			if(rst.equals("0"))
			{
			
				Toast.makeText(getActivity(), "Something went wrong !!", Toast.LENGTH_LONG).show();
				
			}
			else 
			{	
				try {
					JSONArray toolJsonArray=new JSONArray(rst);
					
					for (int i=0;i<toolJsonArray.length();i++)
					{
						
						String provider=paresetool(toolJsonArray.getJSONObject(i));
						Log.e("toolprovider", provider.toString());
					
						arrayListToolProvider.add(provider);
					
					}
					
					listAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayListToolProvider);
					sellerlist.setAdapter(listAdapter);
					listAdapter.notifyDataSetChanged();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//launch next activity
			
			}
		
		
	}
	
	
	
	
		public String paresetool(JSONObject  Jobject)
		{
			String name;
			long mobileno;
			
			String data=null;
		
		try{
			name = Jobject.getString("name");
			mobileno= Jobject.getLong("mobileno");
			 String id=Jobject.getString("password");
		
			  data="Name-"+name+"   +91"+mobileno;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

			return data;
		}
		
	}
	}
		

