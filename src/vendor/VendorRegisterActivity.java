package vendor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import vendor_dto.Vendor;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bhumiputra.R;

public class VendorRegisterActivity extends Activity {
	
	
	EditText etname,etpassword,etmobile;
	Button btncontinue,btnGallary;
	
	
	ImageView images;
	

	
	  String imgDecodableString;
	  private static int RESULT_LOAD_IMG = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendoreregister);
		
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
				String name=etname.getText().toString();
				String password=etpassword.getText().toString();
				long mobile=Long.parseLong(etmobile.getText().toString());
				
				
				Vendor vendor=new Vendor(name, password, mobile);
				Intent intent=new Intent(VendorRegisterActivity.this, VendorAddressActivity.class);
				
				intent.putExtra("vendor",vendor);
		
				startActivity(intent);
				Toast.makeText(VendorRegisterActivity.this, "task started..", Toast.LENGTH_LONG).show();
				
				
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
