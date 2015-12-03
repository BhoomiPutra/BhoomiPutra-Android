package com.example.bhumiputra;

import java.io.BufferedReader;
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

import com.example.bhumiputra.FarmerSaleFragmet.VendorBuyerStateTask;

import dao.FarmerParser;
import farmer_dto.Farmer;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class FarmerHireFragmet extends Fragment {
	
	//String[] hirearray;
	//String[] hirearray1;
	Spinner hirespinner,hiresItemType,spinnerAddress;
	Spinner hirespinner2;
	 String itemName;
	ArrayAdapter<String> hireAdapter,hireAdapter1,adapterAddress;
	Button btndisplay;
	ListView buylist;
	ArrayAdapter<String> listAdapter;
	Farmer farmer=null;
	ArrayList<String> arrayListState=new ArrayList<String>();
	ArrayList< String> arrayDisplay=new ArrayList<String>();
	
	int j=12123;
	String[] toolManpowerArray1;
	String[] toolManpowerArray={"Tools","Manpower"};
	String[] toolArray={"Cultivator","Tractor","Rotary Cultivator","Animal Drawn Cultivator","Bottom Plough","Chisel Plough","Reversible Plough","Disc Harrow","Puddler","Groundnut Thresher","Combine Harvester","Paddy Harvester","Sugar Cane Harvester","Wheat Harvester","Potato Digger","Seed Driller","Potato Planterr","Hand Sprayer","Rocker Sprayer","Mini Power Sprayer"};
	String[] manpowerArray={"Agricultural Equipments Operature","Harvester Machine operature","Grain Farm Worker","Dairy Farm Worker","Poultry Farm Worker","Vegetable Farm Worker"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		SharedPreferences sp=getActivity().getSharedPreferences("settings",getActivity().MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		
		String json =sp.getString("farmer", null);
		farmer =FarmerParser.parseFarmer(json);
		
		editor.commit();
		
		arrayListState.add(farmer.getAddr().getState());
		arrayListState.add(farmer.getAddr().getDistrict());
		arrayListState.add(farmer.getAddr().getVillage());
		View rootView=inflater.inflate(R.layout.farmer_hire_fragment, container,false);
		
		hirespinner=(Spinner) rootView.findViewById(R.id.spinner1);
		hiresItemType=(Spinner) rootView.findViewById(R.id.spinner2);
		spinnerAddress=(Spinner) rootView.findViewById(R.id.spinner3);
		btndisplay=(Button) rootView.findViewById(R.id.button1);
		buylist=(ListView) rootView.findViewById(R.id.listView1);
		
		hireAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tool_manpower_hire_array));
		hirespinner.setAdapter(hireAdapter);
		//hireAdapter1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tools_assets_array));
		//hirespinner1.setAdapter(hireAdapter1);
	
		
		
		adapterAddress=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayListState);
		spinnerAddress.setAdapter(adapterAddress);
		
		
		listAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayDisplay);
		buylist.setAdapter(listAdapter);
		
		hirespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
		
				switch (arg2) {
				case 0:
					hireAdapter1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tools_assets_array));
					
					j=0;
					break;
					case 1:
						hireAdapter1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.manpower_array));
						j=1;
						break;
				default:
					break;
				}
				hiresItemType.setAdapter(hireAdapter1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	btndisplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				String chooseField=toolManpowerArray[hirespinner.getSelectedItemPosition()];
				if(j==0)
				{
				 itemName=toolArray[hiresItemType.getSelectedItemPosition()];
				}
				else
				{
					 itemName=manpowerArray[hiresItemType.getSelectedItemPosition()];
				}
				String statename=spinnerAddress.getSelectedItem().toString();
				String data=chooseField+"    "+itemName+"   "+statename;
				arrayDisplay.add(data);
				
				HireToolsManpowerStateTask buyerStateTask=new HireToolsManpowerStateTask();
				if(j==1)
				{
					
					
					switch (spinnerAddress.getSelectedItemPosition()) {
					case 0:
						
						String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/getManpowerBasedOnStateServlet";
						buyerStateTask.execute(registerUrl,statename,itemName);
						
						break;
					case 1:
						
						String registerUrl2="http://192.168.76.37:9292/BhumiPutraServer/getManpowerBasedOnDistrictServlet";
						buyerStateTask.execute(registerUrl2,statename,itemName);
						break;
						
					case 2:
						
						String registerUrl3="http://192.168.76.37:9292/BhumiPutraServer/getManpowerBasedOnVillageServlet";
						buyerStateTask.execute(registerUrl3,statename,itemName);
						break;
					}
					
					
				}
				else
				{
					switch (spinnerAddress.getSelectedItemPosition()) {
					case 0:
						
						String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/getToolBasedOnStateServlet";
						buyerStateTask.execute(registerUrl,statename,itemName);
						break;
					case 1:
						String registerUrl2="http://192.168.76.37:9292/BhumiPutraServer/getToolBasedOnDistrictServlet";
						buyerStateTask.execute(registerUrl2,statename,itemName);
						break;
						
					case 2:
						String registerUrl3="http://192.168.76.37:9292/BhumiPutraServer/getToolBasedOnVillageServlet";
						buyerStateTask.execute(registerUrl3,statename,itemName);
						break;
					
					}
					
				
				
				
				
				}
			}
		});
		
	
			return rootView;
	}
	

	
	
class HireToolsManpowerStateTask extends AsyncTask<String, Void, String>
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
					buylist.setAdapter(listAdapter);
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
	
			
		
			

		
