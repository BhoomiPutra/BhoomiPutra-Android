package com.example.bhumiputra;

import java.util.Locale;

import tools.ToolsManpowerLoginActivity;
import vendor.VendorLoginActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;





public class MainActivity extends Activity {

	Button btFarmer,bttool,btvendor;
	Spinner splanguage;
	String[] lagarray;
	Boolean flag=false;
	
	ArrayAdapter<String> langAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selector);
        btFarmer=(Button) findViewById(R.id.button1);
        bttool=(Button) findViewById(R.id.button3);
        btvendor=(Button) findViewById(R.id.button2);
		
        
        
        splanguage=(Spinner) findViewById(R.id.spinner1);
		
	
		lagarray=getResources().getStringArray(R.array.language_selector);
		
		langAdapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,lagarray);
		
		splanguage.setAdapter(langAdapter);
       splanguage.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

		
			Locale locale =new Locale("hi");
			 Resources res = getResources();
	            String current = res.getConfiguration().locale.getCountry();
	            Log.i("Current", current);
	            String localeString = new String(current);
			
		switch (arg2) {
			case 0:
				locale=new Locale("hi");
				break;
			case 1:
				locale=new Locale("en");
				break;
				}
			
		/*if (!current.equalsIgnoreCase(localeString) && localeString.length() > 0) {
                // Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.locale = new Locale(localeString.toLowerCase());
                res.updateConfiguration(conf, dm);  
                //refresh menu
                setGridView();
                //added this line to get refreshed listener
                changeLanguageListener();
            }  */    
			
			
			
		
			Locale.setDefault(locale);
			Configuration configuration=new Configuration();
			configuration.locale=locale;
			MainActivity.this.getApplicationContext().getResources().updateConfiguration(configuration, null);
				

		
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});



        btFarmer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				

		        Intent intent=new Intent(MainActivity.this,FarmerLoginActivity.class);
		        
		        startActivity(intent);
		        
		        
			
				
			}
		});
      

        
 bttool.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				

		        Intent intent=new Intent(MainActivity.this,ToolsManpowerLoginActivity.class);
		        
		        startActivity(intent);
		        
		        
			
				
			}
		});
      
 btvendor.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			

	        Intent intent=new Intent(MainActivity.this,VendorLoginActivity.class);
	        
	        startActivity(intent);
	        
	        
		
			
		}
	});

 
    }

    public void onConfigurationChanged(Configuration newConfig) {
    	  // refresh your views here
    	  super.onConfigurationChanged(newConfig);
    	
    Toast.makeText(MainActivity.this,"hey", Toast.LENGTH_LONG).show();
    
    
    }
   



}
