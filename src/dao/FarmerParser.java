package dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import farmer_dto.Address;
import farmer_dto.Farmer;

public class FarmerParser {

	static Farmer farmer;
	
	
	
	public static Farmer parseFarmer(String rst) {
		

		String name;
		long mobileno;
		JSONObject Jobject;
		
	try {
			Jobject = new JSONObject(rst);
			name = Jobject.getString("name");
			
			
					 
					 
			mobileno= Jobject.getLong("mobileno");
			// String password=Jobject.getString("password");
			 
			
			
					JSONObject addr=Jobject.getJSONObject("addr");
					String village=addr.getString("village");
					String state=addr.getString("state");
					 String district=addr.getString("district");
					 String tehsil=addr.getString("tehsil");
					 String houseno=addr.getString("houseNo");
					 int pinCode=addr.getInt("pincode");
				Address address=new Address(village, houseno, tehsil, district, state, pinCode);
				farmer=new Farmer(name, "pass", mobileno);
			
				
				
				double totalLand=Jobject.getDouble("totalLand");
			 farmer.setAddr(address);
			 farmer.setTotalLand(totalLand);
			
			
			Log.d("farmer",""+farmer);
			//tvmobile.setText("+91"+mobileno);
			//tvname.setText(name);
		
			
			
			
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return farmer;
		
	}
}
