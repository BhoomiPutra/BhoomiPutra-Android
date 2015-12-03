package vendor;

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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhumiputra.FarmerHomeActivity;
import com.example.bhumiputra.FarmerLoginActivity;
import com.example.bhumiputra.R;


public class VendorLoginActivity extends Activity {
EditText etMobile,etPassword;
	Button btnLogin,btregister;
	TextView textView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendormain);
       
        etMobile=(EditText)findViewById(R.id.editText1);
        etPassword=(EditText)findViewById(R.id.editText2);
        btnLogin=(Button) findViewById(R.id.button2);
        btregister=(Button) findViewById(R.id.button1);
       
        
        btregister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				  Intent intent=new Intent(VendorLoginActivity.this,VendorRegisterActivity.class);
			        
			        startActivity(intent);
				Toast.makeText(VendorLoginActivity.this, "task started..", Toast.LENGTH_LONG).show();
		        
		        
			
				
			}
		});
      
        
        
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
				
				
				
				String mobileno=etMobile.getText().toString();
				String password=etPassword.getText().toString();
				if(mobileno==""||password=="")
				{
					Toast.makeText(VendorLoginActivity.this,"Plesse Enter Mobileno and Password",Toast.LENGTH_LONG);
				}
				else
				{
				String loginUrl="http://192.168.76.37:9292/BhumiPutraServer/VendorLoginServlet";
				LoginTask task=new LoginTask();
				task.execute(mobileno,password,loginUrl);
				
				}
				
				
				
			}
		});
        
        
    }
    
    
    
    
    class LoginTask extends AsyncTask<String, Void, String>
   	{

   		@Override
   		protected String doInBackground(String... params) {
   			
   			String mobileno=params[0];
   			String password=params[1];
   			String loginUrl=params[2];
   			//send values using post method
   			HttpPost postRequest=new HttpPost(loginUrl);
   			//set parameter in post request
   			BasicNameValuePair pair1=new BasicNameValuePair("mobileno", mobileno);
   			BasicNameValuePair pair2=new BasicNameValuePair("password", password);
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
   			
   				Toast.makeText(VendorLoginActivity.this, "Wrong MobileNo or Password,Please try again or Register !!", Toast.LENGTH_LONG).show();
   				
   			}
   			else 
   			{	
   				//launch next activity
   			SharedPreferences sp=getSharedPreferences("settings",MODE_PRIVATE);
   			SharedPreferences.Editor editor=sp.edit();
   			editor.putInt("f_id",Integer.parseInt(rst));
   			editor.commit();
   			//Toast.makeText(FarmerLoginActivity.this, "success", Toast.LENGTH_LONG).show();
   			Intent intent=new Intent(VendorLoginActivity.this,VendorHomeActivity.class);
   			 startActivity(intent);
   				
   			}
   		}
    
    
   	}}
