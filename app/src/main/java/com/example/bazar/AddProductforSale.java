package com.example.bazar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class AddProductforSale extends AppCompatActivity {
    private Context context;
    private EditText name, price, short_description, long_description;
    private TextView photo_status;
    private ImageView photo_taken;
    private Button take_picture;
    public final String APP_TAG = "Bazar";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_productfor_sale);
        name = (EditText) findViewById(R.id.name_of_product_entry);
        price = (EditText) findViewById(R.id.price_entry);
        short_description = (EditText) findViewById(R.id.shortdes_entry);
        long_description = (EditText) findViewById(R.id.longdes_entry);
        photo_status = (TextView) findViewById(R.id.photo_status);
        photo_taken = (ImageView) findViewById(R.id.taken_pic);
        take_picture= (Button)findViewById(R.id.TakePicture);
        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /*

    private void setPic() {
        // Get the dimensions of the View
        int targetW = photo_taken.getWidth();
        int targetH = photo_taken.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        photo_taken.setImageBitmap(bitmap);
    }

*/
    public void takepicture(View view){

      //Take or choose picture from here
        this.context = view.getContext();
        dispatchTakePictureIntent();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo_taken.setImageBitmap(imageBitmap);
        }
    }


    public void cancelclick(View view){

    }
    public void submitclick(View view){

    }
}
