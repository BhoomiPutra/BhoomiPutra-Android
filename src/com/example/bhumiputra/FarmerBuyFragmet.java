package com.example.bhumiputra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
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

import tool_dto.ToolsProvider;

import com.example.bhumiputra.FarmerSaleFragmet.VendorBuyerStateTask;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import dao.FarmerParser;
import farmer_dto.Farmer;

public class FarmerBuyFragmet extends Fragment {
	String[] buyarray;
	Spinner spinnerProductType;
	Spinner spinnerProductName;
	Spinner buyspinner2;
	ArrayAdapter<String> buyAdapter;
	ArrayAdapter<String> buyAdapter1,adapterAddress;
	ArrayAdapter<String> listAdapter;
	Button btndisplay;
	ListView buylist;
	
	String productName;
	int j=12123;
	Farmer farmer=null;
	ArrayList<String> arrayListState=new ArrayList<String>();
	ArrayList< String> arrayDisplay=new ArrayList<String>();
	String[] vendorArray={"Seed","Manure","Pesticides","Tools"};
	String[] buyerCropArray={"Paddy","Wheat","Vegetable","Pulse"};
     String[] itemManureArray={"Uria","Potash","Sulphur"};
	String[] itemPesticidesArray={"DDT","Nephthol","Diclofop"};
	String[] itemToolArray={"Cultivator","Tractor","Rotary Cultivator","Animal Drawn Cultivator","Bottom Plough","Chisel Plough","Reversible Plough","Disc Harrow","Puddler","Groundnut Threshe","Combine Harvester","Paddy Harvester","Sugar Cane Harvester","Wheat Harvester","Potato Digger","Seed Driller","Potato Planter","Hand Sprayer","Rocker Sprayer","Mini Power Sprayer"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		 
			SharedPreferences sp=getActivity().getSharedPreferences("settings",getActivity().MODE_PRIVATE);
			SharedPreferences.Editor editor=sp.edit();
			
			String json =sp.getString("farmer", null);
			farmer =FarmerParser.parseFarmer(json);
			
			editor.commit();
			
			arrayListState.add(farmer.getAddr().getState());
			arrayListState.add(farmer.getAddr().getDistrict());
			arrayListState.add(farmer.getAddr().getVillage());
			
			
		

		
		View rootView=inflater.inflate(R.layout.farmer_buy_fragment, container,false);
		buyarray=getResources().getStringArray(R.array.vendor_array);
		spinnerProductType=(Spinner) rootView.findViewById(R.id.spinner1);
		spinnerProductName=(Spinner) rootView.findViewById(R.id.spinner2);
		buyspinner2=(Spinner) rootView.findViewById(R.id.spinner3);
		btndisplay=(Button) rootView.findViewById(R.id.button1);
		buylist=(ListView) rootView.findViewById(R.id.listView1);
		
		adapterAddress=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayListState);
		buyspinner2.setAdapter(adapterAddress);
		
		buyAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.vendor_array));
	spinnerProductType.setAdapter(buyAdapter);
		//buylist.setAdapter(buyAdapter);
	
	
	listAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayDisplay);
	buylist.setAdapter(listAdapter);
	
		
		spinnerProductType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) { 
				
				
				switch (arg2) {
				case 0:
					buyAdapter1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.item_array));
					j=0;
					break;

				case 1:
					buyAdapter1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.itemmanure_array));
					j=1;
					break;
				case 2:
					buyAdapter1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.itempesticides_array));
					j=2;
					break;
				case 3:
					buyAdapter1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tools_assets_array));
					j=3;
					break;
					
				default:
					break;
				}
				spinnerProductName.setAdapter(buyAdapter1);
				
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
			
				
			String	productType=vendorArray[spinnerProductType.getSelectedItemPosition()];
				
				if(j==0)
				{
				 productName=buyerCropArray[spinnerProductName.getSelectedItemPosition()];
				}
				
				else if(j==1)
				{
					
					 productName=itemManureArray[spinnerProductName.getSelectedItemPosition()];
				}
				else if(j==2)
				{
					
					 productName=itemPesticidesArray[spinnerProductName.getSelectedItemPosition()];
				}
				else
				{
					
			 productName=itemToolArray[spinnerProductName.getSelectedItemPosition()];
				}
				
				
				String statename=buyspinner2.getSelectedItem().toString();
			String data=productType+"    "+productName+"   "+statename;
				arrayDisplay.add(data);
				//arrayDisplay.add(productName);
				//arrayDisplay.add(statename);
				
				//listAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayDisplay);
				//buylist.setAdapter(listAdapter);
				//listAdapter.notifyDataSetChanged();
				
				

				VendorSellerStateTask buyerStateTask=new VendorSellerStateTask();
				
				
				switch (buyspinner2.getSelectedItemPosition()) {
				case 0:
					
					String registerUrl="http://192.168.76.37:9292/BhumiPutraServer/getSupplierBasedOnStateServlet";
					buyerStateTask.execute(registerUrl,statename,productName);
					
					
					break;
				case 1:
					
					
					String registerUrl2="http://192.168.76.37:9292/BhumiPutraServer/getSupplierBasedOnDistrictServlet";
					buyerStateTask.execute(registerUrl2,statename,productName);
					
					
					break;
					
				case 2:
					
					
					String registerUrl3="http://192.168.76.37:9292/BhumiPutraServer/getSuplierBasedOnVillageServlet";
					buyerStateTask.execute(registerUrl3,statename,productName);
					
					
					break;
					
				default:
					break;
				}
				
			}
		});
		
		
		return rootView;
		
	}
	

	
	
class VendorSellerStateTask extends AsyncTask<String, Void, String>
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
	


