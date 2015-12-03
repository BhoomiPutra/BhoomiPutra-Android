package dao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import tool_dto.Manpower;
import tool_dto.ToolAddress;
import tool_dto.Tools;
import tool_dto.ToolsProvider;

public class ToolParser {


	static ToolsProvider toolsProvider;


public static ToolsProvider parseToolProvider(String rst){
	
	
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
			 
	 
	 
	 Log.d("toolJson", name+addr.toString()+pinCode);
		ToolAddress toolAddress=new ToolAddress(village, houseno, tehsil, district, state, pinCode);
		 toolsProvider=new ToolsProvider(name, password, mobileno);
		
		
		toolsProvider.setAddr(toolAddress);
		
		JSONArray toolJsonArray=Jobject.getJSONArray("tools");
		
		
		ArrayList<Tools> arrayListTool=new ArrayList<Tools>();
		
		for (int i=0;i<toolJsonArray.length();i++) {
		JSONObject jsonObjectTool=toolJsonArray.getJSONObject(i);
		String toolname=jsonObjectTool.getString("name");
		double toolrate=jsonObjectTool.getDouble("toolrate");
		int count=jsonObjectTool.getInt("count");
		
		Tools tools=new Tools(toolname, toolrate, count);
		
		arrayListTool.add(tools);
		
		}
		toolsProvider.setListTools(arrayListTool);
		
		//end of tool
		
		
		JSONArray manpowerJsonArray=Jobject.getJSONArray("manPowerList");
		
		
		ArrayList<Manpower> arrayListManpower=new ArrayList<Manpower>();
		
		for (int i=0;i<manpowerJsonArray.length();i++) {
		JSONObject jsonObjectTool=manpowerJsonArray.getJSONObject(i);
		String manpowerCategory=jsonObjectTool.getString("manpowercategory");
		double manpowerRate=jsonObjectTool.getDouble("manpowerRate");
		int count=jsonObjectTool.getInt("count");
		
		Manpower manpower=new Manpower(manpowerCategory, manpowerRate, count);
		
		arrayListManpower.add(manpower);
		
		}
		toolsProvider.setListManpowers(arrayListManpower);
		
		
		
		Log.e("toolsprovider",toolsProvider.toString());
		/*tvmobile.setText("+91"+mobileno);
		tvname.setText(name);
	*/

	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	

	return toolsProvider;
}


}
