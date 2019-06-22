package com.example.bazar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class AddProductforSale extends AppCompatActivity {
    private Context context;
    private EditText name, price, short_description, long_description;
    private TextView photo_status;
    private ImageView photo_taken;
    private Button take_picture;

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
    public static final int GALLERY = 0;

    private void dispatchTakePictureIntent() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Capture photo from camera",
                "Select photo from gallery"
                 };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                        }
                        else if(which ==1){
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            if(galleryIntent.resolveActivity(getPackageManager())!=null){
                                startActivityForResult(galleryIntent, GALLERY);
                            }


                        }
                    }
                });
        pictureDialog.show();


    }

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
            photo_taken.setScaleX((float) 1.5);
        }
        else if(requestCode== GALLERY && resultCode == RESULT_OK){
            Uri contentURI = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                Toast.makeText(AddProductforSale.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                //get the path of the image
                photo_taken.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(AddProductforSale.this, "Cannot save image",Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void cancelclick(View view){
        name.setText("");
        price.setText("");
        short_description.setText("");
        long_description.setText("");
        photo_taken.setImageDrawable(null);

    }
    public void submitclick(View view){

        //Communicate_with_server.add_product_to_database(name.getText(),Double.parseDouble(price.getText()),);

        Intent intent = new Intent(view.getContext(), Slidermenu.class);
        view.getContext().startActivity(intent);
    }
}
