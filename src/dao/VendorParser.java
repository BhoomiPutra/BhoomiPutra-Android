package dao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import vendor_dto.Vendor;
import vendor_dto.VendorAddress;
import vendor_dto.VendorBuyer;
import vendor_dto.VendorSeller;

public class VendorParser {
	
	static Vendor vendor;
	
	
	public static Vendor parseVendor(String rst) {
		
		
		String name;
		long mobileno;
		JSONObject Jobject;
		
	try {
		Jobject = new JSONObject(rst);
		name = Jobject.getString("name");
		mobileno= Jobject.getLong("mobileno");
		 String password=Jobject.getString("password");
		 JSONObject addr=Jobject.getJSONObject("addr");
				String village=addr.getString("village");
				String state=addr.getString("state");
				 String district=addr.getString("district");
				 String tehsil=addr.getString("tehsil");
				 String houseno=addr.getString("houseNo");
				 int pinCode=addr.getInt("pincode");
				 
				VendorAddress vendorAddress=new VendorAddress(village, houseno, tehsil, district, state, pinCode); 
			Vendor vendor=new Vendor(name, password, mobileno);
			vendor.setAddr(vendorAddress);
			
			
			JSONArray buyerJsobArray=Jobject.getJSONArray("listBuyer");
			
			
			ArrayList<VendorBuyer> arrayListbuyer=new ArrayList<VendorBuyer>();
			
			for (int i=0;i<buyerJsobArray.length();i++) {
			JSONObject buyerJsonObject=buyerJsobArray.getJSONObject(i);
			

			String cropType=buyerJsonObject.getString("cropType");
			
			String cropSubtype=buyerJsonObject.getString("cropSubtype");
			double cropPrice=buyerJsonObject.getDouble("cropPrice");
			

		VendorBuyer vendorBuyer=new VendorBuyer(cropType, cropSubtype, cropPrice);
			
			arrayListbuyer.add(vendorBuyer);
			
			}
			vendor.setListBuyer(arrayListbuyer);
			
			
			
       JSONArray sellerJsobArray=Jobject.getJSONArray("listSeller");
			
			
			ArrayList<VendorSeller> arrayListseller=new ArrayList<VendorSeller>();
			
			for (int i=0;i<sellerJsobArray.length();i++) {
			JSONObject sellerJsonObject=sellerJsobArray.getJSONObject(i);
			
			
			String productType=sellerJsonObject.getString("itemType");
			String itemType=sellerJsonObject.getString("itemType");
			Double price=sellerJsonObject.getDouble("price");
			int count=sellerJsonObject.getInt("count");

			

		VendorSeller vendorBuyer=new VendorSeller(productType, itemType, price);
			
			arrayListseller.add(vendorBuyer);
			
			}
			vendor.setListSeller(arrayListseller);
			
			
			
				Log.d("vendor",vendor.toString());
				/*tvmobile.setText("+91"+mobileno);
				tvname.setText(name);*/
		

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	return vendor;
	
	}
	

}
