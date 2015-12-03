package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhumiputra.R;



import dao.ToolParser;
import farmer_dto.Address;


public class ToolsManpowerHomeFragmet extends Fragment {
	
	TextView tvname;
	TextView tvmobile;
	TextView tvid;
	ImageView profileImgView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.tools_and_manpower_fragment, container,false);
	
		tvname=(TextView) rootView.findViewById(R.id.textView1);
		tvmobile=(TextView) rootView.findViewById(R.id.textView2);
		tvid=(TextView) rootView.findViewById(R.id.textView3);
		profileImgView=(ImageView) rootView.findViewById(R.id.imageView1);
		
		SharedPreferences sp=getActivity().getSharedPreferences("settings",getActivity().MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		
		int f_id =sp.getInt("f_id", 0);
		
		Log.d("fid",""+f_id);
		
		
		tvid.setText("ID:"+f_id);
		
		HomeTask task=new HomeTask();
		String url="http://192.168.76.37:9292/BhumiPutraServer/getToolServlet";
		task.execute(""+f_id,url);
		
		
	
		
		String	_path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();  
		Log.e("path",_path);  
		_path= _path + "/" + "temp"+".jpg";  
		System.out.println("pathhhhhhhhhhhhhhhhhhhh111111111 " + _path);  
	//	Bitmap bitmap = BitmapFactory.decodeFile(_path);  
		//profileImgView.setImageBitmap(bitmap);
		ProfilePicTask task2=new ProfilePicTask();
		String url2="http://192.168.76.37:9292/BhumiPutraServer/getToolProfilePicServlet";
		task2.execute(""+f_id,url2);
		
		
		return rootView;
		
	}

	 class HomeTask extends AsyncTask<String, Void, String>
		{
@Override
			protected String doInBackground(String... params) {
				
				String fid=params[0];
	
				String loginUrl=params[1];
				

				//send values using post method
				HttpPost postRequest=new HttpPost(loginUrl);
				
				//set parameter in post request
				BasicNameValuePair pair1=new BasicNameValuePair("f_id", fid);
			
				
				ArrayList<BasicNameValuePair> listParams=new ArrayList<BasicNameValuePair>();
				listParams.add(pair1);
			
				//int	result1 = 0;
				String temp="hey";
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
					//Log.e("RESULT-------->", "he -------"+temp);
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
			protected void onPostExecute(String rst)
			{
				// TODO Auto-generated method stub
				super.onPostExecute(rst);

				
				if(rst.equals("fail"))
				{
				
					Toast.makeText(getActivity(), "SOME THING WENT WRONG PLEASE TRY AGIN!!", Toast.LENGTH_LONG).show();
				}
				else 
				{
					ToolsProvider provider=ToolParser.parseToolProvider(rst);
					
					tvname.setText(provider.getName());
					tvmobile.setText("+91"+provider.getMobileno());
					
					
					Log.d("toolJson", rst);
					
						
				}
	
			}
		}

	 class ProfilePicTask extends AsyncTask<String, Void, String>
		{
		 @Override
			protected String doInBackground(String... params) {
				
				String fid=params[0];
	
				String loginUrl=params[1];
				

				//send values using post method
				HttpPost postRequest=new HttpPost(loginUrl);
				
				//set parameter in post request
				BasicNameValuePair pair1=new BasicNameValuePair("f_id", fid);
			
				
				ArrayList<BasicNameValuePair> listParams=new ArrayList<BasicNameValuePair>();
				listParams.add(pair1);
			
				//int	result1 = 0;
				String result="";
				try {
					UrlEncodedFormEntity entity=new UrlEncodedFormEntity(listParams);
					
					postRequest.setEntity(entity);
					
					//send req to the server
					HttpClient client=new DefaultHttpClient();
					HttpResponse response=client.execute(postRequest);		
					InputStream input=response.getEntity().getContent();
					InputStreamReader read=new InputStreamReader(input);
					BufferedReader bufferedReader=new BufferedReader(read);
					
					//save all data to result string
					while (true) {
						String str=bufferedReader.readLine();
						if(str==null)break;
						
						result=result+str;
						
					}//end of while
					bufferedReader.close();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return result;
			}//eof of doInBack
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				Log.e("json", result);
				String stream="";
				
				//parse json result
				try {
					JSONArray jaArray=new JSONArray(result);
					
					//fetch all value from JSONarray
					
						JSONObject jsonObject=jaArray.getJSONObject(0);
						stream=jsonObject.getString("image");
					
					byte[] img=Base64.decode(stream, Base64.DEFAULT);
					Bitmap bp=BitmapFactory.decodeByteArray(img, 0, img.length);
					profileImgView.setImageBitmap(bp);
					
					saveBitmap(bp);
					
					
					
				} catch (Exception e) {
					
					Log.e("errer----",e.toString());
					
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
	
		}


	 private void saveBitmap(Bitmap image) {
			try {

						String path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
						OutputStream fOut = null;
						File file = new File(path,"temp"+".jpg"); // the File to save to
						fOut = new FileOutputStream(file);
						 Toast.makeText(getActivity(),path,
			                        Toast.LENGTH_LONG).show();
						Bitmap pictureBitmap =image; // obtaining the Bitmap
						pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
						
						MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
						fOut.flush();
						fOut.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
			 }


}

		
	
