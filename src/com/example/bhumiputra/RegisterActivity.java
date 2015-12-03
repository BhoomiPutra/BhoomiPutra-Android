package com.example.bhumiputra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import farmer_dto.Farmer;

public class RegisterActivity extends Activity {
	
	
	EditText etname,etpassword,etmobile;
	Button btncontinue,btnGallary;
	
	ImageView images;
	
	Farmer farmer=null;
	String name=null;
	long mobileno=0;
	String password=null;
	
	String imgDecodableString;
	  private static int RESULT_LOAD_IMG = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
	       etname=(EditText)findViewById(R.id.editText1);
	       etpassword=(EditText)findViewById(R.id.editText2);
	       etmobile=(EditText)findViewById(R.id.editText3);
			btncontinue=(Button)findViewById(R.id.button2);
			btnGallary=(Button)findViewById(R.id.button1);
	
			images=(ImageView)findViewById(R.id.imageView1);
			btnGallary.setOnClickListener(new OnClickListener() {
		
			
			
			
			@Override
			public void onClick(View v) {
				Intent galleryIntent = new Intent(Intent.ACTION_PICK,
				        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				// Start the Intent
				startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
			}
		});
		

	//	final String name=etname.getText().toString();
		//final String password=etpassword.getText().toString();
		//final int mobile=Integer.parseInt(etmobile.getText().toString());
		btncontinue.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				 name=etname.getText().toString();
				 password=etpassword.getText().toString();
				 mobileno=Long.parseLong(etmobile.getText().toString());
				farmer=new Farmer(name,password,mobileno);
				Intent intent=new Intent(RegisterActivity.this, AddressActivity.class);
				if(name.equals("")||password.equals("")||etmobile.getText().equals(""))
				{
					Toast.makeText(RegisterActivity.this,"Please fill all details", Toast.LENGTH_LONG).show();; 
				}
				else
				{
				intent.putExtra("farmer",farmer);
		
				startActivity(intent);
				
				}
				
			}
		});
		
		
	}	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
 
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
               
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
 
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
               
                // Set the Image in ImageView after decoding the String
               
                Bitmap bm=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgDecodableString), 150, 150, false);
                images.setImageBitmap(bm);
                saveBitmap(bm);
                
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
 
	}
	

	private void saveBitmap(Bitmap image) throws FileNotFoundException{
		try {

					String path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
					OutputStream fOut = null;
					File file = new File(path,"temp"+".jpg"); // the File to save to
					fOut = new FileOutputStream(file);
					 Toast.makeText(this,path,
		                        Toast.LENGTH_LONG).show();
					Bitmap pictureBitmap =image; // obtaining the Bitmap
					pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
					
					MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
					fOut.flush();
					fOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
		 }
}